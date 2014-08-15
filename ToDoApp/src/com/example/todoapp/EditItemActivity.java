package com.example.todoapp;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends ActionBarActivity {
	private EditText edItemText; 
	private Integer pos;
	private String editText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		edItemText = (EditText) findViewById(R.id.edItemText);
		editText = getIntent().getStringExtra(TodoActivity.TodoActivity_Item_DESC);
		pos = getIntent().getExtras().getInt(TodoActivity.TodoActivity_Item_POS);
		System.out.println("**** editText  : " + editText+" pos  : " + pos);		
		edItemText.setText(editText);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	public void saveItem(View v){
		String itemText = 	edItemText.getText().toString();
		System.out.println("**** saveItem New Item --> " + itemText+" old item -->"+editText);
		// Prepare data intent 
		Intent data = new Intent();
		// Pass relevant data back as a result
		data.putExtra(TodoActivity.TodoActivity_Item_DESC, itemText);
		data.putExtra(TodoActivity.TodoActivity_Item_POS, pos);
		// Activity finished ok, return the data
		setResult(RESULT_OK, data); // set result code and bundle data for response
		finish();
	}	
}
