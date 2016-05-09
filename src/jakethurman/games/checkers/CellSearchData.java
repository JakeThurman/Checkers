package jakethurman.games.checkers;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import jakethurman.foundation.Point;

/*
 * Represents a cell that a player can potentially move to.
 * Also handles finding double jumps in the case that they are valid.
 */
public class CellSearchData implements CellSearchResult {
	// Data about the move
	private final Point source;
	private final int   deltaX, 
	                    deltaY;
	
	// A list of the cells we want will jump for this move.
	public final LinkedList<Point> jumpedCells;
		
	// Public c'tor
	public CellSearchData(int deltaX, int deltaY, Point source) {
		this(deltaX, deltaY, source, new LinkedList<Point>());
	}
	
	// Private c'tor that propagates jumpedCells
	private CellSearchData(int deltaX, int deltaY, Point source, LinkedList<Point> jumpedCells){
		this.deltaX = deltaX;
		this.deltaY = deltaY;
		this.source = source;
		this.jumpedCells = jumpedCells;
	}
	
	// Are we jumping over any pieces with this move?
	@Override
	public boolean getIsJump() {		
		return !jumpedCells.isEmpty();
	}
	
	// Get all of the cells we are jumping with this move	
	@Override
	public List<Point> getJumpedCells() {		
		return jumpedCells;
	}
	
	// Get the point at which this move will be made to
	@Override
	public Point getPoint() {
		return new Point(source.x + deltaX, source.y + deltaY);
	}
	
	// Creates a version of this this object modified as a jump. (With double deltas).
	public CellSearchData withJumpOffset() {
		this.jumpedCells.add(this.getPoint());
		return new CellSearchData(deltaX * 2, deltaY * 2, source, jumpedCells);
	}
	
	// Gets all of the valid double jump options for this search result	
	@SuppressWarnings("unchecked")
	public List<CellSearchData> getDoubleJumpOptions(Iterable<CellSearchData> deltas, Predicate<Point> isValid) {
		// Create a list to store the results in
		LinkedList<CellSearchData> results = new LinkedList<>();
			
		// Loop through each of the given deltas
		for (CellSearchData d : deltas) {
			// Create a new instance of this class to get the point
			// at which we would double jump to for this potential move
			Point toDoubleJump = new CellSearchData(deltaX + (d.deltaX), deltaY + (d.deltaY), source).getPoint();
				
			// Test if this is a valid move using the given Predicate function.
			if(isValid.test(toDoubleJump)) {
				// If it is valid, add it to the cells that we can double jump temporarily.
				jumpedCells.add(toDoubleJump);
				// Create a new cell search result and add it the results list.
				results.add(new CellSearchData(deltaX + (d.deltaX * 2), deltaY + (d.deltaY * 2), source, (LinkedList<Point>)jumpedCells.clone()));
				// This object cannot jump this cell so remove it again.
				jumpedCells.remove(toDoubleJump);
			}
		}
		
		// Return all of the options we found for the user to double jump too. 		
		return results;
	}
	
	// Is this equal?
	@Override
	public boolean equals(Object obj) {
		// They are equal if it is also a CellSearchData instance and the points are equal.
		return obj instanceof CellSearchData && ((CellSearchData)obj).getPoint().equals(getPoint());
	}
	
	@Override
	public String toString() {
		return "{ source: " + source.toString() + ", deltaX: " + deltaX + ", deltaY: " + deltaY + " }";
	}

	// Compares two search results.
	// Used by the AI player move calculation
	// when storing in the AVLTree.
	@Override
	public int compareTo(CellSearchResult other) {
		// Get the points of each (this and other)
		Point otherPoint = other.getPoint();
		Point myPoint = getPoint();
		
		// Get the count of the number of cells each move is jumping
		int otherJumpedCells = other.getJumpedCells().size();
		int myJumedCells = jumpedCells.size();
		
		// If everything is the same, return 0; (equal)
		if (otherPoint.equals(myPoint) && otherJumpedCells == myJumedCells) 
			return 0;
		
		// Prioritize difference in jumped cells
		if (myJumedCells > otherJumpedCells)
			return -1;
		if (myJumedCells < otherJumpedCells)
			return 1;

		// Otherwise, do the comparison based off of the x value; Ignore y.
		return otherPoint.x > myPoint.x ? 1 : -1;
	}
}