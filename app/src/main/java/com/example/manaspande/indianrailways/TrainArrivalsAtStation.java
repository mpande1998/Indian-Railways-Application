package com.example.manaspande.indianrailways;

import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.elmargomez.typer.Font;
import com.elmargomez.typer.Typer;
import com.example.manaspande.indianrailways.Adapters.StationCodeAutoCompleteAdapter;
import com.example.manaspande.indianrailways.Adapters.TrainArrivalsAtStationAdapter;

import org.json.JSONException;

import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.color.white;

public class TrainArrivalsAtStation extends AppCompatActivity {

    @BindView(R.id.stationCodeInput) AutoCompleteTextView stationCodeInput;
    @BindView(R.id.windowTimeInput) EditText windowTimeInput;
    @BindView(R.id.fab) FloatingActionButton submitButton;
    @BindView(R.id.TAASRecyclerView) RecyclerView mRecyclerView;

    TrainArrivalsAtStationAdapter mAdapter;
    Context TAAS_CONTEXT = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_arrivals_at_station);
        ButterKnife.bind(this);

        ((CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar)).setTitle("Train Arrivals at Station");
        StationCodeAutoCompleteAdapter stationCodeAutoCompleteAdapter = new StationCodeAutoCompleteAdapter(TAAS_CONTEXT, android.R.layout.simple_dropdown_item_1line);
        stationCodeInput.setDropDownBackgroundResource(white);
        stationCodeInput.setAdapter(stationCodeAutoCompleteAdapter);

        Typeface font = Typer.set(this).getFont(Font.ROBOTO_LIGHT);
        stationCodeInput.setTypeface(font);
        windowTimeInput.setTypeface(font);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userQuery1 = getStationCodeFromInput(stationCodeInput.getText().toString());
                String userQuery2 = windowTimeInput.getText().toString();
                ArrayList<String> mArrayList = queryArrayListGenerator("arrivals", "station", userQuery1, "hours", userQuery2);

                MyAsyncTask mAsyncTask = new MyAsyncTask();
                mAsyncTask.execute(mArrayList);
            }
        });
    }

    private class MyAsyncTask extends AsyncTask<ArrayList<String>, Void, ArrayList<ArrayList<String>>> {

        @Override
        protected ArrayList<ArrayList<String>> doInBackground(ArrayList<String>... params) {
            ArrayList<ArrayList<String>> mArrayList = new ArrayList<>();
            ArrayList<String> queryArrayList = params[0];
            URL url = NetworkUtils.urlBuilder2(queryArrayList);
            String JSONresponse = NetworkUtils.getResponseFromHttpUrl(url);
            try {
                mArrayList = NetworkUtils.getResultsFromJSONTAAS(JSONresponse);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return mArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<ArrayList<String>> al) {
            Integer itemCount = al.size();
            mAdapter = new TrainArrivalsAtStationAdapter(TAAS_CONTEXT, itemCount, al);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TAAS_CONTEXT);

            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerView.setHasFixedSize(true);
        }
    }
    public ArrayList<String> queryArrayListGenerator(String fName, String qParam1, String q1, String qParam2, String q2) {
        ArrayList<String> al = new ArrayList<>();
        al.add(0, fName);
        al.add(1, qParam1);
        al.add(2, q1);
        al.add(3, qParam2);
        al.add(4, q2);
        return al;
    }

    public String getStationCodeFromInput(String stationInput) {
        String[] arr = stationInput.split(" ", 2);
        String stationCode = arr[0];

        return stationCode;
    }
}
