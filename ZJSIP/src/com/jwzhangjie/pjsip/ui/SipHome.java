package com.jwzhangjie.pjsip.ui;

import com.jwzhangjie.pjsip.R;
import com.jwzhangjie.pjsip.ui.dialpad.DigitsEditText;
import com.jwzhangjie.pjsip.widgets.Dialpad.OnDialKeyListener;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;

public class SipHome extends SipBase implements OnClickListener, OnLongClickListener, OnDialKeyListener{

	private DigitsEditText digits;
	private String initText = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sip_home);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.sip_home, menu);
		return true;
	}

	@Override
	public void onTrigger(int keyCode, int dialTone) {
		
	}

	@Override
	public boolean onLongClick(View v) {
		return false;
	}

	@Override
	public void onClick(View v) {
		
	}

}
