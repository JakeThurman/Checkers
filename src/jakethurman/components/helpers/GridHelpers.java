package jakethurman.components.helpers;

import jakethurman.components.Point;
import jakethurman.components.SafeGridPane;
import jakethurman.components.SafePaint;
import jakethurman.components.factories.ShapeFactory;
import jakethurman.foundation.CleanupHandler;
import jakethurman.foundation.Disposable;

public class GridHelpers implements Disposable {
	private final ShapeFactory   shapeFactory;
	private final CleanupHandler cleanup;
	
	public GridHelpers(final ShapeFactory shapeFactory) {
		this.shapeFactory = shapeFactory;
		this.cleanup = new CleanupHandler(shapeFactory);
	}
	
	public void fillGridWithSquares(final SafeGridPane parentNode) {
		final SafePaint[] squareColors = new SafePaint[] {SafePaint.WHITE, SafePaint.BLACK};
		
		int rows = parentNode.getRowCount();
		int columns = parentNode.getColumnCount();
		
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < columns; col++) {
				parentNode.add(shapeFactory.createRect(squareColors[(row+col)%2]), new Point(col, row));
			}
		}
	}
	
	@Override
	public void dispose() {
		cleanup.dispose();
	}
}
