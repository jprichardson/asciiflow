//Copyright Lewis Hemens 2011
package com.lewish.asciigram.client;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextArea;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ExportPanel extends Composite {

	private final FlowPanel resizePanel = new FlowPanel();
	private final FlowPanel hidePanel = new FlowPanel();
	private final TextArea textArea = new TextArea();
	private final Canvas canvas;

	@Inject
	public ExportPanel(Canvas canvas) {
		this.canvas = canvas;
		textArea.addStyleName(CssStyles.ExportTextArea);
		resizePanel.addStyleName(CssStyles.ExportPanel);

		hidePanel.add(textArea);
		hidePanel.add(new HTML("Copy the text above and use it in a <b>monospace</b> font"));
		resizePanel.add(hidePanel);
		initWidget(resizePanel);
		hide();
	}

	public void show(final boolean html) {
		setHeight("100px");
		Timer timer = new Timer() {
			@Override
			public void run() {
				hidePanel.setVisible(true);
				textArea.setFocus(true);
				textArea.selectAll();
			}
		};
		textArea.setText(getText(html));
		timer.schedule(500);
	}

	public void hide() {
		setHeight("0px");
		hidePanel.setVisible(false);
	}

	public void toggle(boolean html) {
		if (hidePanel.isVisible()) {
			hide();
		} else {
			show(html);
		}
	}
	
	public String getText(boolean html) {
		String text = html ? "<pre>" : "";
		for (int y = 0; y < canvas.getHeight(); y++) {
			String rowText = "";
			for (int x = canvas.getWidth()-1; x >= 0 ; x--) {
				Cell cell = canvas.getCell(x, y);
				if (rowText.equals("") && cell.getValue() == null)
					continue;
				rowText = (html ? cell.getHTML() : cell.getText()) + rowText;
			}
			text += rowText + (rowText.equals("") ? "" : "\n");
		}
		return text + (html ? "</pre>" : "");
	}
}
