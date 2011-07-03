package com.lewish.asciiflow.client.social;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.lewish.asciiflow.client.CssStyles;

public class FacebookWidget extends Composite {

	public FacebookWidget() {
		HTML html = new HTML("<fb:like href=\"http://www.asciiflow.com\" send=\"false\" " +
				"layout=\"button_count\" width=\"100\" show_faces=\"true\" font=\"arial\"></fb:like>");
		html.getElement().setId("facebook-widget");
		html.setStyleName(CssStyles.FacebookWidget);
		initWidget(html);
	}

	@Override
	protected void onAttach() {
		super.onAttach();
		parseFBTags();
	}

	private native void parseFBTags() /*-{
		if(!!$wnd.FB) {
			$wnd.FB.XFBML.parse(document.getElementById('facebook-widget'));
		}
	}-*/;
}
