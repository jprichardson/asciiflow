package com.lewish.asciiflow.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lewish.asciiflow.client.StoreHelper.SaveCallback;
import com.lewish.asciiflow.shared.Uri;
import com.lewish.asciiflow.shared.State;

@Singleton
public class StoreWidget extends Composite {

	private final StoreHelper storeHelper;

	private Anchor editLink;
	private Anchor readonlyLink;
	private Button saveButton;

	@Inject
	public StoreWidget(final Canvas canvas, final StoreHelper storeHelper) {
		this.storeHelper = storeHelper;
		editLink = new Anchor();
		readonlyLink = new Anchor();

		saveButton = new Button("Save");

		saveButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				storeHelper.save(canvas.getState(), new SaveCallback() {

					@Override
					public void afterSave(boolean success, State state) {
						updateLinks();
					}
				});
			}
		});

		FlowPanel panel = new FlowPanel();
		panel.add(saveButton);
		panel.add(new Label(" Edit Link: "));
		panel.add(editLink);
		panel.add(new Label(" Readonly Link: "));
		panel.add(readonlyLink);
		initWidget(panel);
	}

	private void updateLinks() {
		if(storeHelper.getCurrentState() == null) {
			readonlyLink.setVisible(false);
			editLink.setVisible(false);
		} else {
		String editHref = Uri.getDocumentLink(storeHelper.getCurrentState().getId(), storeHelper
				.getCurrentState().getEditCode());
		String readonlyHref = Uri.getDocumentLink(storeHelper.getCurrentState().getId(), null);
		readonlyLink.setHref(readonlyHref);
		readonlyLink.setText(readonlyHref);
		editLink.setHref(editHref);
		editLink.setText(editHref);
		}
	}
}
