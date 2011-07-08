//Copyright Lewis Hemens 2011
package com.lewish.asciiflow.client;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

@GinModules(AsciiflowModule.class)
public interface AsciiflowGinjector extends Ginjector {

	Canvas getCanvas();
	InfoPanel getInfoPanel();
	MenuPanel getMenuPanel();
	ToolPanel getToolPanel();
	ExportWidget getExportPanel();
	ImportWidget getImportPanel();
	SaveWidget getSavePanel();
	StorageHelper getStorageHelper();
}
