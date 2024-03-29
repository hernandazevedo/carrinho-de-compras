package br.com.carrinho.carrinhocomprasapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;
import br.com.carrinho.dao.ParceiroDAO;
import br.com.carrinho.dao.ProdutoDAO;
import br.com.carrinho.model.Parceiro;
import br.com.carrinho.model.Produto;

public class SyncService extends IntentService {

	//Teste com ip externo usando 3g
//	private static String hostPort = "hernand.no-ip.org";

	//Teste usando localhost
	private static String hostPort = "10.0.2.2:8080";
	
	public SyncService() {
		super("Sync Service");
	}

	private static ProdutoDAO produtoDAO;
	ParceiroDAO parceiroDAO;

	// HTTP GET request
		public static String sendGet(String url) throws Exception {
	 
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	 
			// optional default is GET
			con.setRequestMethod("GET");
	 
			
			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
	 
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
	 
			//print result
			return response.toString();
	 
		}
	
		public static void onSuccess(String response) {

			List<Produto> produtos = null;
			try {
				produtos = parseJsonToListProduto(response);
				for (Produto p : produtos) {
					if (produtoDAO.get(p.getCodigoBarras()) == null) {
						if (produtoDAO.insert(p)) {
							Log.i(MainActivity.class.getName(),
									"Produto com codigo "
											+ p.getCodigoBarras()
											+ " inserido com sucesso!");
						}
					} else {
						produtoDAO.update(p);
					}
				}

			} catch (Exception e) {
				Log.e(MainActivity.class.getName(),
						"Erro ao salvar o produto: " + e.getMessage());
			}
		}

		
	public void doSync(String imei, Context context) {
		String freq = PreferenceManager.getDefaultSharedPreferences(context).getString("sync_frequency", null);
		
		if(freq == null || freq.equals("-1")) return;
		
		produtoDAO = new ProdutoDAO(context);
		hostPort = PreferenceManager.getDefaultSharedPreferences(context).getString("hostport", null); 

		String listaParceiros = getParceirosIdAtivosPorVirgula(context); // TODO construir uma lista com os parceiros separados por virgula. Enviar -99 caso nao haja nenhum
		
		String urlCall = "http://"+hostPort+"/CarrinhoSync/rest/sync/" + imei +"/"+listaParceiros;
		
		try {
			String response = sendGet(urlCall);
			onSuccess(response);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

	private String getParceirosIdAtivosPorVirgula(Context context) {
		parceiroDAO = new ParceiroDAO(context);
		
		StringBuilder parceirosAtivos = new StringBuilder();
		
		List<Parceiro> listParceiros = parceiroDAO.getAll();
		
		for(Parceiro p :listParceiros){
			if(parceirosAtivos.length() == 0){
				parceirosAtivos.append(p.getParceiroId());
			}
			else{
				parceirosAtivos.append(",");
				parceirosAtivos.append(p.getParceiroId());
			}
		}
		

		if(parceirosAtivos.length() == 0) parceirosAtivos.append("-99");
		return parceirosAtivos.toString();
	}
//
//	// HTTP POST request
//		private void sendPost() throws Exception {
//	 
//			String url = "https://selfsolve.apple.com/wcResults.do";
//			URL obj = new URL(url);
//			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
//	 
//			//add reuqest header
//			con.setRequestMethod("POST");
////			con.setRequestProperty("User-Agent", USER_AGENT);
//			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
//	 
//			String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
//	 
//			// Send post request
//			con.setDoOutput(true);
//			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
//			wr.writeBytes(urlParameters);
//			wr.flush();
//			wr.close();
//	 
//			int responseCode = con.getResponseCode();
//			System.out.println("\nSending 'POST' request to URL : " + url);
//			System.out.println("Post parameters : " + urlParameters);
//			System.out.println("Response Code : " + responseCode);
//	 
//			BufferedReader in = new BufferedReader(
//			        new InputStreamReader(con.getInputStream()));
//			String inputLine;
//			StringBuffer response = new StringBuffer();
//	 
//			while ((inputLine = in.readLine()) != null) {
//				response.append(inputLine);
//			}
//			in.close();
//	 
//			//print result
//			System.out.println(response.toString());
//	 
//		}
//	
	public static List<Produto> parseJsonToListProduto(String string) {
		JSONObject obj = null;
		List<Produto> produtos = new ArrayList<Produto>();
		try {
			obj = new JSONObject(string);

			JSONArray discountListArray = obj
					.getJSONArray("listaProduto");
			for (int i = 0; i < discountListArray.length(); i++) {
				String codigoBarras = discountListArray
						.getJSONObject(i).getString("codigoBarras");
				String nome = discountListArray.getJSONObject(i)
						.getString("nome");
				String preco = discountListArray.getJSONObject(i)
						.getString("preco");
				String urlImagem = discountListArray.getJSONObject(i)
						.getString("urlImagem");
				Integer parceiroId = discountListArray.getJSONObject(i)
						.getInt("parceiroId");

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
	
	public static List<Parceiro> parseJsonToListParceiro(String string) {
		JSONObject obj = null;
		List<Parceiro> parceiros = new ArrayList<Parceiro>();
		try {
			obj = new JSONObject(string);

			JSONArray discountListArray = obj
					.getJSONArray("listaParceiro");
			for (int i = 0; i < discountListArray.length(); i++) {
				Integer id =  discountListArray
						.getJSONObject(i).getInt("parceiroId");
				String nome = discountListArray.getJSONObject(i)
						.getString("nome");
				String receiverPaypal = discountListArray.getJSONObject(i)
						.getString("receiverPaypal");
			

				Parceiro p = new Parceiro();
				p.setParceiroId(id);
				p.setNome(nome);
				p.setReceiverPaypal(receiverPaypal);
				
				

				parceiros.add(p);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return parceiros;
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		Log.i(Constants.LOG_TAG,
				"Sync Service invoked. Realizando o sincronismo de produtos");
		CarrinhoApp app = (CarrinhoApp) getApplication();
		if (app.connectionPresent()) {
			doSync(app.getImei(),this);
		} else {
			Log.w(Constants.LOG_TAG,
					"Sem conexão disponivel, não vai sincronizar os produtos");
		}
	}

}
