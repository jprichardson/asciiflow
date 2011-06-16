//Copyright Lewis Hemens 2011
package com.lewish.asciigram.client;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class HistoryManager {

	private static HistoryManager instance;

	private final Canvas canvas;

	private List<State> undoStates = new ArrayList<State>();
	private int maxHistory = 100;
	private int currentState = -1;

	@Inject
	public HistoryManager(Canvas canvas) {
		this.canvas = canvas;
		if(instance == null) {
			instance = this;
		}
	}

	public void save(State state) {
		if(undoStates.size() >= maxHistory && currentState + 1 == undoStates.size()) {
			undoStates.remove(0);
		} else {
			//Erase old states
			while(undoStates.size() > currentState + 1) {
				undoStates.remove(undoStates.size()-1);
			}
			currentState++;
		}
		undoStates.add(state);
	}

	public void undo() {
		if(currentState >= 0) {
			canvas.drawState(undoStates.get(currentState));
			canvas.refreshDraw();
			undoStates.set(currentState--, canvas.commitDraw());
		}
	}

	public void redo() {
		if(undoStates.size() > currentState + 1) {
			canvas.drawState(undoStates.get(++currentState));
			canvas.refreshDraw();
			undoStates.set(currentState, canvas.commitDraw());
		}
	}

	public static HistoryManager get() {
		return instance;
	}
}
