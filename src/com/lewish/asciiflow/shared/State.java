//Copyright Lewis Hemens 2011
package com.lewish.asciiflow.shared;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import org.dellroad.lzma.client.LZMAByteArrayCompressor;

@PersistenceCapable
public class State {

	private final List<CellState> states = new ArrayList<CellState>();

	@Persistent
	private String compressedState;

	public void add (CellState cellState) {
		states.add(cellState);
	}

	public List<CellState> getStates() {
		return states;
	}

	public static class CellState {
		public int x;
		public int y;
		public String value;
	
		public CellState(int x, int y, String value) {
			this.x = x;
			this.y = y;
			this.value = value;
		}

		public String toString() {
			return x + ":" + y + ":" + value;
		}
	}

	public String compress() {
		String s = "";
		for(CellState cellstate : states) {
			s += states.toString();
		}
		//new LZMAByteArrayCompressor(
		return "";
	}
}
