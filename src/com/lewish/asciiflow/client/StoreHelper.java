package com.lewish.asciiflow.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lewish.asciiflow.shared.AccessException;
import com.lewish.asciiflow.shared.State;

@Singleton
public class StoreHelper {

	private final StoreServiceAsync service;
	private final Canvas canvas;
	private final LoadingWidget loadingWidget;

	private State currentState;

	@Inject
	public StoreHelper(StoreServiceAsync service, Canvas canvas, LoadingWidget loadingWidget) {
		this.service = service;
		this.canvas = canvas;
		this.loadingWidget = loadingWidget;

		currentState = new State();
	}

	public void load(final Long id, final Integer editCode, final LoadCallback callback) {
		loadingWidget.show();
		service.loadState(id, editCode, new AsyncCallback<State>() {
			@Override
			public void onSuccess(final State result) {
				result.uncompress(new AsyncCallback<Boolean>() {
					@Override
					public void onFailure(Throwable caught) {
						loadingWidget.hide();
						Window.alert(caught.getMessage());
						callback.afterLoad(false, null);
					}

					@Override
					public void onSuccess(Boolean success) {
						loadingWidget.hide();
						currentState = result;
						callback.afterLoad(true, result);
					}
				});
				// TODO Clear History, add open confirmation, etc
			}

			@Override
			public void onFailure(Throwable caught) {
				loadingWidget.hide();
				Window.alert(caught.getMessage());
				callback.afterLoad(false, null);
			}
		});
	}

	public void save(final SaveCallback callback) {
		loadingWidget.show();
		currentState.setCellStateMap(canvas.getCellStates());
		currentState.compress(new AsyncCallback<Boolean>() {

			@Override
			public void onSuccess(Boolean result) {
				service.saveState(currentState, new AsyncCallback<State>() {
					@Override
					public void onSuccess(State result) {
						loadingWidget.hide();
						currentState.setId(result.getId());
						currentState.setEditCode(result.getEditCode());
						callback.afterSave(true, currentState);
					}

					@Override
					public void onFailure(Throwable caught) {
						loadingWidget.hide();
						Window.alert(caught.getMessage());
						if (caught instanceof AccessException) {
							//Bad access code, reset id for branching.
							currentState.setEditCode(0);
							currentState.setId(null);
						}
						callback.afterSave(false, null);
					}
				});
			}

			@Override
			public void onFailure(Throwable caught) {
				loadingWidget.hide();
				callback.afterSave(false, null);
			}
		});
	}

	public static interface SaveCallback {
		public void afterSave(boolean success, State state);
	}

	public static interface LoadCallback {
		public void afterLoad(boolean success, State state);
	}

	public void clearState() {
		currentState = new State();
	}

	public State getCurrentState() {
		return currentState;
	}
}
