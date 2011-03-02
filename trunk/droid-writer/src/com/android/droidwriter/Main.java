package com.android.droidwriter;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Main extends Activity {
	
	private static final String TAG = "RTEditor";
	
	private int styleStart;
	private int cursorLoc;
	
	private ToggleButton boldToggle;
	private ToggleButton italicsToggle;
	private ToggleButton underlineToggle;
	private ToggleButton uListToggle;
	private ToggleButton oListToggle;
	
	private Button smiley1Button;
	private Button smiley2Button;
	private Button smiley3Button;
	private Button clearButton;
	
	private EditText editor;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        styleStart = 0;
        cursorLoc = 0;
        
        boldToggle = (ToggleButton)findViewById(R.id.BoldToggle);
    	italicsToggle = (ToggleButton)findViewById(R.id.ItalicsToggle);
    	underlineToggle = (ToggleButton)findViewById(R.id.UnderlineToggle);
    	uListToggle = (ToggleButton)findViewById(R.id.UnorderedListToggle);
    	oListToggle = (ToggleButton)findViewById(R.id.OrderedListToggle);
    	
    	smiley1Button = (Button)findViewById(R.id.SmileyButton1);
    	smiley2Button = (Button)findViewById(R.id.SmileyButton2);
    	smiley3Button = (Button)findViewById(R.id.SmileyButton3);
    	clearButton = (Button)findViewById(R.id.ClearButton);
    	
    	editor = (EditText)findViewById(R.id.Editor);
    	
    	// Handling the styling buttons
    	
    	boldToggle.setOnClickListener(new Button.OnClickListener() {
	            public void onClick(View v) {
	                int selectionStart = editor.getSelectionStart();
	
	                styleStart = selectionStart;
	
	                int selectionEnd = editor.getSelectionEnd();
	
	                if (selectionStart > selectionEnd){
	                    int temp = selectionEnd;
	                    selectionEnd = selectionStart;
	                    selectionStart = temp;
	                }
	
	
	                if (selectionEnd > selectionStart)
	                {
	                    Spannable str = editor.getText();
	                    StyleSpan[] ss = str.getSpans(selectionStart, selectionEnd, StyleSpan.class);
	
	                    boolean exists = false;
	                    for (int i = 0; i < ss.length; i++) {
	                        if (ss[i].getStyle() == android.graphics.Typeface.BOLD){
	                            str.removeSpan(ss[i]);
	                            exists = true;
	                        }
	                    }
	
	                    if (!exists){
	                        str.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), selectionStart, selectionEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	                    }
	
	                    boldToggle.setChecked(false);
	                }
	            }
    	});
    	
    	italicsToggle.setOnClickListener(new Button.OnClickListener() {
	            public void onClick(View v) {
	                int selectionStart = editor.getSelectionStart();
	
	                styleStart = selectionStart;
	
	                int selectionEnd = editor.getSelectionEnd();
	
	                if (selectionStart > selectionEnd){
	                    int temp = selectionEnd;
	                    selectionEnd = selectionStart;
	                    selectionStart = temp;
	                }
	
	
	                if (selectionEnd > selectionStart)
	                {
	                    Spannable str = editor.getText();
	                    StyleSpan[] ss = str.getSpans(selectionStart, selectionEnd, StyleSpan.class);
	
	                    boolean exists = false;
	                    for (int i = 0; i < ss.length; i++) {
	                        if (ss[i].getStyle() == android.graphics.Typeface.ITALIC){
	                            str.removeSpan(ss[i]);
	                            exists = true;
	                        }
	                    }
	
	                    if (!exists){
	                        str.setSpan(new StyleSpan(android.graphics.Typeface.ITALIC), selectionStart, selectionEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	                    }
	
	                    italicsToggle.setChecked(false);
	                }
	            }
		});
    	
    	underlineToggle.setOnClickListener(new Button.OnClickListener() {
	            public void onClick(View v) {
	                int selectionStart = editor.getSelectionStart();
	
	                styleStart = selectionStart;
	
	                int selectionEnd = editor.getSelectionEnd();
	
	                if (selectionStart > selectionEnd){
	                    int temp = selectionEnd;
	                    selectionEnd = selectionStart;
	                    selectionStart = temp;
	                }
	
	
	                if (selectionEnd > selectionStart)
	                {
	                    Spannable str = editor.getText();
	                    UnderlineSpan[] ss = str.getSpans(selectionStart, selectionEnd, UnderlineSpan.class);
	
	                    boolean exists = false;
	                    for (int i = 0; i < ss.length; i++) {
	                        //if (ss[i].getStyle() == android.graphics.Typeface.BOLD){
	                            str.removeSpan(ss[i]);
	                            exists = true;
	                        //}
	                    }
	
	                    if (!exists){
	                        str.setSpan(new UnderlineSpan(), selectionStart, selectionEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	                    }
	
	                    underlineToggle.setChecked(false);
	                }
	            }
		});
    	
    	// Handling the smiley buttons
    	
    	smiley1Button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				editor.append(":-)");
				Spannable text = editor.getText();
				text.setSpan(new ImageSpan(BitmapFactory.decodeResource(getResources(), R.drawable.smiley1), DynamicDrawableSpan.ALIGN_BASELINE), 
						text.length()-3, text.length(), 
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
		});
    	
    	smiley2Button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				editor.append(":-(");
				Spannable text = editor.getText();
				text.setSpan(new ImageSpan(BitmapFactory.decodeResource(getResources(), R.drawable.smiley2), DynamicDrawableSpan.ALIGN_BASELINE), 
						text.length()-3, text.length(), 
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
		});
    	
    	smiley3Button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				editor.append(":-D");
				Spannable text = editor.getText();
				text.setSpan(new ImageSpan(BitmapFactory.decodeResource(getResources(), R.drawable.smiley3), DynamicDrawableSpan.ALIGN_BASELINE), 
						text.length()-3, text.length(), 
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
		});
    
    	// Handling the text editor stuff
    	
    	editor.addTextChangedListener(new TextWatcher() { 
	            public void afterTextChanged(Editable s) { 
	                //add style as the user types if a toggle button is enabled
	                int position = Selection.getSelectionStart(editor.getText());
	                if (position < 0){
	                    position = 0;
	                }
	
	                if (position > 0){
	
	                    if (styleStart > position || position > (cursorLoc + 1)){
	                        //user changed cursor location, reset
	                        styleStart = position - 1;
	                    }
	
	                    cursorLoc = position;
	
	                    if (boldToggle.isChecked()){  
	                        StyleSpan[] ss = s.getSpans(styleStart, position, StyleSpan.class);
	
	                        for (int i = 0; i < ss.length; i++) {
	                            if (ss[i].getStyle() == android.graphics.Typeface.BOLD){
	                                s.removeSpan(ss[i]);
	                            }
	                        }
	                        s.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), styleStart, position, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	                    }
	                    
	                    if (italicsToggle.isChecked()){  
	                        StyleSpan[] ss = s.getSpans(styleStart, position, StyleSpan.class);
	
	                        for (int i = 0; i < ss.length; i++) {
	                            if (ss[i].getStyle() == android.graphics.Typeface.ITALIC){
	                                s.removeSpan(ss[i]);
	                            }
	                        }
	                        s.setSpan(new StyleSpan(android.graphics.Typeface.ITALIC), styleStart, position, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	                    }
	                    
	                    if (underlineToggle.isChecked()){  
	                    	UnderlineSpan[] ss = s.getSpans(styleStart, position, UnderlineSpan.class);
	
	                        for (int i = 0; i < ss.length; i++) {
	                            //if (ss[i].getStyle() == android.graphics.Typeface.ITALIC){
	                                s.removeSpan(ss[i]);
	                            //}
	                        }
	                        s.setSpan(new UnderlineSpan(), styleStart, position, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	                    }
	                }
	            } 
	            public void beforeTextChanged(CharSequence s, int start, int count, int after) { 
	                    //unused
	            } 
	            public void onTextChanged(CharSequence s, int start, int before, int count) { 
	                    //unused
	            } 
    	});
    	
    	clearButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//editor.setText("");
				Toast.makeText(getApplicationContext(), editor.getText().toString(), Toast.LENGTH_SHORT).show();
			}
		});
    }
}