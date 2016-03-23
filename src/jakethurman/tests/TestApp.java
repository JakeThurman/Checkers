package jakethurman.tests;

import jakethurman.foundation.tests.CleanupHandlerTests;
import jakethurman.foundation.tests.ObservableListTests;

public class TestApp {
	public static void main(String[] args) {
		TestRunner runner = new TestRunner(
			new CleanupHandlerTests(),
			new ObservableListTests()
		);
		
		runner.subscribeToResults(System.out::println);
		
		runner.runAll();
	}
}
