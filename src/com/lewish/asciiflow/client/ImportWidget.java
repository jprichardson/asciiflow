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
import com.lewish.asciiflow.client.resources.AsciiflowCss;
import com.lewish.asciiflow.client.tools.EraseTool;
import com.lewish.asciiflow.shared.CellState;
import com.lewish.asciiflow.shared.CellStateMap;

@Singleton
public class ImportWidget extends MenuWidget {

	private final Canvas canvas;
	private final HistoryManager historyManager;
	private final StoreModel storeModel;

	private TextArea textArea;

	@Inject
	public ImportWidget(Canvas canvas, HistoryManager historyManager, AsciiflowCss css, StoreModel storeModel) {
		super(120, css);
		this.canvas = canvas;
		this.historyManager = historyManager;
		this.storeModel = storeModel;
	}

	@Override
	protected Widget getPanel() {
		textArea = new TextArea();
		textArea.addStyleName(css.importTextArea());
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
		CellStateMap state = new CellStateMap();
		int height = 0;
		String[] lines = text.split("\n");
		for(String line : lines) {
			for(int i = 0; i< line.length(); i++) {
				String val = new String(Character.toChars(line.codePointAt(i)));
				if (!val.equals(" ")) {
					state.add(new CellState(i, height, val));
				}
			}
			height++;
		}
		storeModel.clearState();
		EraseTool.draw(canvas);
		canvas.drawCellStates(state);
		canvas.refreshDraw();
		historyManager.save(canvas.commitDraw());
	}
}
