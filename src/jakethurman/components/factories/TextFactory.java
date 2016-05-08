package jakethurman.components.factories;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import jakethurman.components.SafeNode;
import jakethurman.components.SafeText;
import jakethurman.foundation.Disposable;

/* 
 * Handles rendering text nodes. 
 * TODO: I probably shouldn't have methods for every style I want.
 */
public class TextFactory implements Disposable {
	// Constants! (Why doesn't java just have a "constant"?)
	// TODO: Make these arguments somewhere
	private static final String FONT_FAMILY = "Tahoma"; //Always use this font..
	private static final int    FONT_SIZE   = 20; // Always use a 20pt font.
	
	// Creates a left aligned text element with an initial value
	public SafeNode createLeftAlign(String text) {
		SafeText t = createLeftAlign();
		t.setText(text);
		return t;
	}
	
	// Creates a left aligned text element
	public SafeText createLeftAlign() {
		return create_impl(Font.font(FONT_FAMILY, FONT_SIZE), TextAlignment.LEFT);
	}

	// Creates a centered, bold text element with an initial value
	public SafeNode createCenteredBold(String text) {
		SafeText t = createCenteredBold();
		t.setText(text);
		return t;
	}
	
	// Creates a centered, bold text element
	public SafeText createCenteredBold() {
		return create_impl(Font.font(FONT_FAMILY, FontWeight.BOLD, FONT_SIZE), TextAlignment.CENTER);
	}

	// The common/shared implementation of the create methods.
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
