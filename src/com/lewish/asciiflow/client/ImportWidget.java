package com.lewish.asciiflow.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lewish.asciiflow.client.State.CellState;

@Singleton
public class ImportWidget extends MenuWidget {

	private TextArea textArea;
	private final Canvas canvas;
	private final Controller controller;
	private final HistoryManager historyManager;

	@Inject
	public ImportWidget(Canvas canvas, Controller controller, HistoryManager historyManager) {
		super(120);
		this.canvas = canvas;
		this.controller = controller;
		this.historyManager = historyManager;
	}

	@Override
	protected Widget getPanel() {
		textArea = new TextArea();
		textArea.addStyleName(CssStyles.ImportTextArea);
		FlowPanel panel = new FlowPanel();
		panel.add(textArea);
		panel.add(new Label("Paste in text and click import to load a drawing into the canvas."));
		panel.add(new Button("Import", new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				loadText(textArea.getText());
				hide();
			}
		}));
		return panel;
	}

	@Override
	protected void onShow() {
		textArea.setText("");
	}

	private void loadText(String text) {
		State state = new State();
		int maxWidth = 0;
		int height = 0;
		String[] lines = text.split("\n");
		for(String line : lines) {
			if(line.length() > maxWidth) {
				maxWidth = line.length();
			}
			for(int i = 0; i< line.length(); i++) {
				String val = String.valueOf(line.charAt(i));
				if (!val.equals(" ")) {
					state.add(new CellState(i, height, val));
				}
			}
			height++;
		}
		//Resize canvas
		while(canvas.getWidth() < maxWidth) {
			canvas.addColumn(controller);
		}
		while(canvas.getHeight() < height) {
			canvas.addRow(controller);
		}
		canvas.clearCells();
		canvas.refreshDraw();
		canvas.commitDraw();
		canvas.drawState(state);
		canvas.refreshDraw();
		historyManager.save(canvas.commitDraw());
	}
}
