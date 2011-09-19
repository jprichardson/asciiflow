package com.lewish.asciiflow.client;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lewish.asciiflow.client.CompressedStoreServiceAsync.LoadCallback;
import com.lewish.asciiflow.client.CompressedStoreServiceAsync.SaveCallback;
import com.lewish.asciiflow.shared.State;

@Singleton
public class StoreModel {

	private final CompressedStoreServiceAsync service;
	private final Canvas canvas;
	private final LoadingWidget loadingWidget;

	private State currentState;

	@Inject
	public StoreModel(CompressedStoreServiceAsync service, Canvas canvas,
			LoadingWidget loadingWidget) {
		this.service = service;
		this.canvas = canvas;
		this.loadingWidget = loadingWidget;

		currentState = new State();
	}

	public void load(final Long id, final Integer editCode, final LoadCallback callback) {
		loadingWidget.show();
		service.loadState(id, editCode, new LoadCallback() {
			@Override
			public void afterLoad(boolean success, State state) {
				loadingWidget.hide();
				currentState = state;
				callback.afterLoad(success, state);
			}
		});
	}

	public void save(final SaveCallback callback) {
		loadingWidget.show();
		currentState.setCellStateMap(canvas.getCellStates());
		service.saveState(currentState, new SaveCallback() {
			@Override
			public void afterSave(boolean success, State state) {
				loadingWidget.hide();
				currentState.setId(state.getId());
				currentState.setEditCode(state.getEditCode());
				callback.afterSave(success, currentState);
			}
		});
	}

	public void clearState() {
		currentState = new State();
	}

	public State getCurrentState() {
		return currentState;
	}
}
