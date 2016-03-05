import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

public class Checker implements Disposable {
	public  final Shape   node;
	private final boolean isPlayer1;
	private final Paint   kingFill;
	
	private boolean   isKing;
	private CellIndex pos;
	
	public Checker(boolean isPlayer1, Paint kingFill, Shape node, CellIndex initialPos) {
		this.isPlayer1 = isPlayer1;
		this.node      = node;
		this.pos       = initialPos;
		this.kingFill  = kingFill;
		this.isKing    = false;
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
	
	public boolean getIsPlayer1() {
		return isPlayer1;
	}
	
	public boolean getIsKing() {
		return isKing;
	}
	
	public void kingMe() {
		this.isKing = true;
		
		this.getNode().setFill(this.kingFill);
	}
	
	public String toString() {
		return "Checker: { Pos: {" + pos.toString() + "}, isPlayer1: " + isPlayer1 + " }"; 
	}
	
	public void dispose() {
		this.pos.dispose();
		this.pos = null;
	}
}
