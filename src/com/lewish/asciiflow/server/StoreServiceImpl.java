package com.lewish.asciiflow.server;

import java.util.Random;

import javax.jdo.PersistenceManager;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.lewish.asciiflow.client.StoreService;
import com.lewish.asciiflow.shared.AccessException;
import com.lewish.asciiflow.shared.State;

public class StoreServiceImpl extends RemoteServiceServlet implements StoreService {

	private static final long serialVersionUID = -3286308257185371845L;

	private Random random = new Random();

	@Override
	public State saveState(State state) throws AccessException {
		PersistenceManager pm = Persistence.getManager();
		if(!state.hasId()) {
			//TODO Check collisions or do some math.
			state.setId(generateId());
			state.setEditCode(generateEditCode());
		} else {
			State loadState = fetchState(state.getId());
			if(!loadState.getEditCode().equals(state.getEditCode())) {
				throw new AccessException(state);
			}
		}
		
		if (state.isCompressed()) {
			state.blobify();
			try {
				state = pm.makePersistent(state);
				return state;
			} finally {
				pm.close();
			}
		} else {
			return null;
		}
	}

	private Long generateId() {
		Long id = 0l;
		while(id <= 0) {
			id = random.nextLong();
		}
		return id;
	}

	private Integer generateEditCode() {
		Integer code = 0;
		while(code <= 0) {
			code = random.nextInt();
		}
		return code;
	}

	@Override
	public State loadState(Long id, Integer editCode) {
		State state = fetchState(id);
		//Do not return the edit code unless it is valid.
		if (!editCode.equals(state.getEditCode())) {
			state.setEditCode(0);
		}
		state.unblobify();
		return state;
	}

	private State fetchState(Long id) {
		PersistenceManager pm = Persistence.getManager();
		State state;
		try {
			state = pm.getObjectById(State.class, id);
		} catch (Exception e) {
			return null;
		} finally {
			pm.close();
		}
		return state;
	}
}
