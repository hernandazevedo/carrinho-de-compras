package br.com.carrinho.carrinhocomprasapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;

public class CarrinhoBootReceiver extends BroadcastReceiver {

	   @Override
	   public void onReceive(Context context, Intent intent) {
		   
		   String freq = PreferenceManager.getDefaultSharedPreferences(context).getString("sync_frequency", null);
			
			if(freq == null || freq.equals("-1")) return;
		   
		   long intervalo = Constants.ALARM_INTERVAL;
		   if(freq.equals("10"))
			   intervalo =  Constants.ALARM_INTERVAL_TEN_SECODS;
		   else if(freq.equals("60"))
			   intervalo =  Constants.ALARM_INTERVAL_HOUR;
		   
		   
	      Log.i(Constants.LOG_TAG, "DealBootReceiver invoked, configuring AlarmManager");
	      AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
	      PendingIntent pendingIntent =
	               PendingIntent.getBroadcast(context, 0, new Intent(context, CarrinhoAlarmReceiver.class), 0);

	      // use inexact repeating which is easier on battery (system can phase events and not wake at exact times)
	      alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, Constants.ALARM_TRIGGER_AT_TIME,
	    		  intervalo, pendingIntent);
	   }
}
