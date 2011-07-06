//Copyright Lewis Hemens 2011
package com.lewish.asciiflow.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.dellroad.lzma.client.LZMAByteArrayCompressor;

@PersistenceCapable
public class State implements Serializable {

	private static final long serialVersionUID = 8847057226414076746L;

	private final List<CellState> states = new ArrayList<CellState>();

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private long id;

	@Persistent
	private byte[] compressedState;

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

	public byte[] compress() {
		String s = "";
		for(CellState cellstate : states) {
			if(!s.equals("")) {
				s += "\n";
			}
			s += cellstate.toString();
		}
		compressedState = new LZMAByteArrayCompressor(s.getBytes()).getCompressedData();
		return compressedState;
	}

	public long getId() {
		return id;
	}
}
