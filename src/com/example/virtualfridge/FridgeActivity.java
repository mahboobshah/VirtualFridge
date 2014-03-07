package com.example.virtualfridge;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class FridgeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fridge, menu);
        return true;
    }
    
    public void openFridgeActivity(View v) {
		Intent prefIntent = new Intent(FridgeActivity.this,
				OpenActivity.class);
		// MainActivity.this.startActivity(prefIntent);
		startActivity(prefIntent);

	}
    
    public void loadFridgeActivity(View v) {
		Intent prefIntent = new Intent(FridgeActivity.this,
				LoadActivity.class);
		// MainActivity.this.startActivity(prefIntent);
		startActivity(prefIntent);

	}
    
}
