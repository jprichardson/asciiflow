package com.lewish.asciiflow.server;

import javax.jdo.PersistenceManager;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.lewish.asciiflow.client.StoreService;
import com.lewish.asciiflow.shared.State;

public class StoreServiceImpl extends RemoteServiceServlet implements StoreService {

	private static final long serialVersionUID = -3286308257185371845L;

	@Override
	public String saveState(State state) {
		PersistenceManager manager = Persistence.getManager();
		return "";
	}

}
