package jakethurman.tests;

public abstract class TestUnit {
	public abstract TestCase[] getTests();

	public void setup() {}
	public void tearDown() {}
	public void beforeEach() {}
	public void afterEach() {}
}
