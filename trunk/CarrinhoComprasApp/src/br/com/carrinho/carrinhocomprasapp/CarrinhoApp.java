package br.com.carrinho.carrinhocomprasapp;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;

public class CarrinhoApp extends Application {

	private ConnectivityManager cMgr;

	//
	// lifecycle
	//
	@Override
	public void onCreate() {
		super.onCreate();
		this.cMgr = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		// this.parser = new DailyDealsXmlPullFeedParser();
		// this.sectionList = new ArrayList<Section>(6);
		// this.imageCache = new HashMap<Long, Bitmap>();
		// this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
	}

	@Override
	public void onTerminate() {
		// not guaranteed to be called
		super.onTerminate();
	}
	
	// Schedule AlarmManager to invoke DealAlarmReceiver and cancel any existing
		// current PendingIntent
		// we do this because we *also* invoke the receiver from a BOOT_COMPLETED
		// receiver
		// so that we make sure the service runs either when app is
		// installed/started, or when device boots
		public void scheduleAlarmReceiver() {
			
			
			scheduleAlarmReceiver(this);
		}
		
		// Schedule AlarmManager to invoke DealAlarmReceiver and cancel any existing
				// current PendingIntent
				// we do this because we *also* invoke the receiver from a BOOT_COMPLETED
				// receiver
				// so that we make sure the service runs either when app is
				// installed/started, or when device boots
				public static void scheduleAlarmReceiver(Context ctx) {
					
					
					AlarmManager alarmMgr = (AlarmManager) ctx
							.getSystemService(Context.ALARM_SERVICE);
					PendingIntent pendingIntent = PendingIntent.getBroadcast(ctx, 0,
							new Intent(ctx, CarrinhoAlarmReceiver.class),
							PendingIntent.FLAG_CANCEL_CURRENT);

					
					String freq = PreferenceManager.getDefaultSharedPreferences(ctx).getString("sync_frequency", null);
					
					if(freq == null || freq.equals("-1")) return;
				   
				   long intervalo = Constants.ALARM_INTERVAL;
				   if(freq.equals("10"))
					   intervalo =  Constants.ALARM_INTERVAL_TEN_SECODS;
				   else if(freq.equals("60"))
					   intervalo =  Constants.ALARM_INTERVAL_HOUR;
					
					
					// Use inexact repeating which is easier on battery (system can phase
					// events and not wake at exact times)
					alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
							Constants.ALARM_TRIGGER_AT_TIME, intervalo,
							pendingIntent);
				}
		
				
				// Schedule AlarmManager to invoke DealAlarmReceiver and cancel any existing
				// current PendingIntent
				// we do this because we *also* invoke the receiver from a BOOT_COMPLETED
				// receiver
				// so that we make sure the service runs either when app is
				// installed/started, or when device boots
				public static void scheduleAlarmReceiver(Context ctx,String freq) {
					
					
					AlarmManager alarmMgr = (AlarmManager) ctx
							.getSystemService(Context.ALARM_SERVICE);
					PendingIntent pendingIntent = PendingIntent.getBroadcast(ctx, 0,
							new Intent(ctx, CarrinhoAlarmReceiver.class),
							PendingIntent.FLAG_CANCEL_CURRENT);

					
					
					if(freq == null || freq.equals("-1")) return;
				   
				   long intervalo = Constants.ALARM_INTERVAL;
				   if(freq.equals("10"))
					   intervalo =  Constants.ALARM_INTERVAL_TEN_SECODS;
				   else if(freq.equals("60"))
					   intervalo =  Constants.ALARM_INTERVAL_HOUR;
					
					
					// Use inexact repeating which is easier on battery (system can phase
					// events and not wake at exact times)
					alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
							Constants.ALARM_TRIGGER_AT_TIME, intervalo,
							pendingIntent);
				}

	public boolean connectionPresent() {
		NetworkInfo netInfo = cMgr.getActiveNetworkInfo();
		if ((netInfo != null) && (netInfo.getState() != null)) {
			return netInfo.getState().equals(State.CONNECTED);
		}
		return false;
	}
	
	public String getImei(){
		String imei = null;
		try{
			 TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
			 imei = telephonyManager.getDeviceId();
		 }catch (Exception e) {
			 imei = "11233455";
		 }
		 if(imei == null){
			 imei = "11233455";
		 }
		 return imei;
	}
}
