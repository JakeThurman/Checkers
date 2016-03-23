package jakethurman.foundation;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class ObservableList<T> {
	private final List<T>           state;
	private final List<Consumer<T>> subscribers;
		
	public ObservableList() {
		this.state       = new LinkedList<>();
		this.subscribers = new LinkedList<>();
	}
	
	public Collection<T> getState() {
		return state;
	}
	
	public void subscribe(Consumer<T> subscriber) {
		subscribers.add(subscriber);
	}
	
	public void dispatch(T obj) {
		state.add(obj);
		
		for(Consumer<T> subscriber : subscribers)
			subscriber.accept(obj);
	}
}
