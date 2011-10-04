package com.lewish.asciiflow.client.gallery;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.lewish.asciiflow.client.StoreServiceAsync;
import com.lewish.asciiflow.shared.BatchStoreQueryResult;
import com.lewish.asciiflow.shared.State;

@Singleton
public class GallerySidebarWidget extends Composite {

	private final StoreServiceAsync storeService;
	private final Provider<GalleryPreviewWidget> previewWidgetProvider;
	private final List<State> states = new ArrayList<State>();
	private String cursorString = null;

	private final FlowPanel panel = new FlowPanel();

	@Inject
	public GallerySidebarWidget(StoreServiceAsync storeService,
			Provider<GalleryPreviewWidget> previewWidgetProvider) {
		this.storeService = storeService;
		this.previewWidgetProvider = previewWidgetProvider;
		initWidget(panel);
	}

	@Override
	protected void onAttach() {
		if (cursorString == null) {
			fetchMore();
		}
	}

	private void fetchMore() {
		storeService.loadTenStates(cursorString, new AsyncCallback<BatchStoreQueryResult>() {

			@Override
			public void onSuccess(BatchStoreQueryResult result) {
				GallerySidebarWidget.this.states.addAll(result.getStates());
				cursorString = result.getCursorString();
				for (State state : result.getStates()) {
					GalleryPreviewWidget widget = previewWidgetProvider.get();
					widget.setState(state);
					panel.add(widget);
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Report error
			}
		});
	}
}
