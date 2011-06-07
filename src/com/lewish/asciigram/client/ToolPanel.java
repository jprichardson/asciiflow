package com.lewish.asciigram.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;

public class ToolPanel extends Composite {

	private final List<Tool> tools = new ArrayList<Tool>();
	private final List<ToggleButton> buttons = new ArrayList<ToggleButton>();
	private final FlowPanel panel = new FlowPanel();
	private Controller controller;

	public ToolPanel(final Controller controller, final Canvas canvas) {
		this.controller = controller;

		tools.add(new BoxTool(canvas));
		tools.add(new LineTool(canvas, false));
		tools.add(new LineTool(canvas, true));
		tools.add(new TextTool(canvas));
		tools.add(new EraseTool(canvas));

		for (final Tool tool : tools) {
			final FocusPanel button = new FocusPanel(tool.getLabel());
			button.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					selectButton(button, tool);
				}
			});
			button.addStyleName(CssStyles.ToolButton);
			buttons.add(button);
			panel.add(button);
		}

		selectButton(buttons.get(0), tools.get(0));
		panel.add(new HTML("<br>"));
		panel.add(new Button("Add row", new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				canvas.addRow();
			}
		}));
		panel.add(new Button("Add column", new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				canvas.addColumn();
			}
		}));
		panel.add(new Button("Clear cells", new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				//TODO Add confirmation.
				canvas.clearCells();
			}
		}));
		panel.add(new HTML("<br>"));
		panel.add(new ExportWidget(canvas));

		panel.setStyleName(CssStyles.ToolPanel);
		initWidget(panel);
	}

	public void selectButton(Widget widget, Tool tool) {
		for(ToggleButton b : buttons) {
			b.setDown(false);
		}
		button.setDown(true);
		controller.setTool(tool);
	}

	private class ToolButton extends Composite {
		public ToolButton(final Tool tool) {
			FlowPanel panel = new FlowPanel();
			Image image = new Image(tool.getImageUrl());
			image.addClickHandler((new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					selectButton(panel, tool);
				}
			}));
			panel.add(image);
			initWidget(panel);
		}
	}
}
