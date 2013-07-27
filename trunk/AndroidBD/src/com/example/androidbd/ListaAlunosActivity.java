package com.example.androidbd;

import java.io.ByteArrayInputStream;

import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ListaAlunosActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		SegundoExemploOpenHelper dbHelper = SegundoExemploOpenHelper.getInstance(this);
		
		Cursor c = dbHelper.obterTodosAlunos();
		AlunoCursorAdapter alunoAdapter = new AlunoCursorAdapter(this, c, 
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		
		setListAdapter(alunoAdapter);
	}

	class AlunoCursorAdapter extends CursorAdapter {

		public AlunoCursorAdapter(Context context, Cursor c, int flags) {
			super(context, c, flags);
		}

		@Override
		public void bindView(View view, Context arg1, Cursor c) {
			
			TextView tv1 = (TextView)view.findViewById(R.id.textView1);
			TextView tv2 = (TextView)view.findViewById(R.id.textView2);
			ImageView imgV =(ImageView)view.findViewById(R.id.imageView1); 
			 
			tv1.setText(c.getString(1));
			tv2.setText(c.getString(2));
			
			byte[] bytes = c.getBlob(3);
			ByteArrayInputStream imageStream = new ByteArrayInputStream(bytes);
			Bitmap theImage= BitmapFactory.decodeStream(imageStream);
			imgV.setImageBitmap(theImage);
			
		}

		@Override
		public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {

			return getLayoutInflater().inflate(R.layout.aluno_celula, arg2, false);
		}
		
	}
}
