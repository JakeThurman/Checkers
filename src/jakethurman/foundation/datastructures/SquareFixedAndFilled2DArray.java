package jakethurman.foundation.datastructures;

import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Supplier;
import jakethurman.foundation.Point;

public class SquareFixedAndFilled2DArray<V> implements Iterable<V> {
	private final int size;
	
	private final FixedAndFilledArray<FixedAndFilledArray<V>> grid;

	public SquareFixedAndFilled2DArray(final int size, final Supplier<V> init) {
		this(size, (p) -> init.get());
	}
	
	public SquareFixedAndFilled2DArray(final int size, final Function<Point, V> init) {
		this.size = size;
		
		// Initialize each cell in the 2D array right away
		this.grid = new FixedAndFilledArray<>(size, 
			(x) -> new FixedAndFilledArray<>(size,
				(y) -> init.apply(new Point(x, y))));
	}

	// Gets an object at the given coordinate pair
	public V get(Point p) {
		// If this is an invalid point on our grid, return null
		if (!isValid(p))
			return null;
		
		// Get the appropriate object
		return grid.get(p.x).get(p.y);
	}
	
	// Validates that a given coordinate pair
	// are valid for points on this object
	public boolean isValid(Point p) {
		return p != null
		    && p.x < size && p.x >= 0 
		    && p.y < size && p.y >= 0;
	}
	
	@Override
	public Iterator<V> iterator() {		
		Iterator<? extends Iterable<V>> iterators = grid.iterator();
		
		return new Iterator<V>() {
			private Iterator<V> curr = null;
						
			@Override
			public boolean hasNext() {
				// First, handle the case when we don't have a current 
				// iterator (the first call to hasNext) & that is has 
				// a next value.
				if (curr == null || !curr.hasNext()) {
					// If there is no next iterator, we can be sure 
					// that there is nothing next.
					if (!iterators.hasNext())
						return false;

					// Set the current iterator to the next iterator
					curr = iterators.next().iterator();
				}
				
				// Does the current iterator have a next item?
				// In theory this will only return true, but
				// Who can be sure! Better safe than sorry!
				return curr.hasNext();
			}

			@Override
			public V next() {
				// If there is no next item to give, return null
				if (!hasNext())
					return null;
				
				// Return the next item from the current iterator
				// which hasNext will keep updated for us
				return curr.next();
			}
		};
	}
	
	@Override
	public String toString() {
		return grid.toString();
	}
}
