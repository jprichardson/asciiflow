package com.lewish.asciiflow.shared;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;

public class Uri {

	private static final String NIGHTLY_HOST = "http://nightly.ascii-flow.appspot.com/";
	private static final String STABLE_HOST = "http://www.asciiflow.com/";

	private static final String BLOG_URI = "http://blog.asciiflow.com";
	private static final String MAILTO_URI = "mailto:lewis@asciiflow.com";

	public static String getHost() {
		String host = "http://" + Window.Location.getHost();
		if (!host.endsWith("/")) {
			host += "/";
		}
		return host;
	}

	public static String getNightlyUri() {
		return NIGHTLY_HOST;
	}
	public static String getStableUri() {
		return STABLE_HOST;
	}

	public static String getBlogUri() {
		return BLOG_URI;
	}

	public static String getMailtoUri() {
		return MAILTO_URI;
	}

	public static String getDocumentLink(Long id, Integer editCode) {
		String link = getHost() + "#" + String.valueOf(id);
		if (editCode != null  && editCode != 0) {
			link += "/" + editCode;
		}
		return link;
	}

	public static String getInlineTextLink(Long id) {
		return getHost() + "inline?id=" + id.toString();
	}

	public static String getInlineHtmlLink(Long id) {
		return getHost() + "inline?html=true&id=" + id.toString();
	}

	public static boolean isNightly() {
		return Window.Location.getHref().contains(
				NIGHTLY_HOST.subSequence(7, NIGHTLY_HOST.length() - 2));
	}

	public static boolean isStable() {
		return Window.Location.getHref().contains(
				STABLE_HOST.subSequence(7, STABLE_HOST.length() - 2));
	}

	public static boolean isGalleryMode() {
		return History.getToken().contains("gallery");
	}
}