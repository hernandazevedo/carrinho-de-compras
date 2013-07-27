package br.com.carrinho.dao;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import br.com.carrinho.dbhelper.DbOpenHelper;
import br.com.carrinho.model.Produto;

public class ProdutoDAO implements IGenericDAO<Produto>{
	private DbOpenHelper dbHelper;
	
	public ProdutoDAO(Context context) {
		this.dbHelper = new DbOpenHelper(context);		 
	}
	
	
	public boolean insert(Produto p){
		ContentValues valores = new ContentValues();
        valores.put("codigo_barras", p.getCodigoBarras());
        valores.put("nome_produto", p.getNomeProduto());
        valores.put("url_image", p.getUrlImage());
        valores.put("preco", p.getPreco());
        valores.put("parceiro_id", p.getParceiroId());
        
        
        long id = dbHelper.getWritableDatabase().insert(DbOpenHelper.TABLE_PRODUTO, null, valores);
        
        return (id != -1 ? true : false);
		
	}


	@Override
	public int update(Produto obj) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public Produto get(Object id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Produto> getAll() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void delete(Produto obj) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}
}
