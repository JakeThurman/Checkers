package jakethurman.games.checkers.components;

import javafx.scene.shape.Shape;
import jakethurman.components.CellIndex;
import jakethurman.foundation.Disposable;

public class Checker implements Disposable {
	public  final Shape   node;
	private final boolean isPlayer1;

	private Runnable  onKing = null;
	private boolean   isKing;
	private CellIndex pos;
	
	public Checker(boolean isPlayer1, Shape node, CellIndex initialPos) {
		this.isPlayer1 = isPlayer1;
		this.node      = node;
		this.pos       = initialPos;
		this.isKing    = false;
	}
	
	public void setOnKing(final Runnable onKing) {
		this.onKing = onKing;
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
		
		if (onKing != null) 
			onKing.run();
	}
	
	public String toString() {
		return "Checker: { Pos: {" + pos.toString() + "}, isPlayer1: " + isPlayer1 + " }"; 
	}
	
	public void dispose() {
		this.pos.dispose();
		this.pos    = null;
		this.onKing = null;
	}
}
