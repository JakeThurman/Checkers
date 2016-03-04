class CellSearchData implements CellSearchResult {
	private final CellIndex source;
	private final int       deltaX, 
	                        deltaY,
	                        originalDeltaX,
	                        originalDeltaY;
	public  final boolean   isJump;
		
	public CellSearchData(int deltaX, int deltaY, CellIndex source) {
		this(deltaX, deltaX, deltaY, deltaY, source, false);
	}
	
	private CellSearchData(int deltaX, int originalDeltaX, int deltaY, int originalDeltaY, CellIndex source, boolean isJump){
		this.deltaX = deltaX;
		this.deltaY = deltaY;
		this.source = source;
		this.isJump = isJump;
		this.originalDeltaX = originalDeltaX;
		this.originalDeltaY = originalDeltaY;
	}
	
	public CellIndex getJumpedCellIndex() {		
		return new CellIndex(source.x + deltaX - originalDeltaX, source.y + deltaY - originalDeltaY);
	}
	
	public boolean getIsJump() {
		return isJump;
	}
	
	public CellIndex getCellIndex() {
		return new CellIndex(source.x + deltaX, source.y + deltaY);
	}
	
	public CellSearchData withJumpOffset() {
		return new CellSearchData(deltaX + originalDeltaX, originalDeltaX, deltaY + originalDeltaY, originalDeltaY, source, true);
	}
	
	public boolean equals(Object obj) {
		return obj instanceof CellSearchData && ((CellSearchData)obj).getCellIndex() == getCellIndex();
	}
}