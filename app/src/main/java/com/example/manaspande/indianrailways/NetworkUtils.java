package com.example.manaspande.indianrailways;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by manaspande on 2017-01-19.
 */

public class NetworkUtils extends MainActivity {

    private static String BASE_QUERY_URL = "http://api.railwayapi.com";
    private static String API_KEY_PARAM = "apikey";
    protected static String API_KEY = "q79d1hp8";

    public static URL urlBuilder(ArrayList<String> queryArrayList) {
        String functionName = queryArrayList.get(0);
        String queryParam = queryArrayList.get(1);
        String query = queryArrayList.get(2);

        URL url;
        Uri builtUri = Uri.parse(BASE_QUERY_URL).buildUpon()
                .appendPath(functionName)
                .appendPath(queryParam)
                .appendPath(query)
                .appendPath(API_KEY_PARAM)
                .appendPath(API_KEY)
                .build();

        try {
            url = new URL(builtUri.toString());
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static URL urlBuilder2(ArrayList<String> queryArrayList) {
        String functionName = queryArrayList.get(0);
        String queryParam1 = queryArrayList.get(1);
        String query1 = queryArrayList.get(2);
        String queryParam2 = queryArrayList.get(3);
        String query2 = queryArrayList.get(4);

        URL url;
        Uri builtUri = Uri.parse(BASE_QUERY_URL).buildUpon()
                .appendPath(functionName)
                .appendPath(queryParam1)
                .appendPath(query1)
                .appendPath(queryParam2)
                .appendPath(query2)
                .appendPath(API_KEY_PARAM)
                .appendPath(API_KEY)
                .build();

        try {
            url = new URL(builtUri.toString());
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static URL urlBuilder3(ArrayList<String> queryArrayList) {
        String functionName = queryArrayList.get(0);
        String queryParam1 = queryArrayList.get(1);
        String query1 = queryArrayList.get(2);
        String queryParam2 = queryArrayList.get(3);
        String query2 = queryArrayList.get(4);
        String queryParam3 = queryArrayList.get(5);
        String query3 = queryArrayList.get(6);
        String queryParam4 = queryArrayList.get(7);
        String query4 = queryArrayList.get(8);
        String queryParam5 = queryArrayList.get(9);
        String query5 = queryArrayList.get(10);
        String queryParam6 = queryArrayList.get(11);
        String query6 = queryArrayList.get(12);


        URL url;
        Uri builtUri = Uri.parse(BASE_QUERY_URL).buildUpon()
                .appendPath(functionName)
                .appendPath(queryParam1)
                .appendPath(query1)
                .appendPath(queryParam2)
                .appendPath(query2)
                .appendPath(queryParam3)
                .appendPath(query3)
                .appendPath(queryParam4)
                .appendPath(query4)
                .appendPath(queryParam5)
                .appendPath(query5)
                .appendPath(queryParam6)
                .appendPath(query6)
                .appendPath(API_KEY_PARAM)
                .appendPath(API_KEY)
                .build();

        try {
            url = new URL(builtUri.toString());
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getResponseFromHttpUrl(URL queryUrl) {
        OkHttpClient client;

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);
        client = builder.build();

        Request request = new Request.Builder()
                .url(queryUrl)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<ArrayList<String>> getResultsFromJSONTrainRoute(String resultJSON) throws JSONException {
        ArrayList<ArrayList<String>> mArrayList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(resultJSON);
        JSONArray trainRouteJSONArray = jsonObject.getJSONArray("route");

        for (int i=0; i<trainRouteJSONArray.length(); i++) {
            ArrayList<String> nArrayList = new ArrayList<>();

            if(i==0) {
                nArrayList.add(trainRouteJSONArray.getJSONObject(i).getString("fullname"));
                nArrayList.add(trainRouteJSONArray.getJSONObject(i).getString("scharr"));
                nArrayList.add(trainRouteJSONArray.getJSONObject(i).getString("schdep"));
                nArrayList.add("Source");
            } else if (i == trainRouteJSONArray.length()-1) {
                nArrayList.add(trainRouteJSONArray.getJSONObject(i).getString("fullname"));
                nArrayList.add(trainRouteJSONArray.getJSONObject(i).getString("scharr"));
                nArrayList.add(trainRouteJSONArray.getJSONObject(i).getString("schdep"));
                nArrayList.add("Destination");
            } else {
                nArrayList.add(trainRouteJSONArray.getJSONObject(i).getString("fullname"));
                nArrayList.add(trainRouteJSONArray.getJSONObject(i).getString("scharr"));
                nArrayList.add(trainRouteJSONArray.getJSONObject(i).getString("schdep"));
                nArrayList.add(trainRouteJSONArray.getJSONObject(i).getString("distance"));
            }

            mArrayList.add(i, nArrayList);
        }
        return mArrayList;
    }

    public static ArrayList<ArrayList<String>> getResultsFromJSONFareEnquiry(String resultJSON) throws JSONException {
        ArrayList<ArrayList<String>> mArrayList = new ArrayList<ArrayList<String>>();
        JSONObject jsonObject = new JSONObject(resultJSON);
        JSONArray jsonArray = jsonObject.getJSONArray("fare");

        for (int i=0; i<jsonArray.length(); i++) {
            ArrayList<String> nArrayList = new ArrayList<>();
            String code = jsonArray.getJSONObject(i).getString("code");
            String name = jsonArray.getJSONObject(i).getString("name");
            String fare = jsonArray.getJSONObject(i).getString("fare");
            nArrayList.add(code);
            nArrayList.add(name);
            nArrayList.add(fare);

            mArrayList.add(nArrayList);
        }

        return mArrayList;
    }

    public static ArrayList<ArrayList<String>> getResultsFromJSONTAAS(String resultJSON) throws JSONException {
        ArrayList<ArrayList<String>> mArrayList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(resultJSON);
        JSONArray TAASJSONArray = jsonObject.getJSONArray("train");

        for (int i=0; i<TAASJSONArray.length(); i++) {
            ArrayList<String> nArrayList = new ArrayList<>();
            String scharr = TAASJSONArray.getJSONObject(i).getString("scharr");
            String delayarr = TAASJSONArray.getJSONObject(i).getString("delayarr");
            String schdep = TAASJSONArray.getJSONObject(i).getString("schdep");
            String delaydep = TAASJSONArray.getJSONObject(i).getString("delaydep");
            String name = TAASJSONArray.getJSONObject(i).getString("name");
            String number = TAASJSONArray.getJSONObject(i).getString("number");

            nArrayList.add(scharr);
            nArrayList.add(delayarr);
            nArrayList.add(schdep);
            nArrayList.add(delaydep);
            nArrayList.add(name);
            nArrayList.add(number);

            mArrayList.add(nArrayList);
        }

        return mArrayList;
    }

    public static ArrayList<ArrayList<String>> getResultsFromJSONSeatAvailability(String resultJSON) throws JSONException {
        ArrayList<ArrayList<String>> outer = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(resultJSON);
        JSONArray jsonArray = jsonObject.getJSONArray("availability");

        for(int i=0; i<jsonArray.length(); i++) {
            ArrayList<String> inner = new ArrayList<>();

            String available = jsonArray.getJSONObject(i).getString("status");
            String date = jsonArray.getJSONObject(i).getString("date");
            inner.add(available);
            inner.add(date);

            outer.add(inner);
        }
        return outer;
    }
}
