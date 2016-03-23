package jakethurman.tests;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

import jakethurman.foundation.ObservableList;

public class TestRunner {
	private final TestUnit[] testUnits;
	private final ObservableList<String> messages;
	
	private TestUnit currentTestUnit;
	private int testFailures;
	
	public TestRunner(TestUnit...testUnits) {
		this.testUnits = testUnits;
		messages = new ObservableList<>();
	}
	
	public void subscribeToResults(Consumer<String> handler) {
		messages.subscribe(handler);
	}
	
	private void runTestCase(int i, TestCase testCase) {
		String classPath = currentTestUnit.getClass().getName();
		String testName = testCase.getTestTitle();
		
		this.messages.dispatch("Running Test #" + i + ": " + classPath + " - " + testName);
		
		String message = "Success";
		
		currentTestUnit.beforeEach();
		
		try {
			testCase.run();
		}
		catch(Exception e) {
			testFailures++;
			message = new TestExcpetionStringBuilder(e).toString();
		}
		finally {
			currentTestUnit.afterEach();
		}
		
		this.messages.dispatch(message);
	}
	
	public void runAll() {
		int i = 1;
		
		Queue<TestCase> testCasesQueue = new LinkedList<>();
		Queue<TestUnit> testUnitsQueue = new LinkedList<>();
		
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
			
			runTestCase(i, testCasesQueue.poll());
			i++;
		}
		
		this.messages.dispatch("Test Run Complete.\n\n" + (i - 1) + " tests run with " + this.testFailures + " failures.");
	}
}
