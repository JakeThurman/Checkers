package jakethurman.tests;

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