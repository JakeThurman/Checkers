package jakethurman.components;

import javafx.scene.text.Text;

/* 
 * Wraps a text node exposing almost only 
 * the functionality of updating the text.
 */
public class SafeText extends SafeNode {
	private final Text node; // The core value
	
	// C'tor
	public SafeText(Text node) {
		super(node);
		
		this.node = node;
	}
	
	// Updates the text in the node.
	public void setText(String text) {
		node.setText(text);
	}
}
