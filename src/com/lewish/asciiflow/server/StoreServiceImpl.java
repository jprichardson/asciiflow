package com.lewish.asciiflow.server;

import java.util.Random;

import javax.jdo.PersistenceManager;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.lewish.asciiflow.client.StoreService;
import com.lewish.asciiflow.shared.State;

public class StoreServiceImpl extends RemoteServiceServlet implements StoreService {

	private static final long serialVersionUID = -3286308257185371845L;

	private Random random = new Random();

	@Override
	public Long saveState(State state) {
		PersistenceManager pm = Persistence.getManager();
		if(!state.hasId()) {
			//TODO Check collisions or do some math.
			state.setId(getUID());
		}
		if (state.isCompressed()) {
			state.blobify();
			try {
				state = pm.makePersistent(state);
				return state.getId();
			} finally {
				pm.close();
			}
		} else {
			return null;
		}
	}

	private Long getUID() {
		Long id = 0l;
		while(id <= 0) {
			id = random.nextLong();
		}
		return id;
	}

	@Override
	public State loadState(Long id, Integer editCode) {
		PersistenceManager pm = Persistence.getManager();
		State state;
		try {
			state = pm.getObjectById(State.class, id);
		} catch (Exception e) {
			return null;
		} finally {
			pm.close();
		}
		state.unblobify();
		return state;
	}
}
