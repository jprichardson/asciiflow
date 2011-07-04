package com.lewish.asciiflow.client.social;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;

public class TwitterWidget extends Composite {

	public TwitterWidget() {
		/*
		HTML html = new HTML("<a href=\"http://twitter.com/share\" class=\"twitter-share-button\" " + 
				"data-count=\"horizontal\" id = \"twitter-widget\">Tweet</a><script type=\"text/javascript\" src=\"http://platform.twitter.com/widgets.js\"></script>");
		html.getElement().setId("twitter-widget");
		*/
		HTML html = new HTML("<iframe src=\"http://api.tweetmeme.com/button.js?url=http:%2F%2Fasciiflow.com&style=normal" + 
		"&service=bit.ly\" scrolling=\"no\" frameborder=\"0\" width=\"50\" height=\"61\" ></iframe>");
		html.setStyleName("twitter-widget");
		initWidget(html);
	}

	@Override
	protected void onAttach() {
		super.onAttach();
		//parseTwitterTags();
	}

	/*
	private native void parseTwitterTags() /*-{
		var tweet_button = new $wnd.twttr.TweetButton(document.getElementById("twitter-widget"));
		tweet_button.render();
	}-*/;
}
