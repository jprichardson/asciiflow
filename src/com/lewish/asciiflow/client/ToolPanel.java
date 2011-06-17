//Copyright Lewis Hemens 2011
package com.lewish.asciiflow.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lewish.asciiflow.client.resources.AsciiflowClientBundle;
import com.lewish.asciiflow.client.tools.ArrowTool;
import com.lewish.asciiflow.client.tools.BaseBoxTool;
import com.lewish.asciiflow.client.tools.BoxTool;
import com.lewish.asciiflow.client.tools.EraseTool;
import com.lewish.asciiflow.client.tools.LineTool;
import com.lewish.asciiflow.client.tools.SelectTool;
import com.lewish.asciiflow.client.tools.TextTool;
import com.lewish.asciiflow.client.tools.TitledBoxTool;

@Singleton
public class ToolPanel extends Composite {

	private final List<Tool> tools = new ArrayList<Tool>();
	private final FlowPanel panel = new FlowPanel();

	private ToolButton currentButton;
	private Controller controller;
	private InfoPanel infoPanel;

	@Inject
	public ToolPanel(AsciiflowClientBundle imageBundle, final Controller controller, final Canvas canvas, InfoPanel infoPanel,
			SelectTool selectTool, BoxTool boxTool, TitledBoxTool titledBoxTool, LineTool lineTool, ArrowTool arrowTool, TextTool textTool, EraseTool eraseTool) {
		this.controller = controller;
		this.infoPanel = infoPanel;
		tools.add(selectTool);
		tools.add(boxTool);
		tools.add(titledBoxTool);
		tools.add(lineTool);
		tools.add(arrowTool);
		tools.add(textTool);
		tools.add(eraseTool);
		

		for (final Tool tool : tools) {
			final ToolButton button = new ToolButton(tool);
			button.addStyleName(CssStyles.ToolButton);
			panel.add(button);
			if(tool instanceof BaseBoxTool) {
				selectButton(button, tool);
			}
		}

		
		panel.addStyleName(CssStyles.ToolPanel);
		initWidget(panel);
	}

	public void selectButton(ToolButton button, Tool tool) {
		if (currentButton != null) {
			currentButton.removeStyleName(CssStyles.SelectedTool);
		}
		currentButton = button;
		currentButton.addStyleName(CssStyles.SelectedTool);
		controller.setTool(tool);
		infoPanel.setText(tool.getDescription());
	}

	private class ToolButton extends Composite {
		public ToolButton(final Tool tool) {
			final FlowPanel panel = new FlowPanel();
			Image image = new Image(tool.getImageResource());
			image.addClickHandler((new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					selectButton(ToolButton.this, tool);
				}
			}));
			panel.add(image);
			initWidget(panel);
		}
	}
}
