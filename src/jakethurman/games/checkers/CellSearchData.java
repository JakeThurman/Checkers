package jakethurman.games.checkers;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import jakethurman.foundation.Point;

public class CellSearchData implements CellSearchResult {
	private final Point source;
	private final int   deltaX, 
	                    deltaY;
	
	public final LinkedList<Point> jumpedCells;
		
	public CellSearchData(int deltaX, int deltaY, Point source) {
		this(deltaX, deltaY, source, new LinkedList<Point>());
	}
	
	private CellSearchData(int deltaX, int deltaY, Point source, LinkedList<Point> jumpedCells){
		this.deltaX = deltaX;
		this.deltaY = deltaY;
		this.source = source;
		this.jumpedCells = jumpedCells;
	}
	
	@Override
	public boolean getIsJump() {		
		return jumpedCells.toArray().length > 0;
	}
	
	@Override
	public Iterable<Point> getJumpedCells() {		
		return jumpedCells;
	}
	
	@Override
	public Point getPoint() {
		return new Point(source.x + deltaX, source.y + deltaY);
	}
	
	public CellSearchData withJumpOffset() {
		this.jumpedCells.add(this.getPoint());
		return new CellSearchData(deltaX * 2, deltaY * 2, source, jumpedCells);
	}
	
	@SuppressWarnings("unchecked")
	public List<CellSearchData> getDoubleJumpOptions(Iterable<CellSearchData> deltas, Predicate<Point> isValid) {
		LinkedList<CellSearchData> results = new LinkedList<>();
			
		for (CellSearchData d : deltas) {
			Point toDoubleJump = new CellSearchData(deltaX + (d.deltaX), deltaY + (d.deltaY), source).getPoint();
				
			if(isValid.test(toDoubleJump)) {
				jumpedCells.add(toDoubleJump);
				results.add(new CellSearchData(deltaX + (d.deltaX * 2), deltaY + (d.deltaY * 2), source, (LinkedList<Point>)jumpedCells.clone()));
				jumpedCells.remove(toDoubleJump);
			}
		}
		
		return results;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof CellSearchData && ((CellSearchData)obj).getPoint().equals(getPoint());
	}
	
	@Override
	public String toString() {
		return "{ source: " + source.toString() + ", deltaX: " + deltaX + ", deltaY: " + deltaY + " }";
	}
}