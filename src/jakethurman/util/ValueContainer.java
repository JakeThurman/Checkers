package jakethurman.util;

//Helper class to hack around java's limitation
//of inner classes/lambdas only being able to 
//get instance variables of the parent class.
public class ValueContainer<T> {
	private T value;
	private boolean isSet = false;
	
	public ValueContainer(T value) {
		this.set(value);
	}
	
	public ValueContainer() {
	}
	
	public void set(T value) {
		this.value = value;
		this.isSet = true;
	}
	
	public T get() {
		return value;
	}
	
	public boolean isSet() {
		return this.isSet;
	}
	
	public void clear() {
		this.value = null;
		this.isSet = false;
	}
}