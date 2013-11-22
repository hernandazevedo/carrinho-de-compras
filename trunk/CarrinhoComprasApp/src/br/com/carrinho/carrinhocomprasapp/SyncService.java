package br.com.carrinho.carrinhocomprasapp;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import br.com.carrinho.dao.ProdutoDAO;
import br.com.carrinho.model.Produto;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class SyncService extends IntentService {

	public SyncService() {
		super("Sync Service");
	}

	ProdutoDAO produtoDAO;

	public void doSync(String imei, Context context) {
		produtoDAO = new ProdutoDAO(context);
		AsyncHttpClient client = new AsyncHttpClient();

		String urlCall = "http://10.0.2.2:8080/CarrinhoSync/rest/sync/" + imei;
		client.get(urlCall, new AsyncHttpResponseHandler() {

			@SuppressWarnings("deprecation")
			@Override
			public void onFailure(Throwable error) {
				if (error != null) {
					System.out.println(error);
				}
				super.onFailure(error);
			}

			@Override
			public void onSuccess(String response) {

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

			private List<Produto> parseJsonToListProduto(String string) {
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

		});
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
