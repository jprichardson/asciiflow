package com.lewish.asciiflow.client;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lewish.asciiflow.client.CompressedStoreServiceAsync.LoadCallback;
import com.lewish.asciiflow.client.CompressedStoreServiceAsync.SaveCallback;
import com.lewish.asciiflow.shared.State;

@Singleton
public class StoreModel {

	public static interface ModelChangeHandler extends EventHandler {
		public void onModelChange(ModelChangeEvent event);
	}

	public static class ModelChangeEvent extends GwtEvent<ModelChangeHandler> {

		public static enum ModelChangeState {
			LOADED,
			SAVED,
			;
		}

		public static final Type<ModelChangeHandler> TYPE = new Type<ModelChangeHandler>();
		public static final ModelChangeEvent LOADED = new ModelChangeEvent(ModelChangeState.LOADED);
		public static final ModelChangeEvent SAVED = new ModelChangeEvent(ModelChangeState.SAVED);
	
		private final ModelChangeState state;

		public ModelChangeEvent(ModelChangeState state) {
			this.state = state;
		}

		@Override
		public Type<ModelChangeHandler> getAssociatedType() {
			return TYPE;
		}

		@Override
		protected void dispatch(ModelChangeHandler handler) {
			handler.onModelChange(this);
		}

		public ModelChangeState getState() {
			return state;
		}
	}

	private final HandlerManager handlerManager = new HandlerManager(this);

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

	public void load(final Long id, final Integer editCode) {
		loadingWidget.show();
		service.loadState(id, editCode, new LoadCallback() {
			@Override
			public void afterLoad(boolean success, State state) {
				loadingWidget.hide();
				currentState = state;
				fireEvent(ModelChangeEvent.LOADED);
			}
		});
	}

	public void save() {
		loadingWidget.show();
		currentState.setCellStateMap(canvas.getCellStates());
		service.saveState(currentState, new SaveCallback() {
			@Override
			public void afterSave(boolean success, State state) {
				loadingWidget.hide();
				currentState.setId(state.getId());
				currentState.setEditCode(state.getEditCode());
				fireEvent(ModelChangeEvent.SAVED);
			}
		});
	}

	public void clearState() {
		currentState = new State();
	}

	public State getCurrentState() {
		return currentState;
	}

	private void fireEvent(ModelChangeEvent event) {
		handlerManager.fireEvent(event);
	}

	public HandlerRegistration addModelChangeHandler(ModelChangeHandler handler) {
		return handlerManager.addHandler(ModelChangeEvent.TYPE, handler);
	}
}
