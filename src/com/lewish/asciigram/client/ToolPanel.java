package com.lewish.asciigram.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lewish.asciigram.client.tools.BoxTool;
import com.lewish.asciigram.client.tools.EraseTool;
import com.lewish.asciigram.client.tools.LineTool;
import com.lewish.asciigram.client.tools.SelectTool;
import com.lewish.asciigram.client.tools.TextTool;
import com.lewish.asciigram.client.tools.TitledBoxTool;
import com.lewish.asciigram.client.tools.Tool;

@Singleton
public class ToolPanel extends Composite {

	private final List<Tool> tools = new ArrayList<Tool>();
	private final FlowPanel panel = new FlowPanel();

	private ToolButton currentButton;
	private Controller controller;
	private InfoPanel infoPanel;

	@Inject
	public ToolPanel(final Controller controller, final Canvas canvas, InfoPanel infoPanel) {
		this.controller = controller;
		this.infoPanel = infoPanel;
		tools.add(new SelectTool(canvas));
		tools.add(new BoxTool(canvas));
		tools.add(new TitledBoxTool(canvas));
		tools.add(new LineTool(canvas, false));
		tools.add(new LineTool(canvas, true));
		tools.add(new TextTool(canvas));
		tools.add(new EraseTool(canvas));
		

		for (final Tool tool : tools) {
			final ToolButton button = new ToolButton(tool);
			button.addStyleName(CssStyles.ToolButton);
			panel.add(button);
			if(tool instanceof BoxTool) {
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
			Image image = new Image(tool.getImageUrl());
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
