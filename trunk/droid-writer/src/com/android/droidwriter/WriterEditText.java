package com.android.droidwriter;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class WriterEditText extends EditText {

	private SelectionChangedListener selectionListener;
	
	public WriterEditText(Context context) {
		super(context);
	}
	
	public WriterEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public WriterEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onSelectionChanged(int selStart, int selEnd) {
		super.onSelectionChanged(selStart, selEnd);
		
		if(selectionListener != null) selectionListener.onSelectionChanged(selStart, selEnd);
	}
	
	public void setOnSelectionChangedListener(SelectionChangedListener selectionListener){
		this.selectionListener = selectionListener;
	}
}
