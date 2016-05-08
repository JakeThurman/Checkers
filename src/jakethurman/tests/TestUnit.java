package jakethurman.tests;

// Base class of a class to test another class.
// Because this isn't an interface, child classes
// do not need to implement all of the methods.
public abstract class TestUnit {
	public abstract TestCase[] getTests();

	public void setup() {}
	public void tearDown() {}
	public void beforeEach() {}
	public void afterEach() {}
}
