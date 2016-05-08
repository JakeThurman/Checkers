package jakethurman.tests;

//Helper class to hack around java's limitation
//of inner classes/lambdas only being able to 
//get instance variables of the parent class.
public class ValueContainer<T> {
	private T value;
	
	public ValueContainer(T value) {
		this.value = value;
	}
	
	public ValueContainer() {
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	
	public T getValue() {
		return value;
	}
}