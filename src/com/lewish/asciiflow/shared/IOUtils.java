package com.lewish.asciiflow.shared;

import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.lewish.asciiflow.client.Canvas;

public class IOUtils {

	public static String export(Canvas canvas, boolean html) {
		String text = html ? "<pre>" : "";
		for (int y = 0; y < canvas.getHeight(); y++) {
			String rowText = "";
			for (int x = canvas.getWidth() - 1; x >= 0; x--) {
				String value = canvas.getValue(x, y);
				if (rowText.equals("") && value == null)
					continue;
				String out = value == null ? (html ? "&nbsp;" : " ") : (html ? SafeHtmlUtils
						.htmlEscape(value) : value);
				rowText = out + rowText;
			}
			text += rowText + "\n";
		}
		return text + (html ? "</pre>" : "");
	}

	
}
