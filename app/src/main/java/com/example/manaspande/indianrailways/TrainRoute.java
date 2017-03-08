package com.example.manaspande.indianrailways;

import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AutoCompleteTextView;

import com.elmargomez.typer.Font;
import com.elmargomez.typer.Typer;
import com.example.manaspande.indianrailways.Adapters.TrainNumberAutoCompleteAdapter;
import com.example.manaspande.indianrailways.Adapters.TrainRouteAdapter;

import org.json.JSONException;

import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.color.white;

public class TrainRoute extends AppCompatActivity {

    @BindView(R.id.trainRouteInput) AutoCompleteTextView trainRouteInput;
    @BindView(R.id.fab) FloatingActionButton submitButton;
    @BindView(R.id.trainRouteRecyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.collapsingToolbar) CollapsingToolbarLayout collapsingToolbar;

    TrainNumberAutoCompleteAdapter mTrainNumberAutoCompleteAdapter;
    TrainRouteAdapter mAdapter;
    Context TRAIN_ROUTE_CONTEXT = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_route);
        ButterKnife.bind(this);

        mTrainNumberAutoCompleteAdapter = new TrainNumberAutoCompleteAdapter(this, android.R.layout.simple_dropdown_item_1line);
        trainRouteInput.setDropDownBackgroundResource(white);
        trainRouteInput.setAdapter(mTrainNumberAutoCompleteAdapter);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userQuery = trainRouteInput.getText().toString();
                String query = userQuery.substring(0, 5);
                ArrayList<String> mArrayList = queryArrayListGenerator("route", "train", query);

                MyAsyncTask mAsyncTask = new MyAsyncTask();
                mAsyncTask.execute(mArrayList);
            }
        });

        Typeface font = Typer.set(this).getFont(Font.ROBOTO_MEDIUM);
        Typeface font2 = Typer.set(this).getFont(Font.ROBOTO_LIGHT);
        trainRouteInput.setTypeface(font2);

        collapsingToolbar.setExpandedTitleTypeface(font);
        collapsingToolbar.setTitle("Train Route");
    }

    private class MyAsyncTask extends AsyncTask<ArrayList<String>, Void, ArrayList<ArrayList<String>>> {

        @Override
        protected ArrayList<ArrayList<String>> doInBackground(ArrayList<String>... params) {
            ArrayList<String> queryArrayList = params[0];
            URL url = NetworkUtils.urlBuilder(queryArrayList);
            String jsonResponse = NetworkUtils.getResponseFromHttpUrl(url);
            ArrayList<ArrayList<String>> mArrayList = new ArrayList<>();

            try {
                mArrayList = NetworkUtils.getResultsFromJSONTrainRoute(jsonResponse);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return mArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<ArrayList<String>> al) {
            Integer itemCount = al.size();
            mAdapter = new TrainRouteAdapter(TRAIN_ROUTE_CONTEXT, itemCount, al);
            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(TRAIN_ROUTE_CONTEXT);

            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(mLinearLayoutManager);
            mRecyclerView.setHasFixedSize(true);
        }
    }

    public ArrayList<String> queryArrayListGenerator(String fName, String qParam, String q) {
        ArrayList<String> al = new ArrayList<>();
        al.add(0, fName);
        al.add(1, qParam);
        al.add(2, q);
        return al;
    }
}