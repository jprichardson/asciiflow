package com.lewish.asciiflow.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lewish.asciiflow.shared.BatchStoreQueryResult;
import com.lewish.asciiflow.shared.Compressor;
import com.lewish.asciiflow.shared.State;
import com.lewish.asciiflow.shared.Compressor.Callback;

/**
 * A wrapper around the StoreServiceAsync that performs compression and decompression
 * of RPC data and provides simple callbacks to use.
 * 
 * @author lewis
 */
@Singleton
public class CompressedStoreServiceAsync {

	private final StoreServiceAsync service;
	private final Compressor compressor;

	@Inject
	public CompressedStoreServiceAsync(StoreServiceAsync service, Compressor compressor) {
		this.service = service;
		this.compressor = compressor;
	}

	public void loadState(Long id, Integer editCode, final LoadCallback callback) {
		service.loadState(id, editCode, new AsyncCallback<State>() {
			@Override
			public void onSuccess(final State result) {
				compressor.uncompress(result, new Callback() {
					@Override
					public void onFinish(boolean success) {
						callback.afterLoad(success, result);
					}
				});
			}

			@Override
			public void onFailure(Throwable caught) {
				callback.afterLoad(false, null);
			}
		});
	}

	public void loadTenStates(String cursorString, final BatchedLoadCallback callback) {
		service.loadTenStates(cursorString, new AsyncCallback<BatchStoreQueryResult>() {
			@Override
			public void onFailure(Throwable caught) {
				callback.afterLoad(false, null);
			}

			@Override
			public void onSuccess(final BatchStoreQueryResult result) {
				compressor.uncompress(result.getStates(), new Callback() {
					@Override
					public void onFinish(boolean success) {
						callback.afterLoad(success, result);
					}
				});
			}
		});
	}

	public void saveState(final State state, final SaveCallback callback) {
		compressor.compress(state, new Compressor.Callback() {
			@Override
			public void onFinish(boolean result) {
				if (!result) {
					callback.afterSave(false, null);
				} else {
					service.saveState(state, new AsyncCallback<State>() {
						@Override
						public void onSuccess(State result) {
							callback.afterSave(true, result);
						}
						@Override
						public void onFailure(Throwable caught) {
							callback.afterSave(false, null);
						}
					});
				}
			}
		});
	}

	public static interface SaveCallback {
		public void afterSave(boolean success, State state);
	}

	public static interface LoadCallback {
		public void afterLoad(boolean success, State state);
	}

	public static interface BatchedLoadCallback {
		public void afterLoad(boolean success, BatchStoreQueryResult result);
	}
}
