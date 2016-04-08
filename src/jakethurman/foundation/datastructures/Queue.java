package jakethurman.foundation.datastructures;

public final class Queue<V> {
	Node<V> head = null;
	Node<V> tail = null;
	
	// Allows callers to the items of another Queue.
	public void enqueue(Queue<V> items) {		
		// If there is no head in this queue, set  
		// it to the head of the imported queue
		if (head == null)
			head = items.head;
		
		// If there is a tail, join the imported queue 
		// on there. Attaching its head to our tail.
		if (tail != null)
			tail.next = items.head;
		
		// Now we set the imported queue's tail (if it has one) as our new tail.
		if (items.tail != null)
			tail = items.tail;
	}
	
	// Allows callers to enqueue items from an Iterable.
	public void enqueue(Iterable<? extends V> items) {
		for(V item : items)
			enqueue(item);
	}
	
	@SafeVarargs
	// Adds some range of items to the ueue
	public final void enqueue(V... items) {
		for (V item : items) {
			// Create a node wrapper for this item
			Node<V> node = new Node<>(item);
			
			// If there is no head, this is the new head
			if (head == null)
				head = node;
			
			// Set this as the tail if there is no current tail
			if (tail == null) {
				tail = node;
			}
			else {
				// Set this node as the current tail's next item
				tail.next = node;
				// Set the new item as the new tail
				tail = node;
			}
		}
	}
	
	// returns the item at the top of the queue
	public V poll() {
		// If there is no leading element, return null
		if (head == null)
			return null;
		
		// Store the current head's element to return in a moment
		V item = head.element;
		
		// Set the head to the next node in the queue
		head = head.next;
		
		// Return the polled item
		return item;
	}
	
	// Does the queue have a next element?
	public boolean hasNext() {
		return head != null;
	}
	
	private static class Node<V> {
		protected V element;
		protected Node<V> next;

		protected Node(V element) {
			this.element = element;
		}
	}
}
