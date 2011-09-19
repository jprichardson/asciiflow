//Copyright Lewis Hemens 2011
package com.lewish.asciiflow.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lewish.asciiflow.client.resources.AsciiflowCss;

@Singleton
public class Logo extends Composite {

	private static final String logoString = "<pre>"
			+ "    __   ____  ____  _  _    ____    _____ _        __ BETA!<br>"
			+ "   /  \\ | ___|| ___||_||_|  / ___|| |  _  | \\  __  / /<br>"
			+ "  / /\\ \\|_ \\  | |    _  _  / /  | | | | | |\\ \\/  \\/ /<br>"
			+ " /  __  \\_\\ \\ | |__ | || |/ __| | |_| |_| | \\      /<br>"
			+ "/__/  \\__\\___||____||_||_|_/    |____|____|  \\_/\\_/<br>"
			+ "</pre>";

	@Inject
	public Logo(AsciiflowCss css) {
		initWidget(new HTML(logoString));
		addStyleName(css.logo());
	}
}
