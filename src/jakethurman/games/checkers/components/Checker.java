package jakethurman.games.checkers.components;

import jakethurman.components.CellIndex;
import jakethurman.components.SafePaint;
import jakethurman.components.SafeShape;
import jakethurman.foundation.Disposable;

public class Checker implements Disposable {
	private final SafeShape node;
	private final boolean   isPlayer1;
	private final SafePaint kingFill;

	private boolean   isKing;
	private CellIndex pos;
	
	public Checker(boolean isPlayer1, SafePaint kingFill, SafeShape node, CellIndex initialPos) {
		this.isPlayer1 = isPlayer1;
		this.kingFill  = kingFill;
		this.node      = node;
		this.pos       = initialPos;
		this.isKing    = false;
	}
	
	public SafeShape getNode() {
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
		this.node.setFill(kingFill);
	}
	
	public String toString() {
		return "Checker: { Pos: {" + pos.toString() + "}, isPlayer1: " + isPlayer1 + " }"; 
	}
	
	public void dispose() {
		this.pos.dispose();
		this.pos    = null;
	}
}
