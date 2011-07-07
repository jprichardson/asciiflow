package com.lewish.asciiflow.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.lewish.asciiflow.shared.State;

@RemoteServiceRelativePath("../store")
public interface StoreService extends RemoteService {
	public Long saveState(State state);
	public State loadState(Long id);
}
