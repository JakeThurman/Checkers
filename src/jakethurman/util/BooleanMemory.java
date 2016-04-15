package jakethurman.util;


// Helper class to hack around java's limitation
// of inner classes/lambdas only being able to 
// get instance variables of the parent class.
public class BooleanMemory {
	private boolean set = false;
	private boolean value = false;
	
	public BooleanMemory(boolean val) {
		set(val);
	}
	
	public BooleanMemory() {
	}

	public void set(boolean val) {
		this.value = val;
		this.set   = true;
	}
	
	public boolean isSet() {
		return set;
	}
	
	public boolean get() {
		return value;
	}
}