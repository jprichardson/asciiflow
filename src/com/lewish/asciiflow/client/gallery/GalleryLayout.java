package com.lewish.asciiflow.client.gallery;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lewish.asciiflow.client.common.PlainLayout;

@Singleton
public class GalleryLayout extends PlainLayout {

	private final GallerySidebarWidget sidebarWidget;

	@Inject
	public GalleryLayout(GallerySidebarWidget sidebarWidget) {
		this.sidebarWidget = sidebarWidget;
	}

	@Override
	public Widget getContent() {
		FlowPanel panel = new FlowPanel();
		panel.add(sidebarWidget);
		return panel;
	}
}
