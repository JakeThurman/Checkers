package jakethurman.games.checkers.components;

import jakethurman.components.SafePaint;
import jakethurman.components.SafeShape;
import jakethurman.foundation.Disposable;
import jakethurman.foundation.Point;

/*
 * Represents a singular checker on the checker board.
 */
public class Checker implements Disposable {
	// Data
	private final SafeShape node;
	private final boolean   isPlayer1;
	private final SafePaint kingFill;
	
	private boolean isKing;
	private Point   pos;
	
	// C'tor
	public Checker(boolean isPlayer1, SafePaint kingFill, SafeShape node, Point initialPos) {
		this.isPlayer1 = isPlayer1;
		this.kingFill  = kingFill;
		this.node      = node;
		this.pos       = initialPos;
		this.isKing    = false;
	}
	
	// Gets the node of the checker
	public SafeShape getNode() {
		return this.node;
	}
	
	// Gets the current location of the checker
	public Point getPos() {
		return this.pos;
	}
	
	// Updates the current location of the checker
	public void setPos(Point pos) {
		this.pos = pos;
	}
	
	// Is this player 1's checker?
	public boolean getIsPlayer1() {
		return this.isPlayer1;
	}
	
	// Is this checker a king?
	public boolean getIsKing() {
		return this.isKing;
	}
	
	// Make this checker a king
	public void kingMe() {
		this.isKing = true;
		this.node.setFill(this.kingFill); // Change the color!
	}
	
	@Override
	public String toString() {
		return "{ \"pos\": " + this.pos.toString() + ", \"isPlayer1\": " + this.isPlayer1 + " }"; 
	}
	
	@Override
	public void dispose() {
		//Nothing to dispose
	}
}
