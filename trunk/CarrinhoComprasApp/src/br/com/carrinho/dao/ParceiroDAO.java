package br.com.carrinho.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import br.com.carrinho.dbhelper.DbOpenHelper;
import br.com.carrinho.model.Parceiro;

public class ParceiroDAO implements IGenericDAO<Parceiro>{
	private DbOpenHelper dbHelper;
	
	public ParceiroDAO(Context context) {
		this.dbHelper = new DbOpenHelper(context);		 
	}
	
	
	public boolean insert(Parceiro p){
		ContentValues valores = getContentValues(p);
        
        
        long id = dbHelper.getWritableDatabase().insert(DbOpenHelper.TABLE_PARCEIRO, null, valores);
        
        return (id != -1 ? true : false);
		
	}


	private ContentValues getContentValues(Parceiro p){
		ContentValues valores = new ContentValues();
        valores.put("parceiro_id", p.getParceiroId());
        valores.put("nome", p.getNome());
        valores.put("receiver_paypal", p.getReceiverPaypal());
        
        return valores;
		
	}
	
	@Override
	public int update(Parceiro obj) {

		ContentValues valores = getContentValues(obj);
		
		return dbHelper.getWritableDatabase().update(DbOpenHelper.TABLE_PARCEIRO, valores, "parceiro_id "+"="+obj.getParceiroId(), null);
	}


	@Override
	public Parceiro get(Object parceiroId) {
		if(parceiroId != null){
			Cursor cursor = dbHelper.getReadableDatabase().
							rawQuery("select * from Parceiro where parceiro_id = ?", new String[] { parceiroId.toString() }); 
			
			
			if(cursor.moveToFirst()){
				return cursorToParceiro(cursor);			
			}
		}
		return null;
	}


	private Parceiro cursorToParceiro(Cursor cursor) {
		Parceiro p = new Parceiro();
//		codigo_barras INTEGER PRIMARY KEY," +
//        " nome_Parceiro TEXT, url_image TEXT, preco TEXT," +
//        " parceiro_id INTEGER)
//		p.setCodigoBarras(cursor.getString(0));
//		p.setNomeParceiro(cursor.getString(1));
//		p.setUrlImage(cursor.getString(2));
//		p.setPreco(cursor.getString(3));
		p.setParceiroId(cursor.getInt(1));
		p.setNome(cursor.getString(2));
		p.setReceiverPaypal(cursor.getString(3));
		return p;
	}


	@Override
	public List<Parceiro> getAll() {

		List<Parceiro> parceiros = new ArrayList<Parceiro>();
		
		Cursor cursor = dbHelper.getReadableDatabase().
				rawQuery("select * from Parceiro", new String[] {}); 


		while(cursor.moveToNext()){
			parceiros.add(cursorToParceiro(cursor));
		}
	
		
		return parceiros;
	}


	@Override
	public void delete(Parceiro obj) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}
}
