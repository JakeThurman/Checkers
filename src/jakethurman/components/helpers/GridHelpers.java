package jakethurman.components.helpers;

import jakethurman.components.SafeGridPane;
import jakethurman.components.SafePaint;
import jakethurman.components.factories.ShapeFactory;
import jakethurman.foundation.CleanupHandler;
import jakethurman.foundation.Disposable;
import jakethurman.foundation.Point;

/* Simple helper class only used to fill a grid with squares. */
public class GridHelpers implements Disposable {
	/* Dependencies */
	private final ShapeFactory   shapeFactory;
	private final CleanupHandler cleanup;
	
	//C'tor
	public GridHelpers(final ShapeFactory shapeFactory) {
		this.shapeFactory = shapeFactory;
		this.cleanup = new CleanupHandler(shapeFactory);
	}
	
	// Fills a grid with squares.
	public void fillGridWithSquares(final SafeGridPane parentNode) {
		final SafePaint[] squareColors = new SafePaint[] {SafePaint.WHITE, SafePaint.BLACK};
		
		int rows = parentNode.getRowCount();
		int columns = parentNode.getColumnCount();
		
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < columns; col++) {
				// Add a rectangle at every cell of alternating White-Black coloring.
				parentNode.add(shapeFactory.createRect(squareColors[(row+col)%2]), new Point(col, row));
			}
		}
	}
	
	@Override
	public void dispose() {
		cleanup.dispose();
	}
}
