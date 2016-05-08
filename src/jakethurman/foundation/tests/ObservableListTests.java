package jakethurman.foundation.tests;

import java.util.Arrays;

import jakethurman.foundation.ObservableList;
import jakethurman.tests.TestCase;
import jakethurman.tests.TestFailureException;
import jakethurman.tests.TestUnit;
import jakethurman.tests.ValueContainer;

/* Tests for the ObservableList class (see jakethuman.tests.TestApp to run) */
public class ObservableListTests extends TestUnit {

	private ObservableList<String> ol;
	
	@Override
	public void beforeEach() {
		this.ol = new ObservableList<>();
	}
	
	@Override
	public TestCase[] getTests() {
		return new TestCase[] {
			new TestCase("subscribe recives only the newest item.", () -> {
				ValueContainer<Boolean> subscriberRecievedNewestValue = new ValueContainer<>(Boolean.FALSE);
				String val = "This is a test string";
				
				ol.subscribe(s -> subscriberRecievedNewestValue.setValue(new Boolean(s.equals(val))));
				ol.dispatch(val);
				
				if (!subscriberRecievedNewestValue.getValue().booleanValue())
					throw new TestFailureException("Subscriber did not recieve newest item when dispatched");
			}),
			new TestCase("dispatch should call all subscribers.", () -> {
				ValueContainer<Boolean> subscriber1RecievedNewestValue = new ValueContainer<>(Boolean.FALSE);
				ValueContainer<Boolean> subscriber2RecievedNewestValue = new ValueContainer<>(Boolean.FALSE);
								
				ol.subscribe(s -> subscriber1RecievedNewestValue.setValue(Boolean.TRUE));
				ol.subscribe(s -> subscriber2RecievedNewestValue.setValue(Boolean.TRUE));
				ol.dispatch("Test string");
				
				if (!subscriber1RecievedNewestValue.getValue().booleanValue() || !subscriber2RecievedNewestValue.getValue().booleanValue())
					throw new TestFailureException("All subscribers should be called for every dispatch!");
			}),
			new TestCase("subscribers should be called for each dispatch.", () -> {
				ValueContainer<Integer> dispatches = new ValueContainer<>(new Integer(0));
								
				ol.subscribe(s -> dispatches.setValue(new Integer(dispatches.getValue().intValue() + 1)));
				ol.dispatch("Test string1");
				ol.dispatch("Test string2");
				ol.dispatch("Test string3");
				
				if (dispatches.getValue().intValue() != 3)
					throw new TestFailureException("The subscriber should have been called 3 times, but was called " + dispatches + " times.");
			}),
			new TestCase("getState should return all items currently in the list.", () -> {				
				
				String[] values = { "test", "1", "another" };
				
				for (String value : values)
					ol.dispatch(value);
				
				String[] output = {};
				output = ol.getState().toArray(output);
								
				if (!Arrays.deepEquals(output, values))
					throw new TestFailureException("getState did not respond with what it was inputed.");
			}),
			new TestCase("ObservableList Type Argument <T> should support any type.", () -> {	
				ValueContainer<Integer> i = new ValueContainer<>(new Integer(0));
				
				ObservableList<Runnable> olr = new ObservableList<>();
				
				olr.subscribe(r -> r.run());
				
				olr.dispatch(() -> { if (i.getValue().intValue() == 0) i.setValue(new Integer(i.getValue().intValue() + 1)); });
				olr.dispatch(() -> { if (i.getValue().intValue() == 1) i.setValue(new Integer(i.getValue().intValue() + 1)); });
				olr.dispatch(() -> { if (i.getValue().intValue() == 2) i.setValue(new Integer(i.getValue().intValue() + 1)); });
								
				if (i.getValue().intValue() != 3)
					throw new TestFailureException("3 dispatches were made to a subscriber, but " + i + " end up being called.");
			})
		};
	}
}
