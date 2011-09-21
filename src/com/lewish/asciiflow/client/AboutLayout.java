package com.lewish.asciiflow.client;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class AboutLayout extends PlainLayout {

	private static final HTML CONTENT = new HTML(
			"THis is my website woop woop"
			);

	@Override
	public Widget getContent() {
		return CONTENT;
	}
}
