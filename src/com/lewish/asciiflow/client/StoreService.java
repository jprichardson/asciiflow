package com.lewish.asciiflow.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.lewish.asciiflow.shared.AccessException;
import com.lewish.asciiflow.shared.BatchStoreQueryResult;
import com.lewish.asciiflow.shared.State;

@RemoteServiceRelativePath("../store")
public interface StoreService extends RemoteService {
	public State saveState(State state) throws AccessException;
	public State loadState(Long id, Integer editCode);
	public BatchStoreQueryResult loadTenStates(String cursorString);
}
