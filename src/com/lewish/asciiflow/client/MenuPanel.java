//Copyright Lewis Hemens 2011
package com.lewish.asciiflow.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lewish.asciiflow.client.tools.EraseTool;
import com.lewish.asciiflow.shared.State;

@Singleton
public class MenuPanel extends Composite {

	@Inject
	public MenuPanel(final Canvas canvas, final ExportWidget exportWidget,
			final ImportWidget importWidget, final HistoryManager historyManager, final StoreServiceAsync storeService) {
		FlowPanel panel = new FlowPanel();
		panel.add(getButton("Add row", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				canvas.addRow();
			}
		}));
		panel.add(getButton("Add column", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				canvas.addColumn();
			}
		}));
		panel.add(getButton("Undo", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				historyManager.undo();
			}
		}));
		panel.add(getButton("Redo", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				historyManager.redo();
			}
		}));
		panel.add(getButton("Clear cells", new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (Window.confirm("Are you sure you want to clear all cells?")) {
					EraseTool.draw(canvas);
					canvas.refreshDraw();
					historyManager.save(canvas.commitDraw());
				}
			}
		}));
		panel.add(getButton("Export", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				exportWidget.toggle();
			}
		}));
		panel.add(getButton("Export Html", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				exportWidget.setHtml(true);
				exportWidget.toggle();
			}
		}));
		panel.add(getButton("Import", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				importWidget.toggle();
			}
		}));
		panel.add(getButton("Ditaa!", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String export = URL.encode(exportWidget.getText(false))
					.replace("+", "%2B")
					.replace("%20", "+");
				Window.open("http://ditaa.org/ditaa/render?grid=" + export, "_blank", null);
			}
		}));
		panel.add(getButton("Save", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final State state = canvas.getState();
				state.compress(new AsyncCallback<Boolean>() {
					@Override
					public void onFailure(Throwable caught) {
					}
					@Override
					public void onSuccess(Boolean result) {
						storeService.saveState(state, new AsyncCallback<Long>() {
							@Override
							public void onSuccess(Long result) {
								Window.alert(result.toString());
							}
							@Override
							public void onFailure(Throwable caught) {
								Window.alert(caught.getMessage());
							}
						});
					}
					
				});
			}
		}));
		panel.add(getButton("Open", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String hash = Window.Location.getHash().substring(1);
				Long id;
				try {
				id = Long.parseLong(hash);
			} catch (NumberFormatException e) {
				//TODO
				return;
			}
				storeService.loadState(id, new AsyncCallback<State>() {
					@Override
					public void onSuccess(final State result) {
						result.uncompress(new AsyncCallback<Boolean>() {
							@Override
							public void onFailure(Throwable caught) {
							}
							@Override
							public void onSuccess(Boolean success) {
								EraseTool.draw(canvas);
								canvas.drawState(result);
								canvas.refreshDraw();
								canvas.commitDraw();
							}
						});
						//TODO Clear History, add open confirmation
					}
					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}
				});
				
			}
		}));
		panel.setStyleName(CssStyles.MenuPanel);
		initWidget(panel);
	}

	private Widget getButton(String label, ClickHandler handler) {
		Button button = new Button(label);
		button.addClickHandler(handler);
		return button;
	}
}
