package jakethurman.tests;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

import jakethurman.foundation.ObservableList;

// Isn't this beautiful! :)
// Runs all of the tests in any number of given test units
public class TestRunner {
	private final TestUnit[] testUnits;
	private final ObservableList<String> messages;
	
	private TestUnit currentTestUnit;
	private int testFailures;
	
	// C'tor
	public TestRunner(TestUnit...testUnits) {
		this.testUnits = testUnits;
		messages = new ObservableList<>();
	}
	
	// Subscribes a listener to the results stream
	public void subscribeToResults(Consumer<String> handler) {
		messages.subscribe(handler);
	}
	
	// Runs a singular test case 
	private void runTestCase(int i, TestCase testCase) {
		// Get the test class
		String classPath = currentTestUnit.getClass().getName();
		// Get the title of the test case
		String testName = testCase.getTestTitle();
		
		// Format the message and send it to all of the output subscribers
		this.messages.dispatch("Running Test #" + i + ": " + classPath + " - " + testName);
				
		// Run all of the test unit before each handlers
		currentTestUnit.beforeEach();
		
		// Try to run the test
		try {
			testCase.run();
		}
		catch(Exception e) {
			testFailures++;
			// Create and dispatch the test failure message
			this.messages.dispatch(new TestExcpetionStringBuilder(e).toString());
		}
		finally {
			// Run the test units afterEach method whether or not the test passed.
			currentTestUnit.afterEach();
		}
	}
	
	public void runAll() {
		int i = 1;
		
		// Queue up the tests
		Queue<TestCase> testCasesQueue = new LinkedList<>();
		Queue<TestUnit> testUnitsQueue = new LinkedList<>();
		
		// Add all of the test units to the queue
		for (TestUnit unit : this.testUnits)
			testUnitsQueue.add(unit);
		
		while(!testCasesQueue.isEmpty() || !testUnitsQueue.isEmpty()) {
			//Run the test if we can
			if (testCasesQueue.isEmpty()) {
				//close out the last test unit if there is one
				if (currentTestUnit != null)
					currentTestUnit.tearDown();
				
				//Get the next test unit
				currentTestUnit = testUnitsQueue.poll();
				
				//Initialize the new test unit
				currentTestUnit.setup();
				
				//Get the new test cases			
				for (TestCase testCase : currentTestUnit.getTests())
					testCasesQueue.add(testCase);
			}
			
			// Run the next test case
			runTestCase(i, testCasesQueue.poll());
			i++;
		}
		
		// Dispatch the final results stream.
		this.messages.dispatch("Test Run Complete.\n\n" + (i - 1) + " tests run with " + this.testFailures + " failures.");
	}
}
