package com.example.todoapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


public class TodoActivity extends ActionBarActivity {
	public static final String TodoActivity_Item_POS = "TodoActivity_Item_POS";
	public static final String TodoActivity_Item_DESC = "TodoActivity_Item_DESC";
	private ArrayList<String> todoItems;
	private ArrayAdapter<String> itemsAdapter;
	private ListView lvItems;
	private EditText etNewItem; 
	// REQUEST_CODE can be any value we like, used to determine the result type later
	private final int REQUEST_CODE = 20;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
		etNewItem = (EditText) findViewById(R.id.etNewItem);
		lvItems = (ListView) findViewById(R.id.lvItems);
		readItems();
		itemsAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1, todoItems);
		lvItems.setAdapter(itemsAdapter);
		setupListViewListener();

    }
	private void setupListViewListener() {
		lvItems.setOnItemLongClickListener( new OnItemLongClickListener(){

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				todoItems.remove(position);
				itemsAdapter.notifyDataSetChanged();
				saveItems();
				return true;
			}
			
		});
		lvItems.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(TodoActivity.this, EditItemActivity.class); 
	              i.putExtra(TodoActivity_Item_POS, position);
	              i.putExtra(TodoActivity_Item_DESC, todoItems.get(position));
	              startActivityForResult(i, REQUEST_CODE);
				return;
			}
			
		});
		
	}
//	private void populateArrayItems(){
//		todoItems = new ArrayList<String>();
//		todoItems.add("Items 1");
//		todoItems.add("Items 2");
//		todoItems.add("Items 3");
//	}

	public void onAddedItem(View v){		
		String itemText = 	etNewItem.getText().toString();
		//System.out.println("**** onAddedItem New Item  : " + itemText);
		itemsAdapter.add(itemText);
		etNewItem.setText("");
		saveItems();
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
	private void saveItems(){
		File file = new File(getFilesDir(), "todo.txt");
		try{
			FileUtils.writeLines(file, todoItems);
		}catch(IOException ex ){
			ex.printStackTrace();
		}
	}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.todo, menu);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      // REQUEST_CODE is defined above
      if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
         // Extract TodoActivity_Item_DESC & TodoActivity_Item_POS value from result extras
         String itemText = data.getExtras().getString(TodoActivity_Item_DESC);
         int pos = data.getExtras().getInt(TodoActivity_Item_POS);
         todoItems.set(pos, itemText);
         saveItems();
         itemsAdapter.notifyDataSetChanged();
         }else{
        	 System.out.println("**** resultCode is not ok --> " +resultCode);
         }
    } 

}
