package com.example.androidbd;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	EditText editNome;
	EditText editEnd;
	EditText editTelefone;
	
	ExemploOpenHelper dbHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		editNome = (EditText) findViewById(R.id.editText1);
		editEnd = (EditText) findViewById(R.id.editText2);
		editTelefone = (EditText) findViewById(R.id.editText3);
		
		dbHelper = new ExemploOpenHelper(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void gravaBanco(View v) {
		
		boolean salvo = dbHelper.inserir(
				editNome.getText().toString(), 
				editEnd.getText().toString(),
				editTelefone.getText().toString());
		
		String msg = (salvo) ? "Dados salvos" : "Erro ao gravar no banco";
		Toast.makeText(this, msg, 3000).show();
	}
	
	public void queryRaw(View v) {
		
		Intent i = new Intent(this, ListaActivity.class);
		i.putExtra("tipo", 1);
		startActivity(i);
	}
	
	public void queryMetodo(View v) {
		Intent i = new Intent(this, ListaActivity.class);
		i.putExtra("tipo", 2);
		startActivity(i);
	}
	
	public void obtemObjeto(View v) {
		ArrayList<Contato> contatos = new ArrayList<Contato>();
		Cursor c = dbHelper.obterTodos();
		c.moveToFirst();
		while (!c.isAfterLast()) {
			Contato cont = new Contato();
			cont.setId(c.getInt(0));
			cont.setNome(c.getString(1));
			cont.setEndereco(c.getString(2));
			cont.setTelefone(c.getString(3));
			contatos.add(cont);
			c.moveToNext();
		}
		
		Intent i = new Intent(this, SegundaListaActivity.class);
		i.putExtra("contatos", contatos);
		startActivity(i);
	}
}
