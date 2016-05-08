package jakethurman.foundation;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/* Basic list object following the subscriber pattern. */
public class ObservableList<T> {
	private final List<T>           state;
	private final List<Consumer<T>> subscribers;
	
	public ObservableList() {
		this.state       = new LinkedList<>(); // Will store the current state of the object.
		this.subscribers = new LinkedList<>(); // Will store all of the listeners to the object.
	}
	
	// Returns the current state
	public Collection<T> getState() {
		return state;
	}
	
	// Adds a change listener to observe changes.
	public void subscribe(Consumer<T> subscriber) {
		subscribers.add(subscriber);
	}
	
	public void dispatch(T obj) {
		state.add(obj); // Append to state
		
		// Dispatch to all subscribers
		for(Consumer<T> subscriber : subscribers)
			subscriber.accept(obj);
	}
}
