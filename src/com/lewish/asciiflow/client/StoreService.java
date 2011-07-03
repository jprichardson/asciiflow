package com.lewish.asciiflow.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.lewish.asciiflow.shared.State;

public interface StoreService extends RemoteService {
	public String saveState(State state);
}
