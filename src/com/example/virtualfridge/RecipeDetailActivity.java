package com.example.virtualfridge;


import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.EditText;

public class RecipeDetailActivity extends Activity {
	
	private ProgressDialog pDialog;
	 
    // URL to get contacts JSON
    private static String url = "http://api.yummly.com/v1/api/recipe";
    
    // Authentication
    private static String APP_KEY = "YOUR_APP_KEY_HERE";
    private static String APP_ID = "YOUR_APP_ID_HERE";
    private String params;
 
    // JSON Node names
    private static final String TAG_NAME = "name";
    private static final String TAG_RATING = "rating";
    private static final String TAG_INGREDIENT_LINES = "ingredientLines";
    private static final String TAG_YIELD = "yield";
    private static final String TAG_TIME = "totalTime";
//    private static final String TAG_ADDRESS = "address";
//    private static final String TAG_GENDER = "gender";
//    private static (EditText) findViewById(R.id.editText1);final String TAG_PHONE = "phone";
//    private static final String TAG_PHONE_MOBILE = "mobile";
//    private static final String TAG_PHONE_HOME = "home";
//    private static final String TAG_PHONE_OFFICE = "office";
    
    
    EditText edName;
	EditText edRating;
	EditText edIngr;
	EditText edYield;
	EditText edTime;
	
    String name;
    String ingr;
    String yield;
    String time;
    String rating;

    
 // contacts JSONArray
    //JSONArray recipes = null;
 
    /*// Hashmap for ListView
    ArrayList<HashMap<String, String>> recipeList;*/

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        
        Bundle b = getIntent().getExtras();
        String id = b.getString("id");
        
        edName = (EditText) findViewById(R.id.edName);
    	edRating = (EditText) findViewById(R.id.edRating);
    	edIngr = (EditText) findViewById(R.id.edIngr);
    	edYield = (EditText) findViewById(R.id.edYield);
    	edTime = (EditText) findViewById(R.id.edTime);
        
        //ingredients = strJoin(resultArr, sSep);
        
        url += "/"+id;
        
        params = "_app_id="+APP_ID+"&_app_key="+APP_KEY;
 
        //recipeList = new ArrayList<HashMap<String, String>>();
 
        //ListView lv = getListView();
 
        // Listview on item click listener
 
        // Calling async task to get json
        new GetRecipe().execute();
    }
	
	public static String strJoin(String[] aArr, String sSep) {
	    StringBuilder sbStr = new StringBuilder();
	    for (int i = 0, il = aArr.length; i < il; i++) {
	        if (i > 0)
	            sbStr.append(sSep);
	        sbStr.append(aArr[i]);
	    }
	    return sbStr.toString();
	}
 
    /**
     * Async task class to get json by making HTTP call
     * */
    private class GetRecipe extends AsyncTask<Void, Void, Void> {
 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(RecipeDetailActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
 
        }
 
        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
 
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET,params);
 
            Log.d("Response: ", "> " + jsonStr);
 
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                     
                    // Getting JSON Array node
                    name = jsonObj.getString(TAG_NAME);
                    ingr = jsonObj.getString(TAG_INGREDIENT_LINES);
                    yield = jsonObj.getString(TAG_YIELD);
                    time = jsonObj.getString(TAG_TIME);
                    rating = jsonObj.getString(TAG_RATING);
 
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into Activity
             * */
            
            edName.setText(name);
        	edRating.setText(rating);
        	edIngr.setText(ingr);
        	edYield.setText(yield);
        	edTime.setText(time);
            
 
            
        }
 
    }
 
}
