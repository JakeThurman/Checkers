package jakethurman.games.checkers.components;

import jakethurman.components.Point;
import jakethurman.components.SafePaint;
import jakethurman.components.SafeShape;
import jakethurman.foundation.Disposable;

public class Checker implements Disposable {
	private final SafeShape node;
	private final boolean   isPlayer1;
	private final SafePaint kingFill;

	private boolean isKing;
	private Point   pos;
	
	public Checker(boolean isPlayer1, SafePaint kingFill, SafeShape node, Point initialPos) {
		this.isPlayer1 = isPlayer1;
		this.kingFill  = kingFill;
		this.node      = node;
		this.pos       = initialPos;
		this.isKing    = false;
	}
	
	public SafeShape getNode() {
		return this.node;
	}
	
	public Point getPos() {
		return this.pos;
	}
	
	public void setPos(Point pos) {
		this.pos = pos;
	}
	
	public boolean getIsPlayer1() {
		return this.isPlayer1;
	}
	
	public boolean getIsKing() {
		return this.isKing;
	}
	
	public void kingMe() {
		this.isKing = true;
		this.node.setFill(this.kingFill);
	}
	
	@Override
	public String toString() {
		return "Checker: { Pos: {" + this.pos.toString() + "}, isPlayer1: " + this.isPlayer1 + " }"; 
	}
	
	@Override
	public void dispose() {
		this.pos.dispose();
		this.pos    = null;
	}
}
