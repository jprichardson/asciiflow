package com.lewish.asciiflow.client.social;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.inject.Inject;
import com.lewish.asciiflow.client.resources.AsciiflowCss;

public class PlusOneWidget extends Composite {

	@Inject
	public PlusOneWidget(AsciiflowCss css) {
		HTML html = new HTML("<g:plusone size=\"tall\"></g:plusone>");
		html.getElement().setId("plusone-widget");
		html.setStyleName(css.plusOneWidget());
		initWidget(html);
	}
}