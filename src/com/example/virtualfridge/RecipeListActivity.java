package com.example.virtualfridge;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class RecipeListActivity extends ListActivity {
	
	private ProgressDialog pDialog;
	 
    // URL to get contacts JSON
    private static String url = "http://api.yummly.com/v1/api/recipes";
    
    // Authentication
    private static String APP_KEY = "YOUR_APP_KEY_HERE";
    private static String APP_ID = "YOUR_APP_ID_HERE";
    private String ingredients;
    private String sSep = "+";
    private String params;
    String id;
    String name;
    String ingr;
 
    private static final String TAG_ID = "id";
    private static final String TAG_INGREDIENTS = "ingredients";
    private static final String TAG_RECIPE_NAME = "recipeName";
    private static final String TAG_MATCHES = "matches";
//    private static final String TAG_ADDRESS = "address";
//    private static final String TAG_GENDER = "gender";
//    private static final String TAG_PHONE = "phone";
//    private static final String TAG_PHONE_MOBILE = "mobile";
//    private static final String TAG_PHONE_HOME = "home";
//    private static final String TAG_PHONE_OFFICE = "office";
    
 // contacts JSONArray
    JSONArray recipes = null;
 
    // Hashmap for ListView
    ArrayList<HashMap<String, String>> recipeList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        
        Bundle b = getIntent().getExtras();
        String[] resultArr = b.getStringArray("selectedItems");
        
        ingredients = strJoin(resultArr, sSep);
        
        params = "_app_id="+APP_ID+"&_app_key="+APP_KEY+"&q="+ingredients;
 
        recipeList = new ArrayList<HashMap<String, String>>();
 
        ListView lv = getListView();
 
        // Listview on item click listener
        lv.setOnItemClickListener(new OnItemClickListener() {
 
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                // getting values from selected ListItem
                String temp_id = ((TextView) view.findViewById(R.id.mobile))
                        .getText().toString();
               /* String cost = ((TextView) view.findViewById(R.id.email))
                        .getText().toString();
                String description = ((TextView) view.findViewById(R.id.mobile))
                        .getText().toString();
 */
                // Starting single contact activity
                Intent in = new Intent(getApplicationContext(),
                        RecipeDetailActivity.class);
                in.putExtra(TAG_ID, temp_id);
                startActivity(in);
 
            }
        });
 
        // Calling async task to get json
        new GetRecipes().execute();
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
    private class GetRecipes extends AsyncTask<Void, Void, Void> {
 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(RecipeListActivity.this);
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
                    recipes = jsonObj.getJSONArray(TAG_MATCHES);
 
                    // looping through All Contacts
                    for (int i = 0; i < recipes.length(); i++) {
                        JSONObject c = recipes.getJSONObject(i);
                        
                        id = c.getString(TAG_ID);
                        name = c.getString(TAG_RECIPE_NAME);
                        ingr = c.getString(TAG_INGREDIENTS);
                        
                        // tmp hashmap for single contact
                        HashMap<String, String> recipe = new HashMap<String, String>();
 
                        // adding each child node to HashMap key => value
                        recipe.put(TAG_ID, id);
                        recipe.put(TAG_RECIPE_NAME, name);
                        recipe.put(TAG_INGREDIENTS, ingr);
 
                        // adding contact to contact list
                        recipeList.add(recipe);
                    }
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
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    RecipeListActivity.this, recipeList,
                    R.layout.list_item, new String[] { TAG_RECIPE_NAME, TAG_INGREDIENTS,
                            TAG_ID }, new int[] { R.id.name,
                            R.id.email, R.id.mobile });
 
            setListAdapter(adapter);
        }
 
    }
 
}
