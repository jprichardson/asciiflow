package com.lewish.asciiflow.client.gallery;

import com.google.gwt.user.client.ui.Composite;
import com.google.inject.Inject;
import com.lewish.asciiflow.client.StoreServiceAsync;

public class GallerySidebarWidget extends Composite {

	private final StoreServiceAsync storeService;
	
	@Inject
	public GallerySidebarWidget(StoreServiceAsync storeService) {
		this.storeService = storeService;
	}
}
