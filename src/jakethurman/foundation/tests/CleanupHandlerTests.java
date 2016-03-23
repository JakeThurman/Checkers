package jakethurman.foundation.tests;

import jakethurman.foundation.CleanupHandler;
import jakethurman.foundation.Disposable;
import jakethurman.tests.TestCase;
import jakethurman.tests.TestFailureException;
import jakethurman.tests.TestUnit;

public class CleanupHandlerTests extends TestUnit {
	
	private CleanupHandler ch;
	private TestDisposable d;
	
	@Override
	public void beforeEach() {
		d  = new TestDisposable();
		ch = new CleanupHandler(d);
	}
	
	@Override
	public TestCase[] getTests() {
		return new TestCase[] {
			new TestCase("Disposed is not called on the object before the cleanup handler finishes.", () -> { 
				if (d.disposed)
					throw new TestFailureException("The object was disposed before the cleanup handler tried to dispose it!");
			}),
			new TestCase("The dispose method is only called once per item", () -> {
				ch.dispose();
				
				if (!d.disposed)
					throw new TestFailureException("The object was not disposed after the cleanup handler tried to dispose it!");
			})
		};
	}
		
	private class TestDisposable implements Disposable {
		public boolean disposed = false;
		
		public TestDisposable() {
		}

		@Override
		public void dispose() {
			disposed = true;
		}
	}
}
