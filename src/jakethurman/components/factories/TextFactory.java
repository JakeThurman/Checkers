package jakethurman.components.factories;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import jakethurman.components.SafeNode;
import jakethurman.components.SafeText;
import jakethurman.foundation.Disposable;

public class TextFactory implements Disposable {
	private static final String FONT_FAMILY = "Tahoma";
	private static final int    FONT_SIZE   = 20;
	
	public SafeNode createLeftAlign(String text) {
		SafeText t = createLeftAlign();
		t.setText(text);
		return t;
	}
	
	public SafeText createLeftAlign() {
		return create_impl(Font.font(FONT_FAMILY, FONT_SIZE), TextAlignment.LEFT);
	}

	public SafeNode createCenteredBold(String text) {
		SafeText t = createCenteredBold();
		t.setText(text);
		return t;
	}
	
	public SafeText createCenteredBold() {
		return create_impl(Font.font(FONT_FAMILY, FontWeight.BOLD, FONT_SIZE), TextAlignment.CENTER);
	}

	private static SafeText create_impl(Font f, TextAlignment ta) {
		Text t = new Text();
		t.setTextAlignment(ta);
		t.setFont(f);
		return new SafeText(t);
	}
	
	@Override
	public void dispose() {
		// Nothing to dispose
	}
}
