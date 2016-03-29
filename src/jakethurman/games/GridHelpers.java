package jakethurman.games;

import jakethurman.components.Point;
import jakethurman.components.SafeGridPane;
import jakethurman.components.SafePaint;
import jakethurman.components.factories.ShapeFactory;

public class GridHelpers {
	public static void fillGridWithSquares(final ShapeFactory shapeFactory, final SafeGridPane parentNode) {
		final SafePaint[] squareColors = new SafePaint[] {SafePaint.WHITE, SafePaint.BLACK};
		
		int rows = parentNode.getRowCount();
		int columns = parentNode.getColumnCount();
		
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < columns; col++) {
				parentNode.add(shapeFactory.createRect(squareColors[(row+col)%2]), new Point(col, row));
			}
		}
	}
}
