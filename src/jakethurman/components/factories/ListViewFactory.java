package jakethurman.components.factories;

import javafx.collections.FXCollections;
import javafx.scene.control.ListView;

import java.util.ArrayList;

import jakethurman.components.SafeNode;
import jakethurman.foundation.Disposable;

public class ListViewFactory implements Disposable {
	public SafeNode create(ArrayList<String> values) {
		ListView<String> lv = new ListView<>();
		
		lv.setItems(FXCollections.observableArrayList(values));
		
		return new SafeNode(lv);
	}

	@Override
	public void dispose() {
		//Nothing to dispose
	}
}
