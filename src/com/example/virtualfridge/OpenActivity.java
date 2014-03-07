package com.example.virtualfridge;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.view.View.OnClickListener;

public class OpenActivity extends Activity implements OnClickListener{
	
	Button button;
    ListView listView;
    ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_open);
		findViewsById();
		//String[] values = new String[] {"Tomato", "Cabbage", "Ginger", "Garlic"};
		//String[] values = new String[]{};
		ArrayList<String> values = new ArrayList<String>();
		Log.i("Enter","Array issue before");
		try {
		Bundle b = getIntent().getExtras();
        values = b.getStringArrayList("selectedItems");
		}
		catch(Exception e){
			Log.i("Exception","Array issue");
		}
		
		adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, values);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		listView.setAdapter(adapter);
		
		button.setOnClickListener(this);
		
	}
	
	private void findViewsById() {
        listView = (ListView) findViewById(R.id.list);
        button = (Button) findViewById(R.id.testbutton);
    }
	
	public void onClick(View v) {
        SparseBooleanArray checked = listView.getCheckedItemPositions();
        ArrayList<String> selectedItems = new ArrayList<String>();
        for (int i = 0; i < checked.size(); i++) {
            // Item position in adapter
            int position = checked.keyAt(i);
            // Add sport if it is checked i.e.) == TRUE!
            if (checked.valueAt(i))
                selectedItems.add(adapter.getItem(position));
        }
        
        String[] outputStrArr = new String[selectedItems.size()];
        
        for (int i = 0; i < selectedItems.size(); i++) {
            outputStrArr[i] = selectedItems.get(i);
        }
 
        Intent intent = new Intent(getApplicationContext(),
                RecipeListActivity.class);
 
        // Create a bundle object
        Bundle b = new Bundle();
        b.putStringArray("selectedItems", outputStrArr);
 
        // Add the bundle to the intent.
        intent.putExtras(b);
 
        // start the ResultActivity
        startActivity(intent);
    }

	
}