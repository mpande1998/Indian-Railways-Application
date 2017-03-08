package com.example.manaspande.indianrailways.Adapters;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by manaspande on 2017-01-23.
 */

public class TrainNumberAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {

    ArrayList<String> trains;

    public TrainNumberAutoCompleteAdapter(Context context, int textViewResourceId){
        super(context, textViewResourceId);
        trains = new ArrayList<>();
    }

    @Override
    public int getCount(){
        return trains.size();
    }

    @Override
    public String getItem(int index){
        return trains.get(index);
    }


    @Override
    public Filter getFilter(){

        Filter myFilter = new Filter(){

            @Override
            protected FilterResults performFiltering(CharSequence constraint){
                FilterResults filterResults = new Filter.FilterResults();
                if(constraint != null) {
                    // A class that queries a web API, parses the data and returns an ArrayList<Style>
//
                    try {
                        trains = new DownloadTrains().execute(new String[]{constraint.toString()}).get();
                    }
                    catch(Exception e) {
//                        Log.e("myException", e.getMessage());
                    }
                    // Now assign the values and count to the FilterResults object
                    filterResults.values = trains;
                    filterResults.count = trains.size();
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if(results != null && results.count > 0) {
                    notifyDataSetChanged();
                }
                else {
                    notifyDataSetInvalidated();
                }
            }
        };

        return myFilter;
    }

    private class DownloadTrains extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... constraint) {
            String query = constraint[0];
            String BASE_QUERY_URL = "http://api.railwayapi.com";
            String FUNCTION_NAME = "suggest_train";
            String QUERY_PARAM = "trains";
            String API_KEY_PARAM = "apikey";
            String API_KEY = "xcbbznho";

            Uri builtUri = Uri.parse(BASE_QUERY_URL).buildUpon()
                    .appendPath(FUNCTION_NAME)
                    .appendPath(QUERY_PARAM)
                    .appendPath(query)
                    .appendPath(API_KEY_PARAM)
                    .appendPath(API_KEY)
                    .build();

            URL url = null;
            try {
                url = new URL(builtUri.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = null;
            String JSONResponse = null;
            try {
                response = client.newCall(request).execute();
                JSONResponse = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ArrayList<String> trainInfo = new ArrayList<>();

            try {
                JSONObject jsonObject = new JSONObject(JSONResponse);
                JSONArray jsonArray = jsonObject.getJSONArray("trains");

                for (int i=0; i<jsonArray.length(); i++) {
                    String number = jsonArray.getJSONObject(i).getString("number");
                    String name = jsonArray.getJSONObject(i).getString("name");
                    trainInfo.add(number + " " + name);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return trainInfo;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {

        }

    }

}
