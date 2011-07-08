package com.lewish.asciiflow.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lewish.asciiflow.shared.State;

@Singleton
public class StorageHelper {

	private StoreServiceAsync service;
	private Long currentId;

	@Inject
	public StorageHelper(StoreServiceAsync service) {
		this.service = service;
	}

	public void load(Long id, final LoadCallback callback) {
		service.loadState(id, new AsyncCallback<State>() {
			@Override
			public void onSuccess(final State result) {
				result.uncompress(new AsyncCallback<Boolean>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
						callback.afterLoad(false, null);
					}

					@Override
					public void onSuccess(Boolean success) {
						callback.afterLoad(true, result);
					}
				});
				// TODO Clear History, add open confirmation, etc
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
				callback.afterLoad(false, null);
			}
		});
	}

	public void save(final State state, final SaveCallback callback) {
		state.compress(new AsyncCallback<Boolean>() {

			@Override
			public void onSuccess(Boolean result) {
				if (currentId != null) {
					state.setId(currentId);
				}
				service.saveState(state, new AsyncCallback<Long>() {
					@Override
					public void onSuccess(Long result) {
						currentId = result;
						callback.afterSave(true, result);
					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
						callback.afterSave(false, null);
					}
				});
			}

			@Override
			public void onFailure(Throwable caught) {
				callback.afterSave(false, null);
			}
		});
	}

	public static interface SaveCallback {
		public void afterSave(boolean success, Long id);
	}

	public static interface LoadCallback {
		public void afterLoad(boolean success, State state);
	}

	public void clearId() {
		this.currentId = null;
	}
}
