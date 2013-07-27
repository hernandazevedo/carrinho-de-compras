package com.example.androidbd;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class SegundaMainActivity extends Activity {

	EditText editNome;
	EditText editEnd;
	ImageView imageView;
	Spinner spinner;
	
	SegundoExemploOpenHelper dbHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.segunda_activity_main);
		
		editNome = (EditText) findViewById(R.id.editText1);
		editEnd = (EditText) findViewById(R.id.editText2);
		imageView = (ImageView) findViewById(R.id.imageView1);
		spinner = (Spinner) findViewById(R.id.spinner1);
		
		dbHelper = SegundoExemploOpenHelper.getInstance(this);
		
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, 
	            android.R.layout.simple_list_item_2, 
	            dbHelper.obterTodasTurmas(), 
	            new String[] { "nome", "_id" }, 
	            new int[] { android.R.id.text1, android.R.id.text2 },
	            CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		
		spinner.setAdapter(adapter);
	}
	
	public void gravaBanco(View v) {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
		 Bitmap bitmap =  ((BitmapDrawable)getResources()
				.getDrawable(R.drawable.img_hering)).getBitmap(); 
		 bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		 byte[] photo = baos.toByteArray();  

		
		boolean salvo = dbHelper.inserirAluno(
				editNome.getText().toString(), 
				editEnd.getText().toString(),
				photo, 1);
		
		String msg = (salvo) ? "Dados salvos" : "Erro ao gravar no banco";
		Toast.makeText(this, msg, 3000).show();
	}
	
	
	public void listaAlunos(View v) {
		
		Intent i = new Intent(this, ListaAlunosActivity.class);
		startActivity(i);
	}
	
}
