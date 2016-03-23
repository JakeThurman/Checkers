package jakethurman.tests;

public class TestCase {
	private final RunnableTestCase runner;
	private final String           title;
	
	public TestCase(String title, RunnableTestCase runner) {
		this.runner = runner;
		this.title  = title;
	}
	
	public void run() throws Exception {
		runner.run();
	}
	
	public String getTestTitle() {
		return this.title;
	}
}
