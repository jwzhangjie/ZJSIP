package com.jwzhangjie.pjsip.ui.dialpad;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 数字输入,暂时不支持字母输入所以把软键盘全部屏蔽
 * @author jwzhangjie
 */
public class DigitsEditText extends EditText {

	public DigitsEditText(Context context) {
		super(context);
	}

	public DigitsEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DigitsEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		final boolean ret = super.onTouchEvent(event);
		// Must be done after super.onTouchEvent()
		applyKeyboardShowHide();
		return ret;
	}

	@Override
	public void layout(int l, int t, int r, int b) {
		super.layout(l, t, r, b);
		applyKeyboardShowHide();
	}

	@Override
	protected void onFocusChanged(boolean focused, int direction,
			Rect previouslyFocusedRect) {
		super.onFocusChanged(focused, direction, previouslyFocusedRect);
		if (focused) {
			applyKeyboardShowHide();
		} else {
			final InputMethodManager imm = ((InputMethodManager) getContext()
					.getSystemService(Context.INPUT_METHOD_SERVICE));
			if (imm != null && imm.isActive(this)) {
				imm.hideSoftInputFromWindow(getApplicationWindowToken(), 0);
			}
		}
	}

	private void applyKeyboardShowHide() {
		final InputMethodManager imm = ((InputMethodManager) getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE));
		if (imm != null) {
			if (imm.isActive(this)) {
				imm.hideSoftInputFromWindow(getApplicationWindowToken(), 0);
			}
		}
	}

}
