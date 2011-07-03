package com.lewish.asciiflow.client.social;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;

public class TwitterWidget extends Composite {

	public TwitterWidget() {
		HTML html = new HTML("<a href=\"http://twitter.com/share\" class=\"twitter-share-button\" " + 
				"data-count=\"horizontal\" id = \"twitter-widget\">Tweet</a><script type=\"text/javascript\" src=\"http://platform.twitter.com/widgets.js\"></script>");
		//html.getElement().setId("twitter-widget");
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
