package com.lewish.asciiflow.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lewish.asciiflow.client.resources.AsciiflowCss;

/**
 * TODO: Provide logic to show different diagram depending on current domain {asciiflow/asciidraw}.
 * 
 * @author lewis
 */
@Singleton
public class Logo extends Composite {

	private static final String logoString = "<pre>"
			+ "    __   ____  ____  _  _    ____    _____ _        __<br>"
			+ "   /  \\ | ___|| ___||_||_|  / ___|| |  _  | \\  __  / /<br>"
			+ "  / /\\ \\|_ \\  | |    _  _  / /  | | | | | |\\ \\/  \\/ /<br>"
			+ " /  __  \\_\\ \\ | |__ | || |/ __| | |_| |_| | \\      /<br>"
			+ "/__/  \\__\\___||____||_||_|_/    |____|____|  \\_/\\_/<br>"
			+ "</pre>";

	/*
	private static final String logoString_Draw = "<pre>"
			+ "    __   ____  ____  _  _  ___  ____   __ __        __<br>"
			+ "   /  \\ | ___|| ___||_||_||   \\| __ | /  \\\\ \\  __  / /<br>"
			+ "  / /\\ \\|_ \\  | |    _  _ | |\\ \\ | _|/ /\\ \\\\ \\/  \\/ /<br>"
			+ " /  __  \\_\\ \\ | |__ | || || |/ /   \\/  __  \\\\      /<br>"
			+ "/__/  \\__\\___||____||_||_||___/|_|\\_\\_/  \\__\\\\_/\\_/<br>"
			+ "</pre>";
	*/

	@Inject
	public Logo(AsciiflowCss css) {
		initWidget(new HTML(logoString));
		addStyleName(css.logo());
	}
}
