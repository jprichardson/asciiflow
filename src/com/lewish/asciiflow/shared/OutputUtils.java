package com.lewish.asciiflow.shared;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gwt.safehtml.shared.SafeHtmlUtils;

public class OutputUtils {

	private static final String HTML_HEADER = "<pre>";
	private static final String HTML_FOOTER = "</pre>";
	private static final String BREAK = "\n";
	private static final String SPACE = " ";

	public static String toHtml(CellStateMap cellStateMap) {
		StringBuilder builder = buildOutput(cellStateMap, true);
		builder.insert(0, HTML_HEADER);
		builder.append(HTML_FOOTER);
		return builder.toString();
	}

	public static String toText(CellStateMap cellStateMap) {
		return buildOutput(cellStateMap, false).toString();
	}

	private static StringBuilder buildOutput(CellStateMap cellStateMap, boolean html) {
		StringBuilder builder = new StringBuilder();
		List<CellState> states = new ArrayList<CellState>(cellStateMap.getCellStates());
		Collections.sort(states);
		int x = 0;
		int y = 0;
		for (CellState cellState : states) {
			while (y < cellState.y) {
				// New Lines
				builder.append(BREAK);
				y++;
				x = 0;
			}
			while (x < cellState.x) {
				// Fill spaces
				builder.append(SPACE);
				x++;
			}
			builder.append(html ? SafeHtmlUtils.htmlEscape(cellState.value) : cellState.value);
			x++;
		}
		return builder;
	}
}
