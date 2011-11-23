package com.lewish.asciiflow.client;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lewish.asciiflow.client.common.PlainLayout;
import com.lewish.asciiflow.client.resources.AsciiflowCss;
import com.lewish.asciiflow.shared.UriConstants;

@Singleton
public class AboutLayout extends PlainLayout {

	private final HTML content = new HTML(
					"Hi welcome to Asciiflow!<br><br>"
					+ "My primary goal when creating this website was to provide a simple way to embed flow diagrams or drawings within emails, blogs, text based media. However, since starting development it has started to become more than this, with a link to export drawings to <a href=\"http://ditaa.org/ditaa/\">DITAA</a>, as well as sharing and saving.<br>"
					+ "I intend to expand the site further to incorporate a gallery, and when core work has been competed I have every intention of making this entirely open source!<br><br>"
					+ "For development updates you can view the <a href=\""
					+ UriConstants.BLOG_URI
					+ "\">Blog</a>.<br><br>"
					+ "The site is made in GWT and runs on Google AppEngine, for best results use Chrome to draw large diagrams, as it is very heavy on JavaScript.<br><br>"
					+ "The code is now open-source and feel free to branch, edit, contribute at <a href=\"https://github.com/lewish/asciiflow\"> github</a>.<br><br>"
					+ "About me, I have been working at Google for a year as a software engineer in AdSense, so this is a bit of a pet project. My primary interest is in web apps, you can contact me via:<br>"
					+ "<a href=\"http://uk.linkedin.com/pub/lewis-hemens/37/10/251\">LinkedIn</a><br>"
					+ "<a href=\"mailto:lewis@asciiflow.com\">Mail</a><br>"
					+ "<a href=\"http://www.facebook.com/NW4J3SUS\">Facebook</a><br><br>"
					+ "Lewis Hemens");

	@Inject
	public AboutLayout(AsciiflowCss css) {
		content.setStyleName(css.aboutContent());
	}

	@Override
	public Widget getContent() {
		return content;
	}
}
