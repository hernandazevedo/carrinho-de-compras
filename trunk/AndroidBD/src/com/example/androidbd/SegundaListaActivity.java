package com.example.androidbd;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.ArrayAdapter;

public class SegundaListaActivity extends ListaActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle bundle = getIntent().getExtras();
		ArrayList<Contato> array = (ArrayList<Contato>) bundle.get("contatos");
		
		ArrayAdapter<Contato> adapter = new ArrayAdapter<Contato>(this,
				android.R.layout.simple_expandable_list_item_1,
				android.R.id.text1, array);
		setListAdapter(adapter);
	}

	
}
