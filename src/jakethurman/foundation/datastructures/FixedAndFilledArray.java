package jakethurman.foundation.datastructures;

import java.util.function.Supplier;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.IntFunction;

public class FixedAndFilledArray<V> implements Iterable<V> {
	private final Object[] data;
	
	public FixedAndFilledArray(int size, Supplier<V> init) {
		this(size, (i) -> init.get());
	}

	public FixedAndFilledArray(int size, IntFunction<V> init) {
		data = new Object[size];
		
		// Initialize each cell in the array right away
		for (int i = 0; i < size; i++)
			data[i] = init.apply(i);
	}
	
	@SuppressWarnings("unchecked")
	public V get(int i) {
		return (V)data[i];
	}
	
	// Validates a point is valid for the array object
	public boolean isValid(int i) {
		return i < data.length && i >= 0;
	}
	
	@Override
	public String toString() {
		return Arrays.toString(data);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<V> iterator() {
		return Arrays.asList((V[])data).iterator();
	}
}
