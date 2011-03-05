package com.android.droidwriter;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.style.AlignmentSpan;
import android.text.style.BulletSpan;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.text.style.QuoteSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.webkit.WebStorage.QuotaUpdater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

public class Main extends Activity implements SelectionChangedListener {
	
	// Log tag
	private static final String TAG = "DroidWriter";
	
	// Style constants
	private static final int STYLE_BOLD = 0;
	private static final int STYLE_ITALIC = 1;
	private static final int STYLE_UNDERLINED = 2;
	private static final int STYLE_ALIGN_NORMAL = 3;
	private static final int STYLE_ALIGN_CENTER = 4;
	private static final int STYLE_ALIGN_OPPOSITE = 5;
	private static final int STYLE_TEST = 20;
	
	// Styling button references
	private ToggleButton boldToggle;
	private ToggleButton italicsToggle;
	private ToggleButton underlineToggle;
	private ToggleButton uListToggle;
	private ToggleButton oListToggle;
	
	// Alignment button references
	private ToggleButton normalAlignToggle;
	private ToggleButton centerAlignToggle;
	private ToggleButton oppositeAlignToggle;
	
	// Buttons for inserting images
	private Button smiley1Button;
	private Button smiley2Button;
	private Button smiley3Button;
	
	// Misc widgets
	private Button clearButton;
	private Button testButton;
	
	// The mighty EditText
	private WriterEditText editor;
	
	// Some state variables
	private int styleStart;
	private int cursorLoc;
	
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
    	testButton = (Button)findViewById(R.id.TestButton);
    	
    	normalAlignToggle = (ToggleButton)findViewById(R.id.AlignNormalToggle);
    	centerAlignToggle = (ToggleButton)findViewById(R.id.AlignCenterToggle);
    	oppositeAlignToggle = (ToggleButton)findViewById(R.id.AlignOppositeToggle);
    	
    	editor = (WriterEditText)findViewById(R.id.Editor);
    	editor.setOnSelectionChangedListener(this);
    	
    	// Handling the styling buttons
    	boldToggle.setOnClickListener(new Button.OnClickListener() {
	            public void onClick(View v) {
	            	toggleStyle(STYLE_BOLD);
	            	Log.d(TAG, "bold toggle!");
	            }
    	});
    	
    	italicsToggle.setOnClickListener(new Button.OnClickListener() {
	            public void onClick(View v) {
	                toggleStyle(STYLE_ITALIC);
	            }
		});
    	
    	underlineToggle.setOnClickListener(new Button.OnClickListener() {
	            public void onClick(View v) {
	                toggleStyle(STYLE_UNDERLINED);
	            }
		});
    	
    	normalAlignToggle.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                toggleStyle(STYLE_ALIGN_NORMAL);
            }
    	});
    	
    	centerAlignToggle.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                toggleStyle(STYLE_ALIGN_CENTER);
            }
    	});
    	
    	oppositeAlignToggle.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                toggleStyle(STYLE_ALIGN_OPPOSITE);
            }
    	});
    	
    	// Handling the image inserting buttons
    	
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
	                // When the user types in new characters, we react to this with applying
	            	// the selected style(s) onto this new character.
	            	
	            	// Add style as the user types if a toggle button is enabled
	                int position = Selection.getSelectionStart(editor.getText());
	                if (position < 0){
	                    position = 0;
	                }
	
	                if (position > 0){
	
	                    if (styleStart > position || position > (cursorLoc + 1)){
	                        // The user changed cursor location, reset styleStart
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
	                        s.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), styleStart, 
	                        		position, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	                    }
	                    
	                    if (italicsToggle.isChecked()){  
	                        StyleSpan[] ss = s.getSpans(styleStart, position, StyleSpan.class);
	
	                        for (int i = 0; i < ss.length; i++) {
	                            if (ss[i].getStyle() == android.graphics.Typeface.ITALIC){
	                                s.removeSpan(ss[i]);
	                            }
	                        }
	                        s.setSpan(new StyleSpan(android.graphics.Typeface.ITALIC), styleStart, 
	                        		position, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	                    }
	                    
	                    if (underlineToggle.isChecked()){  
	                    	UnderlineSpan[] ss = s.getSpans(styleStart, position, UnderlineSpan.class);
	
	                        for (int i = 0; i < ss.length; i++) {
	                            s.removeSpan(ss[i]);
	                        }
	                        s.setSpan(new UnderlineSpan(), styleStart, 
	                        		position, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	                    }
	                }
	            }
	            
	            public void beforeTextChanged(CharSequence s, int start, int count, int after) { 
	                    // Unused
	            } 
	            
	            public void onTextChanged(CharSequence s, int start, int before, int count) { 
	                    // Unused
	            } 
    	});
    	
    	// Clear the entered text
    	clearButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				editor.setText("");
			}
		});
    	
    	testButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				toggleStyle(STYLE_TEST);
			}
		});
    
    }
    
    private void toggleStyle(int style){
    	// Gets the current cursor position, or the starting position of the selection
        int selectionStart = editor.getSelectionStart();
        styleStart = selectionStart;

        // Gets the current cursor position, or the end position of the selection
        // Note: The end can be smaller than the start
        int selectionEnd = editor.getSelectionEnd();
        
        // Reverse if the case is what's noted above
        if (selectionStart > selectionEnd){
            int temp = selectionEnd;
            selectionEnd = selectionStart;
            selectionStart = temp;
        }

        if (selectionEnd > selectionStart)
        {
            Spannable str = editor.getText();
            boolean exists = false;
            StyleSpan[] styleSpans;
            AlignmentSpan.Standard[] alignSpan;
            
            switch(style){
            case STYLE_BOLD:
            	styleSpans = str.getSpans(selectionStart, selectionEnd, StyleSpan.class);
            	
            	// If the selected text-part already has BOLD style on it, then we need to disable it
                for (int i = 0; i < styleSpans.length; i++) {
                    if (styleSpans[i].getStyle() == android.graphics.Typeface.BOLD){
                        str.removeSpan(styleSpans[i]);
                        exists = true;
                    }
                }
                
                // Else we set BOLD style on it
                if (!exists){
                    str.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), selectionStart, 
                    		selectionEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }

                boldToggle.setChecked(false);
            	break;
            case STYLE_ITALIC:
            	styleSpans = str.getSpans(selectionStart, selectionEnd, StyleSpan.class);
            	
            	// If the selected text-part already has ITALIC style on it, then we need to disable it
                for (int i = 0; i < styleSpans.length; i++) {
                    if (styleSpans[i].getStyle() == android.graphics.Typeface.ITALIC){
                        str.removeSpan(styleSpans[i]);
                        exists = true;
                    }
                }
                
                // Else we set ITALIC style on it
                if (!exists){
                    str.setSpan(new StyleSpan(android.graphics.Typeface.ITALIC), selectionStart, 
                    		selectionEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }

                italicsToggle.setChecked(false);
            	break;
            case STYLE_UNDERLINED:
            	UnderlineSpan[] underSpan = str.getSpans(selectionStart, selectionEnd, UnderlineSpan.class);
            	
            	// If the selected text-part already has UNDERLINE style on it, then we need to disable it
                for (int i = 0; i < underSpan.length; i++) {
                    str.removeSpan(underSpan[i]);
                    exists = true;
                }
                
                // Else we set UNDERLINE style on it
                if (!exists){
                    str.setSpan(new UnderlineSpan(), selectionStart, selectionEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }

                underlineToggle.setChecked(false);
            	break;
            case STYLE_ALIGN_NORMAL:
            	alignSpan = str.getSpans(selectionStart, selectionEnd, AlignmentSpan.Standard.class);
            	
            	// If the selected text-part already has UNDERLINE style on it, then we need to disable it
                for (int i = 0; i < alignSpan.length; i++) {
                    str.removeSpan(alignSpan[i]);
                    exists = true;
                }
                
                // Else we set UNDERLINE style on it
                if (!exists){
                    str.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_NORMAL), selectionStart, selectionEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            	break;
            case STYLE_ALIGN_CENTER:
            	alignSpan = str.getSpans(selectionStart, selectionEnd, AlignmentSpan.Standard.class);
            	
            	// If the selected text-part already has UNDERLINE style on it, then we need to disable it
                for (int i = 0; i < alignSpan.length; i++) {
                    str.removeSpan(alignSpan[i]);
                    exists = true;
                }
                
                // Else we set UNDERLINE style on it
                if (!exists){
                    str.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), selectionStart, selectionEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            	break;
            case STYLE_ALIGN_OPPOSITE:
            	alignSpan = str.getSpans(selectionStart, selectionEnd, AlignmentSpan.Standard.class);
            	
            	// If the selected text-part already has UNDERLINE style on it, then we need to disable it
                for (int i = 0; i < alignSpan.length; i++) {
                    str.removeSpan(alignSpan[i]);
                    exists = true;
                }
                
                // Else we set UNDERLINE style on it
                if (!exists){
                    str.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE), selectionStart, selectionEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            	break;
            case STYLE_TEST:
            	BulletSpan[] quoteSpan = str.getSpans(selectionStart, selectionEnd, BulletSpan.class);
            	
            	// If the selected text-part already has UNDERLINE style on it, then we need to disable it
                for (int i = 0; i < quoteSpan.length; i++) {
                    str.removeSpan(quoteSpan[i]);
                    exists = true;
                }
                
                // Else we set UNDERLINE style on it
                if (!exists){
                    str.setSpan(new BulletSpan(), selectionStart, selectionEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            	break;
            }
        }
	}

	@Override
	public void onSelectionChanged(int selStart, int selEnd) {
		Log.d(TAG, "Selection changed selStart:"+selStart+ " selEnd:"+selEnd);
		/*
		if(selStart > 0){
			Spannable str = editor.getText();
			StyleSpan[] styleSpans = str.getSpans(selStart - 1, selStart, StyleSpan.class);
	    	boolean exists = false;
			
	    	// If the selected text-part already has BOLD style on it, then we need to disable it
	        for (int i = 0; i < styleSpans.length; i++) {
	            if (styleSpans[i].getStyle() == android.graphics.Typeface.BOLD){
	                exists = true;
	            }
	        }
	        if(exists)boldToggle.setChecked(true);
	        else boldToggle.setChecked(false);
		}*/
	}
}