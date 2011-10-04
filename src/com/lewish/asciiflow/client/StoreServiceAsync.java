package com.lewish.asciiflow.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.lewish.asciiflow.shared.BatchStoreQueryResult;
import com.lewish.asciiflow.shared.State;

public interface StoreServiceAsync {
	public void saveState(State state, AsyncCallback<State> callback);
	public void loadState(Long id, Integer editCode, AsyncCallback<State> callback);
	public void loadTenStates(String cursorString, AsyncCallback<BatchStoreQueryResult> callback);
}
