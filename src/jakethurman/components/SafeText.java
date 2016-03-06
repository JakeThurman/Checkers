package jakethurman.components;

import javafx.scene.text.Text;

public class SafeText extends SafeNode {
	private final Text node;
	
	public SafeText(Text node) {
		super(node);
		
		this.node = node;
	}
	
	public void setText(String text) {
		node.setText(text);
	}
}
