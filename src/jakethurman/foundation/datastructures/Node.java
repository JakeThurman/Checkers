package jakethurman.foundation.datastructures;

// Super simple node class with mono-directional chaining
public class Node<V> {
	protected V element;
	protected Node<V> next;

	protected Node(V element) {
		this.element = element;
	}
}