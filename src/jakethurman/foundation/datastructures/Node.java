package jakethurman.foundation.datastructures;

public class Node<V> {
	protected V element;
	protected Node<V> next;

	protected Node(V element) {
		this.element = element;
	}
}