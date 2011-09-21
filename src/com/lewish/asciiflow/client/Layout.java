package com.lewish.asciiflow.client;

import com.google.gwt.user.client.ui.Panel;
import com.lewish.asciiflow.client.resources.AsciiflowCss;

public interface Layout {
	abstract Panel buildLayout(AsciiflowCss css, FooterWidget footerWidget);
}
