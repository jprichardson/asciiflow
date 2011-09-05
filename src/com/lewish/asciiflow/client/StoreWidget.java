package com.lewish.asciiflow.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lewish.asciiflow.client.StoreHelper.SaveCallback;
import com.lewish.asciiflow.shared.State;
import com.lewish.asciiflow.shared.Uri;

@Singleton
public class StoreWidget extends Composite {

	private final StoreHelper storeHelper;

	private FlowPanel linksPanel;
	private Anchor editLink;
	private Anchor readonlyLink;
	private Button saveButton;

	@Inject
	public StoreWidget(final Canvas canvas, final StoreHelper storeHelper) {
		this.storeHelper = storeHelper;
		linksPanel = new FlowPanel();
		editLink = new Anchor();
		readonlyLink = new Anchor();

		saveButton = new Button("Save");
		saveButton.setStyleName(CssStyles.Inline);

		saveButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				State state = new State();
				state.setCellStateMap(canvas.getCellStates());
				storeHelper.save(state, new SaveCallback() {

					@Override
					public void afterSave(boolean success, State state) {
						updateLinks();
					}
				});
			}
		});

		FlowPanel panel = new FlowPanel();
		panel.setStyleName(CssStyles.Block);
		panel.add(saveButton);
		linksPanel.add(new InlineLabel(" Edit:"));
		linksPanel.add(editLink);
		linksPanel.add(new InlineLabel("<br>"));
		linksPanel.add(new InlineLabel(" Readonly:"));
		linksPanel.add(readonlyLink);
		linksPanel.setStyleName(CssStyles.Inline);
		panel.add(linksPanel);
		updateLinks();
		initWidget(panel);
	}

	private void updateLinks() {
		if(storeHelper.getCurrentState() == null) {
			linksPanel.setVisible(false);
		} else {
		String editHref = Uri.getDocumentLink(storeHelper.getCurrentState().getId(), storeHelper
				.getCurrentState().getEditCode());
		String readonlyHref = Uri.getDocumentLink(storeHelper.getCurrentState().getId(), null);
		readonlyLink.setHref(readonlyHref);
		readonlyLink.setText(readonlyHref);
		editLink.setHref(editHref);
		editLink.setText(editHref);
		linksPanel.setVisible(true);
		}
	}
}
