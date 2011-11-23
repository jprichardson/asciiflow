package com.lewish.asciiflow.client.social;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.inject.Inject;
import com.lewish.asciiflow.client.resources.AsciiflowCss;

public class FacebookWidget extends Composite {

	@Inject
	public FacebookWidget(AsciiflowCss css) {
		HTML html = new HTML("<fb:like href=\"http://www.asciiflow.com\" send=\"false\" " +
				"layout=\"box_count\" show_faces=\"true\" width=\"80\" font=\"arial\"></fb:like>");
		html.getElement().setId("facebook-widget");
		html.setStyleName(css.facebookWidget());
		initWidget(html);
	}

	@Override
	protected void onAttach() {
		super.onAttach();
		parseFBTags();
	}

	/**
	 * Required to ensure the facebook widget renders.
	 */
	private native void parseFBTags() /*-{
		if(!!$wnd.FB) {
			$wnd.FB.XFBML.parse(document.getElementById('facebook-widget'));
		}
	}-*/;
}
