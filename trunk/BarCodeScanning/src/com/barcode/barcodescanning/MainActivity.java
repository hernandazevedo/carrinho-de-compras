package com.barcode.barcodescanning;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends Activity {
	
	private TextView formatTxt, contentTxt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		formatTxt = (TextView)findViewById(R.id.scan_format);
		contentTxt = (TextView)findViewById(R.id.scan_content);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void scan(View view){
		
		IntentIntegrator scanIntegrator = new IntentIntegrator(this);
		
		scanIntegrator.initiateScan();
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		
		IntentResult scanningResult =  IntentIntegrator.parseActivityResult(requestCode,resultCode,intent);
		
		String scanContent = scanningResult.getContents();

		String scanFormat = scanningResult.getFormatName();
		
		formatTxt.setText("FORMAT: " + scanFormat);
		contentTxt.setText("CONTENT: " + scanContent);
	}
}
