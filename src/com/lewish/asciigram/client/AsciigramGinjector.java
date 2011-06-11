//Copyright Lewis Hemens 2011
package com.lewish.asciigram.client;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

@GinModules(AsciigramModule.class)
public interface AsciigramGinjector extends Ginjector {

	Controller getController();
	Canvas getCanvas();
	InfoPanel getInfoPanel();
	MenuPanel getMenuPanel();
	ToolPanel getToolPanel();
	ExportPanel getExportPanel();
}
