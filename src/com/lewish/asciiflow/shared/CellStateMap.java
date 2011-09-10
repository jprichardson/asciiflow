package com.lewish.asciiflow.shared;

import java.util.Collection;
import java.util.HashMap;

public class CellStateMap {

	private final HashMap<String, CellState> maps = new HashMap<String, CellState>();

	public void add(CellState cellState) {
		String key = cellState.x + ":" + cellState.y;
		maps.put(key, cellState);
	}

	public void remove(CellState cellState) {
		String key = cellState.x + ":" + cellState.y;
		maps.remove(key);
	}

	public Collection<CellState> getCellStates() {
		return maps.values();
	}

	public HashMap<String, CellState> getMap() {
		return maps;
	}
}
