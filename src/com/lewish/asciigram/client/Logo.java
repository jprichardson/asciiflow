//Copyright Lewis Hemens 2011
package com.lewish.asciigram.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;

public class Logo extends Composite {

	private static final String logoString = "&nbsp;&nbsp;&nbsp;&nbsp;__&nbsp;&nbsp;&nbsp;____&nbsp;&nbsp;____&nbsp;&nbsp;_&nbsp;&nbsp;_&nbsp;&nbsp;&nbsp;&nbsp;____&nbsp;&nbsp;&nbsp;&nbsp;_____ _&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;__&nbsp;BETA!<br>"
			+ "&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;\\ | ___|| ___||_||_|&nbsp;&nbsp;/ ___|| |&nbsp;&nbsp;_&nbsp;&nbsp;| \\&nbsp;&nbsp;__&nbsp;&nbsp;/ /<br>"
			+ "&nbsp;&nbsp;/ /\\ \\|_ \\&nbsp;&nbsp;| |&nbsp;&nbsp;&nbsp;&nbsp;_&nbsp;&nbsp;_&nbsp;&nbsp;/ /&nbsp;&nbsp;| | | | | |\\ \\/&nbsp;&nbsp;\\/ /<br>"
			+ "&nbsp;/&nbsp;&nbsp;__&nbsp;&nbsp;\\_\\ \\ | |__ | || |/ __| | |_| |_| | \\&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;/<br>"
			+ "/__/&nbsp;&nbsp;\\__\\___||____||_||_|_/&nbsp;&nbsp;&nbsp;&nbsp;|____|____|&nbsp;&nbsp;\\_/\\_/<br>";

	public Logo() {
		initWidget(new HTML(logoString));
		addStyleName(CssStyles.Logo);
	}
}
