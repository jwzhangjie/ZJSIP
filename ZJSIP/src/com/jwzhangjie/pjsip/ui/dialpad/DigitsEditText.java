package com.jwzhangjie.pjsip.ui.dialpad;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityEvent;
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
    public void sendAccessibilityEventUnchecked(AccessibilityEvent event) {
        if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED) {
            // Since we're replacing the text every time we add or remove a
            // character, only read the difference. (issue 5337550)
            final int added = event.getAddedCount();
            final int removed = event.getRemovedCount();
            final int length = event.getBeforeText().length();
            if (added > removed) {
                event.setRemovedCount(0);
                event.setAddedCount(1);
                event.setFromIndex(length);
            } else if (removed > added) {
                event.setRemovedCount(1);
                event.setAddedCount(0);
                event.setFromIndex(length - 1);
            } else {
                return;
            }
        } else if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_FOCUSED) {
            // The parent EditText class lets tts read "edit box" when this View
            // has a focus, which
            // confuses users on app launch (issue 5275935).
            return;
        }
        super.sendAccessibilityEventUnchecked(event);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        // Here we ensure that we hide the keyboard
        // Since this will be fired when virtual keyboard this will probably
        // blink but for now no better way were found to hide keyboard for sure
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
