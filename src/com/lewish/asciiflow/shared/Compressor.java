package com.lewish.asciiflow.shared;

import java.util.Map.Entry;

public abstract class Compressor {

	public abstract void compress(final State state, Callback callback);
	public abstract void uncompress(final State state, Callback callback);

	protected byte[] preProcessCompress(final State state) {
		String s = "";
		for (Entry<String, CellState> entry : state.getCellStateMap().getMap().entrySet()) {
			if (!s.equals("")) {
				s += "\n";
			}
			s += entry.getKey() + ":" + entry.getValue().value;
		}
		return s.getBytes();
	}

	protected void postProcessUncompress(byte[] data, State state) {
		String s = new String(data);
		String[] split = s.split("\n");
		state.getCellStateMap().getMap().clear();
		for (String line : split) {
			if (line.matches("\\d+:\\d+:.*")) {
				state.getCellStateMap().add(CellState.fromString(line));
			}
		}
	}

	public static interface Callback {
		public void onFinish(boolean result);
	}
}
