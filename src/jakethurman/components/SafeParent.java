package jakethurman.components;

import javafx.scene.Parent;

// This helps us keep us from exposing to much of the API
// while allowing for SafeScene to take a SafeGridPane or SafeBorderPane
public abstract class SafeParent extends SafeNode {
	Parent parentNode;
	
	public SafeParent(Parent node) {
		super(node);
		
		this.parentNode = node;
	}
	
	// Gets a PARENT object. Not just a node!
	protected Parent getUnsafe_Parent() {
		return parentNode;
	}
}
