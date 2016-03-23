package jakethurman.games.checkers;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import jakethurman.components.CellIndex;

public class CellSearchData implements CellSearchResult {
	private final CellIndex source;
	private final int       deltaX, 
	                        deltaY;
	
	public final LinkedList<CellIndex> jumpedCells;
		
	public CellSearchData(int deltaX, int deltaY, CellIndex source) {
		this(deltaX, deltaY, source, new LinkedList<CellIndex>());
	}
	
	private CellSearchData(int deltaX, int deltaY, CellIndex source, LinkedList<CellIndex> jumpedCells){
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
	public Iterable<CellIndex> getJumpedCells() {		
		return jumpedCells;
	}
	
	@Override
	public CellIndex getCellIndex() {
		return new CellIndex(source.x + deltaX, source.y + deltaY);
	}
	
	public CellSearchData withJumpOffset() {
		this.jumpedCells.add(this.getCellIndex());
		return new CellSearchData(deltaX * 2, deltaY * 2, source, jumpedCells);
	}
	
	@SuppressWarnings("unchecked")
	public List<CellSearchData> getDoubleJumpOptions(Iterable<CellSearchData> deltas, Predicate<CellIndex> isValid) {
		LinkedList<CellSearchData> results = new LinkedList<>();
			
		for (CellSearchData d : deltas) {
			CellIndex toDoubleJump = new CellSearchData(deltaX + (d.deltaX), deltaY + (d.deltaY), source).getCellIndex();
				
			if(isValid.test(toDoubleJump)) {
				jumpedCells.add(toDoubleJump);
				results.add(new CellSearchData(deltaX + (d.deltaX * 2), deltaY + (d.deltaY * 2), source, (LinkedList<CellIndex>)jumpedCells.clone()));
				jumpedCells.remove(toDoubleJump);
			}
		}
		
		return results;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof CellSearchData && ((CellSearchData)obj).getCellIndex() == getCellIndex();
	}
	
	@Override
	public String toString() {
		return "{ source: " + source.toString() + ", deltaX: " + deltaX + ", deltaY: " + deltaY + " }";
	}
}