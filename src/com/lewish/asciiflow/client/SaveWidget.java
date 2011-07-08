package com.lewish.asciiflow.client;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

@Singleton
public class SaveWidget extends MenuWidget {

	private static final String LINK_PREFIX = "http://www.asciiflow.com/#";
	private Long id;

	private Anchor saveLink;

	public SaveWidget() {
		super(50);
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	protected Widget getPanel() {
		Label saveLabel = new Label("Copy the link below to save and share your diagram:");
		FlowPanel panel = new FlowPanel();
		saveLink = new Anchor();
		panel.add(saveLabel);
		panel.add(saveLink);
		return panel;
	}

	@Override
	protected void onShow() {
		saveLink.setText(LINK_PREFIX + id);
		saveLink.setHref(LINK_PREFIX + id);
	}
}
