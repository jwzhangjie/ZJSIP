package com.jwzhangjie.pjsip.ui;

import com.jwzhangjie.pjsip.R;
import com.jwzhangjie.pjsip.ui.dialpad.DigitsEditText;
import com.jwzhangjie.pjsip.widgets.DialerCallBar;
import com.jwzhangjie.pjsip.widgets.DialerCallBar.OnDialActionListener;
import com.jwzhangjie.pjsip.widgets.Dialpad;
import com.jwzhangjie.pjsip.widgets.Dialpad.OnDialKeyListener;

import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.text.method.DialerKeyListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class SipHome extends SipBase implements OnClickListener,
		OnLongClickListener, OnDialKeyListener, TextWatcher, OnDialActionListener{

	private DigitsEditText digits;
	private String initText = null;
	private Dialpad dialPad;
	private DialerCallBar callBar;
	private final int[] buttonsToLongAttach = new int[] { R.id.button0,
			R.id.button1 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sip_home);
		digits = (DigitsEditText) findViewById(R.id.digitsText);
		digits.setKeyListener(DialerKeyListener.getInstance());
		digits.addTextChangedListener(this);
		digits.setCursorVisible(false);
		dialPad = (Dialpad) findViewById(R.id.dialPad);
		dialPad.setOnDialKeyListener(this);
		digits.setOnEditorActionListener(keyboardActionListener);
		for (int buttonId : buttonsToLongAttach) {
			attachButtonListener(buttonId, true);
		}
		callBar = (DialerCallBar) findViewById(R.id.dialerCallBar);
		callBar.setOnDialActionListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.sip_home, menu);
		return true;
	}

	/**
	 * Set the value of the text field and put caret at the end
	 * 
	 * @param value
	 *            the new text to see in the text field
	 */
	public void setTextFieldValue(CharSequence value) {
		if (digits == null) {
			initText = value.toString();
			return;
		}
		digits.setText(value);
		// make sure we keep the caret at the end of the text view
		Editable spannable = digits.getText();
		Selection.setSelection(spannable, spannable.length());
	}

	@Override
	public void onTrigger(int keyCode, int dialTone) {
		keyPressed(keyCode);
	}

	private void keyPressed(int keyCode) {
		KeyEvent event = new KeyEvent(KeyEvent.ACTION_DOWN, keyCode);
		digits.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onLongClick(View v) {
		int vId = v.getId();
		if (vId == R.id.button0) {
			keyPressed(KeyEvent.KEYCODE_PLUS);
			return true;
		} else if (vId == R.id.button1) {
			if (digits.length() == 0) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button0:
			
			break;

		case R.id.button1:
			digits.setText(null);
			break;
		}
	}

	private void attachButtonListener(int id, boolean longAttach) {
		ImageButton button = (ImageButton) findViewById(id);
		if (button == null) {
			return;
		}
		if (longAttach) {
			button.setOnLongClickListener(this);
		} else {
			button.setOnClickListener(this);
		}
	}

	private OnEditorActionListener keyboardActionListener = new OnEditorActionListener() {
		@Override
		public boolean onEditorAction(TextView tv, int action, KeyEvent arg2) {
			if (action == EditorInfo.IME_ACTION_GO) {
				return true;
			}
			return false;
		}
	};

	@Override
	public void afterTextChanged(Editable arg0) {

	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {

	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		afterTextChanged(digits.getText());
	}

	@Override
	public void placeCall() {
		
	}

	@Override
	public void placeVideoCall() {
		
	}

	@Override
	public void deleteChar() {
		keyPressed(KeyEvent.KEYCODE_DEL);
	}

	@Override
	public void deleteAll() {
		 digits.getText().clear();
	}

}
