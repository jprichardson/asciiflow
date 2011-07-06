package com.lewish.asciiflow.server;

import javax.jdo.PersistenceManager;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.lewish.asciiflow.client.StoreService;
import com.lewish.asciiflow.shared.State;

public class StoreServiceImpl extends RemoteServiceServlet implements StoreService {

	private static final long serialVersionUID = -3286308257185371845L;

	@Override
	public Long saveState(State state) {
		PersistenceManager pm = Persistence.getManager();
		state.compress();
		try {
			state = pm.makePersistent(state);
		} finally {
			pm.close();
		}
		return state.getId();
	}

	@Override
	public State loadState(Long id) {
		PersistenceManager pm = Persistence.getManager();
		try {
			State state = pm.getObjectById(State.class, id);
			return state;
		} catch (Exception e) {
			return null;
		} finally {
			pm.close();
		}
	}
}
