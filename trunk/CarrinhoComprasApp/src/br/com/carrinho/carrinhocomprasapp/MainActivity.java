package br.com.carrinho.carrinhocomprasapp;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import br.com.carrinho.dao.ProdutoDAO;
import br.com.carrinho.model.Produto;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class MainActivity extends Activity {
	
	ProdutoDAO produtoDAO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	private String getImei(){
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
	
	public void processSync(View v){

		 produtoDAO = new ProdutoDAO(this);
		 AsyncHttpClient client = new AsyncHttpClient();
		 String imei = getImei();
		 
		 
		 String urlCall = "http://10.0.2.2:8080/CarrinhoSync/rest/sync/"+imei;		 
	        client.get(urlCall, new AsyncHttpResponseHandler() {
	        	
	        	@SuppressWarnings("deprecation")
				@Override
	        	public void onFailure(Throwable error) {
	        		if(error != null){
	        			System.out.println(error);
	        		}
	        		super.onFailure(error);
	        	}
	            @Override
	            public void onSuccess(String response) {
	            	
	            List<Produto> produtos = null;
	              try {
	            	  produtos = parseJsonToListProduto(response);
	            	  for(Produto p : produtos){
	            		  if(produtoDAO.get(p.getCodigoBarras()) == null){
		            		  if(produtoDAO.insert(p)){
		            			  Log.i(MainActivity.class.getName(), "Produto com codigo "+p.getCodigoBarras() + " inserido com sucesso!");
		            		  }
	            		  }else{
	            			  produtoDAO.update(p);
	            		  }
	            	  }
	            	  
				} catch (Exception e) {
					e.printStackTrace();
				}
	            }
	            private List<Produto> parseJsonToListProduto(String string) {
	            	JSONObject obj = null;
	            	List<Produto> produtos = new ArrayList<Produto>();
	            	try {
	        			obj = new JSONObject(string);
	        			
	        			JSONArray discountListArray = obj.getJSONArray("listaProduto");
	        			for(int i=0;i<discountListArray.length();i++){
	        				String codigoBarras = discountListArray.getJSONObject(i).getString("codigoBarras");
	        				String nome = discountListArray.getJSONObject(i).getString("nome");
	        				String preco = discountListArray.getJSONObject(i).getString("preco");
	        				String urlImagem = discountListArray.getJSONObject(i).getString("urlImagem");
	        				Integer parceiroId = discountListArray.getJSONObject(i).getInt("parceiroId");
	        				
	        				Produto p = new Produto();
	        				p.setCodigoBarras(codigoBarras);
	        				p.setNomeProduto(nome);
	        				p.setParceiroId(parceiroId);
	        				p.setUrlImage(urlImagem);
	        				p.setPreco(preco);
	        				
	        				produtos.add(p);
	        			}
	        			
	        		} catch (JSONException e) {
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        		}
	            	
	            	return produtos;
	        	}
				
	        });
	}
	
}
