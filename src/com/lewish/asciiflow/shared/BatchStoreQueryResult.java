package com.lewish.asciiflow.shared;

import java.util.List;

public class BatchStoreQueryResult {
	private final List<State> states;
	private final String cursorString;

	public BatchStoreQueryResult(List<State> states, String cursorString) {
		this.states = states;
		this.cursorString  = cursorString;
	}

	public List<State> getStates() {
		return states;
	}

	public String getCursorString() {
		return cursorString;
	}
}
