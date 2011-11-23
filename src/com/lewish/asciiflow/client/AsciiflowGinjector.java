package com.lewish.asciiflow.client;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.lewish.asciiflow.client.resources.AsciiflowCss;

@GinModules(AsciiflowModule.class)
public interface AsciiflowGinjector extends Ginjector {
	AsciiflowCss getCss();
	ContentFrame getContentFrame();
	ActivityController getActivityController();
	ActivityMenu getActivityMenu();
}
