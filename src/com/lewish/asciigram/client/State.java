package com.lewish.asciigram.client;

import java.util.ArrayList;
import java.util.List;

public class State {
	private final List<CellState> states = new ArrayList<CellState>();

	public void add (CellState cellState) {
		states.add(cellState);
	}

	public List<CellState> getStates() {
		return states;
	}
	public static class CellState {
		public final int x;
		public final int y;
		public final String value;
	
		public CellState(int x, int y, String value) {
			this.x = x;
			this.y = y;
			this.value = value;
		}
	}
}
