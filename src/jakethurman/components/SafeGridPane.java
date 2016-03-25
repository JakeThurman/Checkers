package jakethurman.components;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class SafeGridPane extends SafeNode {
	private final GridPane pane;
	
	public SafeGridPane() {
		this(new GridPane());
	}
	
	public SafeGridPane(GridPane pane) {
		super(pane);
		this.pane = pane;
	}
	
	public void add(SafeNode n, Point i) {
		pane.add(n.getUnsafe(), i.x, i.y);
	}
	
	public void remove(SafeNode n) {
		pane.getChildren().remove(n.getUnsafe());
	}

	public void addRow(final double size) {
        final RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setMinHeight(size);
        rowConstraints.setPrefHeight(size);
        rowConstraints.setMaxHeight(size);
        rowConstraints.setValignment(VPos.CENTER);
        pane.getRowConstraints().add(rowConstraints);
	}

	public void addColumn(final double size) {
        final ColumnConstraints colConstraints = new ColumnConstraints();
        colConstraints.setMinWidth(size);
        colConstraints.setMaxWidth(size);
        colConstraints.setPrefWidth(size);
        colConstraints.setHalignment(HPos.CENTER);
        pane.getColumnConstraints().add(colConstraints);
	}
}
