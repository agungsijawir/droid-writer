package hu.scythe.droidwriter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

public class DroidWriterTestActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		ToggleButton boldToggle = (ToggleButton) findViewById(R.id.BoldButton);
		ToggleButton italicsToggle = (ToggleButton) findViewById(R.id.ItalicsButton);
		ToggleButton underlinedToggle = (ToggleButton) findViewById(R.id.UnderlineButton);

		View coolButton = findViewById(R.id.CoolButton);
		View cryButton = findViewById(R.id.CryButton);

		Button clearButton = (Button) findViewById(R.id.ClearButton);

		DroidWriterEditText dwEdit = (DroidWriterEditText) findViewById(R.id.DwEdit);
		dwEdit.setImageGetter(new Html.ImageGetter() {
			@Override
			public Drawable getDrawable(String source) {
				Drawable drawable = null;

				try {
					if (source.equals("smiley_cool.gif")) {
						drawable = getResources().getDrawable(R.drawable.smiley_cool);
					} else if (source.equals("smiley_cry.gif")) {
						drawable = getResources().getDrawable(R.drawable.smiley_cry);
					} else {
						drawable = null;
					}

					// Important
					if (drawable != null) {
						drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
					}
				} catch (Exception e) {
					Log.e("DroidWriterTestActivity", "Failed to load inline image!");
				}
				return drawable;
			}
		});
		dwEdit.setSingleLine(false);
		dwEdit.setMinLines(10);
		dwEdit.setBoldToggleButton(boldToggle);
		dwEdit.setItalicsToggleButton(italicsToggle);
		dwEdit.setUnderlineToggleButton(underlinedToggle);

		dwEdit.setImageInsertButton(coolButton, "smiley_cool.gif");
		dwEdit.setImageInsertButton(cryButton, "smiley_cry.gif");

		dwEdit.setClearButton(clearButton);
	}
}