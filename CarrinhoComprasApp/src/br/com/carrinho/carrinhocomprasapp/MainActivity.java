package br.com.carrinho.carrinhocomprasapp;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import br.com.carrinho.dao.ProdutoDAO;
import br.com.carrinho.model.Produto;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class MainActivity extends Activity {

	List<Produto> carrinho = new ArrayList<Produto>();
	ListView listView;
	
	private TextView contentTxt;
	ProdutoAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		contentTxt = (TextView)findViewById(R.id.scan_content);
		
		 listView = (ListView) findViewById(R.id.listView_produtos);
		 
		 AsyncHttpClient client = new AsyncHttpClient();
	        client.get("http://infnet.aws.af.cm/ofertas.php", new AsyncHttpResponseHandler() {
	            @Override
	            public void onSuccess(String response) {
//	              TextView tx = (TextView) findViewById(R.id.textViewHello);
	              try {
//	            	  listar os produtos depois
//	            	  carrinho = parseJsonToListProduto(new String(response.getBytes(),"UTF-8"));
	            	  adapter = new ProdutoAdapter();
	            	  listView.setAdapter(adapter);
	            	  
//					tx.setText(new String(response.getBytes(),"UTF-8"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            }
	            private List<Produto> parseJsonToListProduto(String string) {
	            	JSONObject obj = null;
	            	List<Produto> produtos = new ArrayList<Produto>();
	            	try {
	        			obj = new JSONObject(string);
	        			JSONObject objResult = obj.getJSONObject("Result");
	        			JSONArray discountListArray = objResult.getJSONArray("DiscountList");
	        			for(int i=0;i<discountListArray.length();i++){
	        				String imageUrl = discountListArray.getJSONObject(i).getString("SmallImageUrlOrDefault");
	        				String preco = discountListArray.getJSONObject(i).getString("DiscountedValue");
	        				String nome = discountListArray.getJSONObject(i).getString("MobileName");
//	        				String precoAntigo = discountListArray.getJSONObject(i).getString("OriginalValue");
//	        				String numOfertaVend = discountListArray.getJSONObject(i).getString("NumSold");
//	        				String percentDisconto = discountListArray.getJSONObject(i).getString("PercentageDiscount");
//	        				
	        				Produto p = new Produto();
	        				
	        				p.setUrlImage(imageUrl);
	        				p.setPreco(preco);
	        				p.setNomeProduto(nome);
	        				
	        				produtos.add(p);
	        			}
	        			
	        		} catch (JSONException e) {
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        		}
	            	
	            	return produtos;
	        	}
				
	        });
	        
		
		scheduleAlarmReceiver();
	}

	class ProdutoAdapter extends BaseAdapter{

		public int getCount() {
			return carrinho.size();
		}

		public Produto getItem(int position) {
			return carrinho.get(position);
		}

		public long getItemId(int position) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			
			View view = convertView;
			if(view == null){
				view = getLayoutInflater().inflate(R.layout.linha_produto, null);
			}
			
			ImageView image = (ImageView) view.findViewById(R.id.imageView1);
			TextView txNome = (TextView) view.findViewById(R.id.textView1);
			TextView txPreco = (TextView) view.findViewById(R.id.textView2);
			
			Produto p = getItem(position);
			
			image.setImageResource(p.getImageID());
			UrlImageViewHelper.setUrlDrawable(image, p.getUrlImage());
			
			txNome.setText(p.getNomeProduto());
			txPreco.setText(p.getPreco());
			
			return view;
		}
    	
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
	
	public void scan(View view){
		
		IntentIntegrator scanIntegrator = new IntentIntegrator(this);
//		
		scanIntegrator.initiateScan();
		
//		adicionarProdutoCarrinho("123456");
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		
		IntentResult scanningResult =  IntentIntegrator.parseActivityResult(requestCode,resultCode,intent);
		
		String scanContent = scanningResult.getContents();

//		contentTxt.setText("Resultado adicionando no carrinho");
		adicionarProdutoCarrinho(scanContent);
//		String scanFormat = scanningResult.getFormatName();
		
		
		
		
	}
	
	public void adicionarProdutoCarrinho(String codigoBarras){
		
		ProdutoDAO dao = new ProdutoDAO(this);
		Log.i(Constants.LOG_TAG,
				"Buscando o produto com codigo de barras "+codigoBarras);
		contentTxt.setText("");
		
		Produto p = dao.get(codigoBarras);
		
		if(p != null){
			Log.i(Constants.LOG_TAG,
					"Produto encontrado adicionando no carrinho");
//			contentTxt.setText("Produto encontrado adicionando no carrinho");
			carrinho.add(p);
			adapter.notifyDataSetChanged();

//			contentTxt.setText("CONTENT: " + codigoBarras + "Produto: "+p.getNomeProduto());
		}else{
			contentTxt.setText("Produto nao encontrado");
		}
	}

}

	