package jakethurman.components;

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
	
	public void add(SafeNode n, CellIndex i) {
		pane.add(n.getUnsafe(), i.x, i.y);
	}
	
	public void remove(SafeNode n) {
		pane.getChildren().remove(n.getUnsafe());
	}

	//TODO: This is unsafe
	public void addRowConstraint(RowConstraints rowConstraints) {
        pane.getRowConstraints().add(rowConstraints);
	}

	//TODO: This is unsafe
	public void addColumnConstraint(ColumnConstraints colConstraints) {
        pane.getColumnConstraints().add(colConstraints);
	}
}
