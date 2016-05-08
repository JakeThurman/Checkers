package jakethurman.tests;

// Class for a single test case
public class TestCase {
	private final RunnableTestCase runner;
	private final String           title;
	
	// C'tor
	public TestCase(String title, RunnableTestCase runner) {
		this.runner = runner;
		this.title  = title;
	}
	
	// Runs the test case
	public void run() throws Exception {
		runner.run();
	}
	
	// Gets the test's title
	public String getTestTitle() {
		return this.title;
	}
}
