package com.lewish.asciiflow.shared;

import java.io.Serializable;
import java.util.List;

public class BatchStoreQueryResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2023771782594006348L;

	private List<State> states;
	private String cursorString;

	public BatchStoreQueryResult() {
	}

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
