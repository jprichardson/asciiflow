package com.lewish.asciiflow.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lewish.asciiflow.client.tools.EraseTool;
import com.lewish.asciiflow.shared.State;

@Singleton
public class StoreHelper {

	private final StoreServiceAsync service;
	private final Canvas canvas;

	private State currentState;

	@Inject
	public StoreHelper(StoreServiceAsync service, Canvas canvas) {
		this.service = service;
		this.canvas = canvas;
	}

	public void parseFragmentLoadAndDraw() {
		//Load
		String hash = Window.Location.getHash();
		if(hash != null && hash.startsWith("#")) {
			try {
				String[] split = hash.split("/");
				if (split.length != 2) {
					return;
				}
				Long id = Long.parseLong(split[0]);
				Integer editCode = Integer.parseInt(split[1]);
				loadAndDraw(id, editCode);
			} catch (NumberFormatException e) {
				//TODO
			}
		}
	}

	public void loadAndDraw(Long id, Integer editCode) {
		load(id, editCode, new LoadCallback() {
			@Override
			public void afterLoad(boolean success, State state) {
				if (success) {
					EraseTool.draw(canvas);
					canvas.drawState(state);
					canvas.refreshDraw();
					canvas.commitDraw();
				}
			}
		});
	}

	public void load(Long id, Integer editCode, final LoadCallback callback) {
		service.loadState(id, editCode, new AsyncCallback<State>() {
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
				if (currentState != null) {
					state.setId(currentState.getId());
					state.setEditCode(currentState.getEditCode());
					currentState = null;
				}
				service.saveState(state, new AsyncCallback<State>() {
					@Override
					public void onSuccess(State result) {
						currentState = result;
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
		public void afterSave(boolean success, State state);
	}

	public static interface LoadCallback {
		public void afterLoad(boolean success, State state);
	}

	public void clearState() {
		currentState = null;
	}

	public State getCurrentState() {
		return currentState;
	}
}
