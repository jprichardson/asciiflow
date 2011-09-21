package com.lewish.asciiflow.client;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class DrawLayout extends SidebarLayout {

	private final Canvas canvas;
	private final ToolPanel toolPanel;
	private final InfoPanel infoPanel;
	private final StoreWidget storeWidget;
	private final MenuPanel menuPanel;
	private final ExportWidget exportWidget;
	private final ImportWidget importWidget;

	@Inject
	public DrawLayout(Canvas canvas, ToolPanel toolPanel, InfoPanel infoPanel,
			StoreWidget storeWidget, MenuPanel menuPanel, ExportWidget exportWidget,
			ImportWidget importWidget) {
		this.canvas = canvas;
		this.toolPanel = toolPanel;
		this.infoPanel = infoPanel;
		this.storeWidget = storeWidget;
		this.menuPanel = menuPanel;
		this.exportWidget = exportWidget;
		this.importWidget = importWidget;
	}

	@Override
	public Widget getContent() {
		FlowPanel flowPanel = new FlowPanel();
		flowPanel.add(canvas);
		flowPanel.add(infoPanel);
		return flowPanel;
	}

	@Override
	public Widget getSideContent() {
		return toolPanel;
	}

	@Override
	public Widget getHeaderContent() {
		FlowPanel panel = new FlowPanel();
		panel.add(storeWidget);
		panel.add(menuPanel);
		panel.add(exportWidget);
		panel.add(importWidget);
		return panel;
	}
}
