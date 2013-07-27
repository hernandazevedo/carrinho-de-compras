package com.example.androidbd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ExemploOpenHelper extends SQLiteOpenHelper {
	
	private static final String NOME_BANCO = "exemplo.db";
    private static final int VERSAO_SCHEMA = 1;
    private static final String TABLE_NAME = "contato";
    
    public ExemploOpenHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO_SCHEMA);
    }
    

	@Override
	public void onCreate(SQLiteDatabase db) {
		 db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + 
			   " (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
	           " nome TEXT, endereco TEXT, telefone TEXT);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(ExemploOpenHelper.class.getName(),
		        "Atualizando database de vers‹o " + oldVersion + " para "
		         + newVersion + ", todos os dados ser‹o removidos");
		// Atualizando base de dados para uma vers‹o mais
		// nova, todos os dados ser‹o removidos
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
	
	
	public boolean inserir(String nome, String endereco, String telefone) {
        
		ContentValues valores = new ContentValues();
        valores.put("nome", nome);
        valores.put("endereco", endereco);
        valores.put("telefone", telefone);
        
        long id = getWritableDatabase().insert(TABLE_NAME, null, valores);
        
        return (id != -1 ? true : false);
    }
	
	public Cursor obterTodosRaw() 
	{
		SQLiteDatabase base = getReadableDatabase();
        return base.rawQuery("select _id, nome, endereco, " +
           "telefone FROM " + TABLE_NAME + " ORDER BY nome", null);
    }
	
	public Cursor obterTodos() 
	{
	  SQLiteDatabase base = getReadableDatabase();
      return base.query(TABLE_NAME, null, null, null, null, null, "nome");
    }

}
