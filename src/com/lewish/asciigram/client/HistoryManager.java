package com.lewish.asciigram.client;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class HistoryManager {

	private static HistoryManager instance;

	private final Canvas canvas;

	private List<String[][]> states = new ArrayList<String[][]>();
	private int maxHistory = 100;
	private int currentState = -1;

	@Inject
	public HistoryManager(Canvas canvas) {
		this.canvas = canvas;
		if(instance == null) {
			instance = this;
		}
		save();
	}

	public void save() {
		if(currentState + 1 == states.size() && states.size() >= maxHistory) {
			states.remove(0);
		} else {
			//Erase old states
			while(states.size() > currentState + 1) {
				states.remove(states.size()-1);
			}
			currentState++;
		}
		states.add(canvas.getState());
	}

	public void undo() {
		if(currentState > 0) {
			canvas.loadState(states.get(--currentState));
		}
	}

	public void redo() {
		if(states.size() > currentState + 1) {
			canvas.loadState(states.get(++currentState));
		}
	}

	public static HistoryManager get() {
		return instance;
	}
}
