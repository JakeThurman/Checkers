package jakethurman.tests;

import jakethurman.foundation.tests.CleanupHandlerTests;
import jakethurman.foundation.tests.ObservableListTests;

//Runs all known test classes
public class TestApp {
	public static void main(String[] args) {
		// Create a test runner object to run the tests.
		TestRunner runner = new TestRunner(
			new CleanupHandlerTests(),
			new ObservableListTests()
		);
		
		// We want to print out all of the results
		runner.subscribeToResults(System.out::println);
		
		// Tell the test runner to run all of the found tests
		runner.runAll();
	}
}
