package com.lewish.asciiflow.client.gallery;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lewish.asciiflow.client.common.SidebarLayout;

@Singleton
public class GalleryLayout extends SidebarLayout {

	private final GallerySidebarWidget sidebarWidget;

	@Inject
	public GalleryLayout(GallerySidebarWidget sidebarWidget) {
		this.sidebarWidget = sidebarWidget;
	}

	@Override
	public Widget getContent() {
		return new Label("CONTENT");
	}

	@Override
	public Widget getHeaderContent() {
		return new FlowPanel();
	}

	@Override
	public Widget getSideContent() {
		return sidebarWidget;
	}

}
