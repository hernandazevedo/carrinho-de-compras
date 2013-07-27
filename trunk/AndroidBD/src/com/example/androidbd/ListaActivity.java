package com.example.androidbd;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;

public class ListaActivity extends ListActivity {
	
	Cursor cursor;
	ExemploOpenHelper dbHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		dbHelper = new ExemploOpenHelper(this);
		
		if(getIntent() != null) {
			
			int tipo = getIntent().getIntExtra("tipo", 1);
			
			if(tipo == 1) 
				cursor = dbHelper.obterTodosRaw();
			else
				cursor = dbHelper.obterTodos();
		}
		
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, 
	            android.R.layout.simple_list_item_2, 
	            cursor, 
	            new String[] { "nome", "endereco" }, 
	            new int[] { android.R.id.text1, android.R.id.text2 },
	            CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		
		setListAdapter(adapter);
	}
	
	@Override
	protected void onStop() {

		dbHelper.close();
		super.onStop();
	}
	
}
