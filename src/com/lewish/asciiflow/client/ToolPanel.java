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
import com.lewish.asciiflow.client.tools.BoxTool;
import com.lewish.asciiflow.client.tools.DottedLineTool;
import com.lewish.asciiflow.client.tools.EraseTool;
import com.lewish.asciiflow.client.tools.FreeformTool;
import com.lewish.asciiflow.client.tools.LineTool;
import com.lewish.asciiflow.client.tools.SelectTool;
import com.lewish.asciiflow.client.tools.TextTool;
import com.lewish.asciiflow.client.tools.TitledBoxTool;

@Singleton
public class ToolPanel extends Composite {

	private final List<Tool> tools = new ArrayList<Tool>();
	private final FlowPanel panel = new FlowPanel();

	private final Controller controller;
	private final InfoPanel infoPanel;
	private final AsciiflowClientBundle clientBundle;

	private ToolButton currentButton;

	@Inject
	public ToolPanel(AsciiflowClientBundle imageBundle,
			final Controller controller, final Canvas canvas,
			InfoPanel infoPanel, SelectTool selectTool, BoxTool boxTool,
			TitledBoxTool titledBoxTool, LineTool lineTool, DottedLineTool dottedLineTool,
			ArrowTool arrowTool, TextTool textTool, EraseTool eraseTool,
			FreeformTool freeformTool) {
		this.controller = controller;
		this.infoPanel = infoPanel;
		this.clientBundle = imageBundle;
		// TODO: Provide via injector.
		tools.add(selectTool);
		tools.add(boxTool);
		tools.add(titledBoxTool);
		tools.add(lineTool);
		tools.add(arrowTool);
		tools.add(textTool);
		tools.add(eraseTool);
		tools.add(freeformTool);

		for (final Tool tool : tools) {
			final ToolButton button = new ToolButton(tool);
			button.addStyleName(imageBundle.css().toolButton());
			panel.add(button);
			if (tool instanceof BoxTool) {
				selectButton(button, tool);
			}
		}

		panel.addStyleName(imageBundle.css().toolPanel());
		initWidget(panel);
	}

	public void selectButton(ToolButton button, Tool tool) {
		if (currentButton != null) {
			currentButton.removeStyleName(clientBundle.css().selectedTool());
		}
		currentButton = button;
		currentButton.addStyleName(clientBundle.css().selectedTool());
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
