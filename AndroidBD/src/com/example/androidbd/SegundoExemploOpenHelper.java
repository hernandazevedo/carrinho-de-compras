package com.example.androidbd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SegundoExemploOpenHelper extends SQLiteOpenHelper {
	
	private static final String NOME_BANCO = "exemplo.db";
    private static final int VERSAO_SCHEMA = 1;
    
    private static SegundoExemploOpenHelper instance = null;
    
    public static SegundoExemploOpenHelper getInstance(Context context){
    	
    	if(instance == null) {
    		instance = new SegundoExemploOpenHelper(context.getApplicationContext());
    	}
    	return instance;
    }
    
    
    public SegundoExemploOpenHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO_SCHEMA);
        
		 inserirTurma("13BBBXXXX");
		 inserirTurma("15ZZZZZEEEE");
    }
    

	@Override
	public void onCreate(SQLiteDatabase db) {
		 
		 db.execSQL( "CREATE TABLE turma (" +
         "_id              INTEGER PRIMARY KEY AUTOINCREMENT," +
         "nome        		TEXT);");
        
		 
		 db.execSQL("CREATE TABLE IF NOT EXISTS aluno" + 
				   " (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
		           " nome TEXT, endereco TEXT, foto BLOB," +
				   " turma_id INTEGER REFERENCES turma(_id) on UPDATE CASCADE);");
		 	 
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		// Atualizando base de dados para uma vers‹o mais
		// nova, todos os dados ser‹o removidos
		db.execSQL("DROP TABLE IF EXISTS turma");
		db.execSQL("DROP TABLE IF EXISTS aluno");
		onCreate(db);
	}
	
	
	public boolean inserirAluno(String nome, String endereco, byte[] foto, int turma_id) {
        
		ContentValues valores = new ContentValues();
        valores.put("nome", nome);
        valores.put("endereco", endereco);
        valores.put("foto", foto);
        valores.put("turma_id", turma_id);
        
        long id = getWritableDatabase().insert("aluno", null, valores);
        
        return (id != -1 ? true : false);
    }
	
	public boolean inserirTurma(String nome) {
        
		ContentValues valores = new ContentValues();
        valores.put("nome", nome);
        
        long id = getWritableDatabase()
        		.insert("turma", null, valores);
        
        return (id != -1 ? true : false);
    }
	
	public Cursor obterTodosAlunos() 
	{
		SQLiteDatabase base = getReadableDatabase();
        return base.rawQuery("select aluno._id, aluno.nome, aluno.endereco, " +
           "aluno.foto, turma.nome FROM " + "aluno, turma WHERE " +
           		"aluno.turma_id == turma._id ORDER BY aluno.nome", null);
    }
	
	public Cursor obterTodasTurmas() 
	{
		SQLiteDatabase base = getReadableDatabase();
        return base.rawQuery("select _id, nome " +
           " FROM " + "turma" + " ORDER BY nome", null);
    }
	
	

}
