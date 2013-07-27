package br.com.carrinho.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbOpenHelper extends SQLiteOpenHelper {
	
	private static final String NOME_BANCO = "carrinhoapp.db";
    private static final int VERSAO_SCHEMA = 2;
    public static final String TABLE_PRODUTO = "produto";
    public static final String TABLE_PARCEIRO = "parceiro";
    
    public DbOpenHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO_SCHEMA);
    }
    

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		
		 db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_PRODUTO + 
			   " (codigo_barras INTEGER PRIMARY KEY," +
	           " nome_produto TEXT, url_image TEXT, preco TEXT," +
	           " parceiro_id INTEGER);");
		 
		 db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_PARCEIRO + 
				   " (parceiro_id INTEGER PRIMARY KEY," +
		           " nome TEXT, receiver_paypal TEXT);");
			
		 
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DbOpenHelper.class.getName(),
		        "Atualizando database de versao " + oldVersion + " para "
		         + newVersion + ", todos os dados serao removidos");
		// Atualizando base de dados para uma versao mais
		// nova, todos os dados serao removidos
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUTO);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARCEIRO);
		onCreate(db);
	}
	
}
