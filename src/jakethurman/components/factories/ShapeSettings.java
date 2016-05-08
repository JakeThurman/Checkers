package jakethurman.components.factories;

import jakethurman.foundation.Disposable;

/*
 * Interface for all of the settings needed by ShapeFactory.
 * 
 * TODO: Move all of these into method arguments there.
 */
public interface ShapeSettings extends Disposable {	
	public int getCircleRadius();
	public int getCircleBorder();
	public int getSquareSize();
}
