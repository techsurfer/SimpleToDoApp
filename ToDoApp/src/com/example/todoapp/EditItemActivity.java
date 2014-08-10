package com.example.todoapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

public class EditItemActivity extends ActionBarActivity {
	private ArrayList<String> todoItems;
	private EditText edItemText; 
	private Integer pos;
	private String editText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		edItemText = (EditText) findViewById(R.id.edItemText);
		readItems();
		editText = getIntent().getStringExtra(TodoActivity.TodoActivity_Item_DESC);
		pos = getIntent().getExtras().getInt(TodoActivity.TodoActivity_Item_POS);
		System.out.println("**** editText  : " + editText+" pos  : " + pos);		
		edItemText.setText(editText);
	}
	private void readItems(){
		File todoFile = new File(getFilesDir() ,   "todo.txt");
		try{			
			todoItems = new ArrayList<String>(FileUtils.readLines(todoFile));
		}catch(IOException ex ){
			todoItems = new ArrayList();
			ex.printStackTrace();
		}
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
		todoItems.set(pos, itemText);
		saveItems();
		Intent i = new Intent(EditItemActivity.this, TodoActivity.class);
		startActivity(i); 
	}
	private void saveItems(){
		File file = new File(getFilesDir(), "todo.txt");
		try{
			FileUtils.writeLines(file, todoItems);
			readItems();
		}catch(IOException ex ){
			ex.printStackTrace();
		}
	}
}
