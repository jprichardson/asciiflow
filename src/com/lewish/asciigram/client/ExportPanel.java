package com.lewish.asciigram.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ExportPanel extends Composite {

	private final FlowPanel panel = new FlowPanel();
	private final TextArea textArea = new TextArea();
	private final Canvas canvas;

	@Inject
	public ExportPanel(Canvas canvas) {
		this.canvas = canvas;
		textArea.addStyleName(CssStyles.ExportTextArea);
		panel.addStyleName(CssStyles.ExportPanel);
		panel.add(textArea);
		Button close = new Button("Close", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		close.removeStyleName("gwt-Button");
		Label label = new Label(
				"Copy the text above for a text representation of your drawing");
		FlowPanel footer = new FlowPanel();
		footer.add(label);
		footer.add(close);
		footer.addStyleName(CssStyles.ExportRight);
		//panel.add(footer);
		initWidget(panel);
		hide();
	}

	public void show() {
		setHeight("100px");
		textArea.setFocus(true);
		Timer timer = new Timer() {
			@Override
			public void run() {
				textArea.setVisible(true);
				textArea.setText(canvas.getText());
				textArea.selectAll();
			}
		};
		timer.schedule(1000);
	}

	public void hide() {
		setHeight("0px");
		textArea.setVisible(false);
	}

	public void toggle() {
		if (textArea.isVisible()) {
			hide();
		} else {
			show();
		}
	}
}
