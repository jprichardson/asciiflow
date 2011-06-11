package com.lewish.asciigram.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class MenuPanel extends Composite {

	@Inject
	public MenuPanel(final Canvas canvas, final ExportPanel exportPanel) {
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
		panel.add(new HTML("&nbsp;"));
		panel.add(getButton("Clear cells", new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				//TODO Add confirmation.
				canvas.clearCells();
			}
		}));
		panel.add(new HTML("&nbsp;"));
		panel.add(getButton("Export", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				exportPanel.toggle();
			}
		}));
		Button save = (Button)getButton("Save", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				
			}
		});
		save.setEnabled(false);
		panel.add(save);
		panel.setStyleName(CssStyles.MenuPanel);
		initWidget(panel);
	}

	private Widget getButton(String label, ClickHandler handler) {
		Button button = new Button(label);
		button.addClickHandler(handler);
		return button;
	}
}
