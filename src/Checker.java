import javafx.scene.shape.Shape;

public class Checker {
	public  final Shape   node;
	private final boolean isPlayer1;
	
	private CellIndex pos = null;
	
	public Checker(boolean isPlayer1, Shape node, CellIndex initialPos) {
		this.isPlayer1 = isPlayer1;
		this.node      = node;
		this.pos       = initialPos;
	}
	
	public Shape getNode() {
		return node;
	}
	
	public CellIndex getPos() {
		return this.pos;
	}
	
	public void setPos(CellIndex pos) {
		this.pos = pos;
	}
	
	public boolean isPlayer1() {
		return isPlayer1;
	}
}
