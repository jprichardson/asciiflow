package com.lewish.asciiflow.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.lewish.asciiflow.shared.State;

public interface StoreServiceAsync {
	public void saveState(State state, AsyncCallback<Long> callback);
	public void loadState(Long id, Integer editCode, AsyncCallback<State> callback);
}
