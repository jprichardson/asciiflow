package com.lewish.asciiflow.client.social;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.inject.Inject;
import com.lewish.asciiflow.client.resources.AsciiflowCss;

public class TwitterWidget extends Composite {

	@Inject
	public TwitterWidget(AsciiflowCss css) {
		HTML html = new HTML("<iframe src=\"http://api.tweetmeme.com/button.js?url=http:%2F%2Fasciiflow.com&style=normal" + 
		"&service=bit.ly\" scrolling=\"no\" frameborder=\"0\" width=\"50\" height=\"61\" ></iframe>");
		html.setStyleName(css.twitterWidget());
		initWidget(html);
	}
}
