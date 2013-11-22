package br.com.carrinho.carrinhocomprasapp;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
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
