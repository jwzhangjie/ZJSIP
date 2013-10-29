package com.jwzhangjie.pjsip.widgets;

import java.util.HashMap;
import java.util.Map;

import com.jwzhangjie.pjsip.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.ToneGenerator;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageButton;

public class Dialpad extends FrameLayout implements OnClickListener {

	private OnDialKeyListener onDialKeyListener;

	public Dialpad(Context context) {
		super(context);
		initLayout(context);
	}

	public Dialpad(Context context, AttributeSet attrs) {
		super(context, attrs);
		initLayout(context);
	}

	private void initLayout(Context context) {
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.dialpad, this, true);
	}

//	 Here we need a map to quickly find if the clicked button id is in the map
//	 keys,之所以这里不用SparseArrays，因为下面取值的方便
	@SuppressLint("UseSparseArrays")
	private static final Map<Integer, int[]> DIGITS_BTNS = new HashMap<Integer, int[]>();
	static {
		DIGITS_BTNS.put(R.id.button0, new int[] { ToneGenerator.TONE_DTMF_0,
				KeyEvent.KEYCODE_0 });
		DIGITS_BTNS.put(R.id.button1, new int[] { ToneGenerator.TONE_DTMF_1,
				KeyEvent.KEYCODE_1 });
		DIGITS_BTNS.put(R.id.button2, new int[] { ToneGenerator.TONE_DTMF_2,
				KeyEvent.KEYCODE_2 });
		DIGITS_BTNS.put(R.id.button3, new int[] { ToneGenerator.TONE_DTMF_3,
				KeyEvent.KEYCODE_3 });
		DIGITS_BTNS.put(R.id.button4, new int[] { ToneGenerator.TONE_DTMF_4,
				KeyEvent.KEYCODE_4 });
		DIGITS_BTNS.put(R.id.button5, new int[] { ToneGenerator.TONE_DTMF_5,
				KeyEvent.KEYCODE_5 });
		DIGITS_BTNS.put(R.id.button6, new int[] { ToneGenerator.TONE_DTMF_6,
				KeyEvent.KEYCODE_6 });
		DIGITS_BTNS.put(R.id.button7, new int[] { ToneGenerator.TONE_DTMF_7,
				KeyEvent.KEYCODE_7 });
		DIGITS_BTNS.put(R.id.button8, new int[] { ToneGenerator.TONE_DTMF_8,
				KeyEvent.KEYCODE_8 });
		DIGITS_BTNS.put(R.id.button9, new int[] { ToneGenerator.TONE_DTMF_9,
				KeyEvent.KEYCODE_9 });
		DIGITS_BTNS.put(R.id.buttonpound, new int[] {
				ToneGenerator.TONE_DTMF_P, KeyEvent.KEYCODE_POUND });
		DIGITS_BTNS.put(R.id.buttonstar, new int[] { ToneGenerator.TONE_DTMF_S,
				KeyEvent.KEYCODE_STAR });
	};

	/**
	 * SparseArray这个是android提供的，可以替换HashMap,来提高效率
	 */
	private static final SparseArray<String> DIGITS_NAMES = new SparseArray<String>();

	static {
		DIGITS_NAMES.put(R.id.button0, "0");
		DIGITS_NAMES.put(R.id.button1, "1");
		DIGITS_NAMES.put(R.id.button2, "2");
		DIGITS_NAMES.put(R.id.button3, "3");
		DIGITS_NAMES.put(R.id.button4, "4");
		DIGITS_NAMES.put(R.id.button5, "5");
		DIGITS_NAMES.put(R.id.button6, "6");
		DIGITS_NAMES.put(R.id.button7, "7");
		DIGITS_NAMES.put(R.id.button8, "8");
		DIGITS_NAMES.put(R.id.button9, "9");
		DIGITS_NAMES.put(R.id.buttonpound, "pound");
		DIGITS_NAMES.put(R.id.buttonstar, "star");
	};

	public interface OnDialKeyListener {

		/**
		 * Called when the user make an action
		 * 
		 * @param keyCode
		 *            keyCode pressed
		 * @param dialTone
		 *            corresponding dialtone
		 */
		void onTrigger(int keyCode, int dialTone);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		for (int buttonId : DIGITS_BTNS.keySet()) {
			ImageButton button = (ImageButton) findViewById(buttonId);
			if (button != null) {
				button.setOnClickListener(this);
			}
		}
	}

	/**
	 * Registers a callback to be invoked when the user triggers an event.
	 * 
	 * @param listener
	 *            the OnTriggerListener to attach to this view
	 */
	public void setOnDialKeyListener(OnDialKeyListener listener) {
		onDialKeyListener = listener;
	}

	private void dispatchDialKeyEvent(int buttonId) {
		if (onDialKeyListener != null && DIGITS_BTNS.containsKey(buttonId)) {
			int[] datas = DIGITS_BTNS.get(buttonId);
			onDialKeyListener.onTrigger(datas[1], datas[0]);
		}
	}

	@Override
	public void onClick(View v) {
		dispatchDialKeyEvent(v.getId());
	}
}
