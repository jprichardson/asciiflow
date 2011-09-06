package com.lewish.asciiflow.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lewish.asciiflow.client.StoreHelper.SaveCallback;
import com.lewish.asciiflow.shared.State;
import com.lewish.asciiflow.shared.Uri;

@Singleton
public class StoreWidget extends Composite {

	private final StoreHelper storeHelper;

	private final FlowPanel linksPanel = new FlowPanel();
	private final Anchor editLink = new Anchor();
	private final Anchor readonlyLink = new Anchor();
	private final Button saveButton = new Button();
	private final TextBox titleBox = new TextBox();
	private final CheckBox isPublic = new CheckBox();

	@Inject
	public StoreWidget(final Canvas canvas, final StoreHelper storeHelper) {
		this.storeHelper = storeHelper;

		saveButton.setStyleName(CssStyles.Inline);

		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				storeHelper.save(new SaveCallback() {

					@Override
					public void afterSave(boolean success, State state) {
						updateLinks();
					}
				});
			}
		});

		titleBox.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				storeHelper.getCurrentState().setTitle(event.getValue());
			}
		});

		isPublic.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				storeHelper.getCurrentState().setPublic(event.getValue());
			}
		});

		saveButton.setText("Save");
		readonlyLink.setText("Read only Link");
		editLink.setText("Edit link");
		titleBox.setText("Untitled");

		readonlyLink.setTarget("_blank");
		editLink.setTarget("_blank");

		FlowPanel panel = new FlowPanel();
		panel.setStyleName(CssStyles.Block);
		panel.add(saveButton);
		panel.add(new InlineLabel(" Title: "));
		panel.add(titleBox);
		linksPanel.add(new InlineLabel(" Public: "));
		linksPanel.add(isPublic);
		linksPanel.add(new InlineLabel(" "));
		linksPanel.add(editLink);
		linksPanel.add(new InlineLabel(" "));
		linksPanel.add(readonlyLink);
		linksPanel.setStyleName(CssStyles.Inline);
		panel.add(linksPanel);
		updateLinks();
		initWidget(panel);
	}

	private void updateLinks() {
		if (storeHelper.getCurrentState().getId() == null) {
			linksPanel.setVisible(false);
		} else {
			String editHref = Uri.getDocumentLink(storeHelper.getCurrentState().getId(),
					storeHelper.getCurrentState().getEditCode());
			String readonlyHref = Uri.getDocumentLink(storeHelper.getCurrentState().getId(), null);
			readonlyLink.setHref(readonlyHref);
			editLink.setHref(editHref);
			linksPanel.setVisible(true);
		}
	}

	public void setTitle(String title) {
		titleBox.setText(title);
	}

	public void setPublic(Boolean isPublic) {
		this.isPublic.setValue(isPublic);
	}
}
