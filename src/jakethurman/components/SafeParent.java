package jakethurman.components;

import javafx.scene.Parent;

public abstract class SafeParent extends SafeNode {
	Parent parentNode;
	
	public SafeParent(Parent node) {
		super(node);
		
		this.parentNode = node;
	}
	
	protected Parent getUnsafe_Parent() {
		return parentNode;
	}
}
