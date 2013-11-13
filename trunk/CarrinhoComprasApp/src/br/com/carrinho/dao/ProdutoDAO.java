package br.com.carrinho.dao;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import br.com.carrinho.dbhelper.DbOpenHelper;
import br.com.carrinho.model.Produto;

public class ProdutoDAO implements IGenericDAO<Produto>{
	private DbOpenHelper dbHelper;
	
	public ProdutoDAO(Context context) {
		this.dbHelper = new DbOpenHelper(context);		 
	}
	
	
	public boolean insert(Produto p){
		ContentValues valores = getContentValues(p);
        
        
        long id = dbHelper.getWritableDatabase().insert(DbOpenHelper.TABLE_PRODUTO, null, valores);
        
        return (id != -1 ? true : false);
		
	}


	private ContentValues getContentValues(Produto p){
		ContentValues valores = new ContentValues();
        valores.put("codigo_barras", p.getCodigoBarras());
        valores.put("nome_produto", p.getNomeProduto());
        valores.put("url_image", p.getUrlImage());
        valores.put("preco", p.getPreco());
        valores.put("parceiro_id", p.getParceiroId());
        
        return valores;
		
	}
	
	@Override
	public int update(Produto obj) {

		ContentValues valores = getContentValues(obj);
		
		return dbHelper.getWritableDatabase().update(DbOpenHelper.TABLE_PRODUTO, valores, "codigo_barras "+"="+obj.getCodigoBarras(), null);
	}


	@Override
	public Produto get(Object codigo_barras) {
		
		Cursor cursor = dbHelper.getReadableDatabase().
						rawQuery("select * from produto where codigo_barras = ?", new String[] { (String)codigo_barras }); 
		
		
		if(cursor.moveToFirst()){
			return cursorToProduto(cursor);			
		}
			
		return null;
	}


	private Produto cursorToProduto(Cursor cursor) {
		Produto p = new Produto();
//		codigo_barras INTEGER PRIMARY KEY," +
//        " nome_produto TEXT, url_image TEXT, preco TEXT," +
//        " parceiro_id INTEGER)
		p.setCodigoBarras(cursor.getString(0));
		p.setNomeProduto(cursor.getString(1));
		p.setUrlImage(cursor.getString(2));
		p.setPreco(cursor.getString(3));
		p.setParceiroId(cursor.getInt(4));
		return p;
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
