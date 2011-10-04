package com.lewish.asciiflow.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.datanucleus.store.appengine.query.JDOCursorHelper;

import com.google.appengine.api.datastore.Cursor;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.lewish.asciiflow.client.StoreService;
import com.lewish.asciiflow.shared.AccessException;
import com.lewish.asciiflow.shared.BatchStoreQueryResult;
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
		return state;
	}

	@Override
	public BatchStoreQueryResult loadTenStates(String cursorString) {
		PersistenceManager pm = Persistence.getManager();
		Query query = pm.newQuery(State.class);
		query.setRange(0, 10);
		if (cursorString != null) {
			Cursor cursor = Cursor.fromWebSafeString(cursorString);
	        Map<String, Object> extensionMap = new HashMap<String, Object>();
	        extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
	        query.setExtensions(extensionMap);
		}
		@SuppressWarnings("unchecked")
		List<State> states = (List<State>) query.execute();
		Cursor cursor = JDOCursorHelper.getCursor(states);
		String newCursorString = cursor.toWebSafeString();
		return new BatchStoreQueryResult(states, newCursorString);
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
