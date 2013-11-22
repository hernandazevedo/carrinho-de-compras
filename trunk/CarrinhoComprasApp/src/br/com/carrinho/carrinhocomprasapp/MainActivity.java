package br.com.carrinho.carrinhocomprasapp;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		scheduleAlarmReceiver();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private String getImei() {
		String imei = null;
		try {
			TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			imei = telephonyManager.getDeviceId();
		} catch (Exception e) {
			imei = "11233455";
		}
		if (imei == null) {
			imei = "11233455";
		}
		return imei;
	}

	public void processSync(View v) {
		new SyncService().doSync(getImei(), this);
	}

	// Schedule AlarmManager to invoke DealAlarmReceiver and cancel any existing
	// current PendingIntent
	// we do this because we *also* invoke the receiver from a BOOT_COMPLETED
	// receiver
	// so that we make sure the service runs either when app is
	// installed/started, or when device boots
	private void scheduleAlarmReceiver() {
		AlarmManager alarmMgr = (AlarmManager) this
				.getSystemService(Context.ALARM_SERVICE);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
				new Intent(this, CarrinhoAlarmReceiver.class),
				PendingIntent.FLAG_CANCEL_CURRENT);

		// Use inexact repeating which is easier on battery (system can phase
		// events and not wake at exact times)
		alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
				Constants.ALARM_TRIGGER_AT_TIME, Constants.ALARM_INTERVAL,
				pendingIntent);
	}

}
