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
import android.widget.EditText;

import com.elmargomez.typer.Font;
import com.elmargomez.typer.Typer;
import com.example.manaspande.indianrailways.Adapters.FareEnquiryAdapter;
import com.example.manaspande.indianrailways.Adapters.StationCodeAutoCompleteAdapter;
import com.example.manaspande.indianrailways.Adapters.TrainNumberAutoCompleteAdapter;

import org.json.JSONException;

import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.color.white;

public class FareEnquiry extends AppCompatActivity {

    @BindView(R.id.fareEnquiryTrainNumber) AutoCompleteTextView fareEnquiryTrainNumber;
    @BindView(R.id.fareEnquirySourceCode) AutoCompleteTextView fareEnquirySourceCode;
    @BindView(R.id.fareEnquiryDestinationCode) AutoCompleteTextView fareEnquiryDestinationCode;
    @BindView(R.id.fareEnquiryAge) EditText fareEnquiryAge;
    @BindView(R.id.fareEnquiryDateOfJourney) EditText fareEnquiryDateOfJourney;
    @BindView(R.id.fareEnquiryRecyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.fab) FloatingActionButton submitButton;
    @BindView(R.id.collapsingToolbar) CollapsingToolbarLayout collapsingToolbar;

    FareEnquiryAdapter mAdapter;
    TrainNumberAutoCompleteAdapter trainNumberACAdapter;
    StationCodeAutoCompleteAdapter stationCodeACAdapter;
    Context FARE_ENQUIRY_CONTEXT = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fare_enquiry);
        ButterKnife.bind(this);

        trainNumberACAdapter = new TrainNumberAutoCompleteAdapter(this, android.R.layout.simple_dropdown_item_1line);
        fareEnquiryTrainNumber.setDropDownBackgroundResource(white);
        fareEnquiryTrainNumber.setAdapter(trainNumberACAdapter);

        stationCodeACAdapter = new StationCodeAutoCompleteAdapter(this, android.R.layout.simple_dropdown_item_1line);
        fareEnquirySourceCode.setDropDownBackgroundResource(white);
        fareEnquirySourceCode.setAdapter(stationCodeACAdapter);

        fareEnquiryDestinationCode.setDropDownBackgroundResource(white);
        fareEnquiryDestinationCode.setAdapter(stationCodeACAdapter);

        Typeface font = Typer.set(this).getFont(Font.ROBOTO_MEDIUM);
        Typeface font2 = Typer.set(this).getFont(Font.ROBOTO_LIGHT);
        fareEnquiryTrainNumber.setTypeface(font2);
        fareEnquirySourceCode.setTypeface(font2);
        fareEnquiryDestinationCode.setTypeface(font2);
        fareEnquiryAge.setTypeface(font2);
        fareEnquiryDateOfJourney.setTypeface(font2);

        collapsingToolbar.setExpandedTitleTypeface(font);
        collapsingToolbar.setTitle("Fare Enquiry");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userQuery1 = fareEnquiryTrainNumber.getText().toString();
                String userQuery2 = fareEnquirySourceCode.getText().toString();
                String userQuery3 = fareEnquiryDestinationCode.getText().toString();
                String userQuery4 = fareEnquiryAge.getText().toString();
                String userQuery6 = fareEnquiryDateOfJourney.getText().toString();
                ArrayList<String> mArrayList = queryArrayListGenerator("fare", "train", userQuery1, "source", userQuery2, "dest", userQuery3, "age", userQuery4, "quota", "GN", "doj", userQuery6);

                MyAsyncTask mAsyncTask = new MyAsyncTask();
                mAsyncTask.execute(mArrayList);
            }
        });
    }

    private class MyAsyncTask extends AsyncTask<ArrayList<String>, Void, ArrayList<ArrayList<String>>> {

        @Override
        protected ArrayList<ArrayList<String>> doInBackground(ArrayList<String>... params) {
            ArrayList<ArrayList<String>> resultArrayList = new ArrayList<>();
            ArrayList<String> queryArrayList = params[0];
            URL url = NetworkUtils.urlBuilder3(queryArrayList);
            String jsonResponse = NetworkUtils.getResponseFromHttpUrl(url);
            try {
                resultArrayList = NetworkUtils.getResultsFromJSONFareEnquiry(jsonResponse);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return resultArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<ArrayList<String>> al) {
            Integer itemCount = al.size();
            mAdapter = new FareEnquiryAdapter(FARE_ENQUIRY_CONTEXT, itemCount, al);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FARE_ENQUIRY_CONTEXT);

            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerView.setHasFixedSize(true);
        }
    }

    public ArrayList<String> queryArrayListGenerator(String fName, String qParam1, String q1, String qParam2, String q2, String qParam3, String q3, String qParam4, String q4, String qParam5, String q5, String qParam6, String q6) {
        ArrayList<String> al = new ArrayList<>();
        al.add(0, fName);
        al.add(1, qParam1);
        al.add(2, q1);
        al.add(3, qParam2);
        al.add(4, q2);
        al.add(5, qParam3);
        al.add(6, q3);
        al.add(7, qParam4);
        al.add(8, q4);
        al.add(9, qParam5);
        al.add(10, q5);
        al.add(11, qParam6);
        al.add(12, q6);
        return al;
    }
}
