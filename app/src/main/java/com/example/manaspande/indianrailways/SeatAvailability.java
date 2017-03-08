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
import android.widget.Spinner;

import com.elmargomez.typer.Font;
import com.elmargomez.typer.Typer;
import com.example.manaspande.indianrailways.Adapters.SeatAvailabilityAdapter;
import com.example.manaspande.indianrailways.Adapters.StationCodeAutoCompleteAdapter;
import com.example.manaspande.indianrailways.Adapters.TrainNumberAutoCompleteAdapter;
import com.example.manaspande.indianrailways.Adapters.SpinnerAdapter;

import org.json.JSONException;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.color.white;

public class SeatAvailability extends AppCompatActivity {

    @BindView(R.id.SADateInput) EditText SADateInput;
    @BindView(R.id.SATrainNumberInput) AutoCompleteTextView SATrainNumberInput;
    @BindView(R.id.SASourceCodeInput) AutoCompleteTextView SASourceCodeInput;
    @BindView(R.id.SADestinationCodeInput) AutoCompleteTextView SADestinationCodeInput;
    @BindView(R.id.SAClassCodeInput) Spinner SAClassCodeInput;
    @BindView(R.id.seatAvailabilityRecyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.fab) FloatingActionButton submitButton;
    @BindView(R.id.collapsingToolbar) CollapsingToolbarLayout collapsingToolbar;

    TrainNumberAutoCompleteAdapter trainNumberAutoCompleteAdapter;
    StationCodeAutoCompleteAdapter stationCodeAutoCompleteAdapter;
    SeatAvailabilityAdapter mAdapter;
    Context SEAT_AVAILABILITY_CONTEXT = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_availability);
        ButterKnife.bind(this);

        trainNumberAutoCompleteAdapter = new TrainNumberAutoCompleteAdapter(this, android.R.layout.simple_dropdown_item_1line);
        SATrainNumberInput.setDropDownBackgroundResource(white);
        SATrainNumberInput.setAdapter(trainNumberAutoCompleteAdapter);

        stationCodeAutoCompleteAdapter = new StationCodeAutoCompleteAdapter(this, android.R.layout.simple_dropdown_item_1line);
        SASourceCodeInput.setDropDownBackgroundResource(white);
        SASourceCodeInput.setAdapter(stationCodeAutoCompleteAdapter);

        SADestinationCodeInput.setDropDownBackgroundResource(white);
        SADestinationCodeInput.setAdapter(stationCodeAutoCompleteAdapter);

        SpinnerAdapter mSpinnerAdapter = new SpinnerAdapter(this, R.layout.custom_spinner_item, Arrays.asList(getResources().getStringArray(R.array.class_codes)));
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SAClassCodeInput.setAdapter(mSpinnerAdapter);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userQuery1 = SATrainNumberInput.getText().toString();
                String userQuery2 = SASourceCodeInput.getText().toString();
                userQuery2 = getStationCodeFromInput(userQuery2);
                String userQuery3 = SADestinationCodeInput.getText().toString();
                userQuery3 = getStationCodeFromInput(userQuery3);
                String userQuery4 = SADateInput.getText().toString();
                String userQuery5 = SAClassCodeInput.getSelectedItem().toString();
                userQuery5 = getClassCodeFromInput(userQuery5);
                ArrayList<String> mArrayList = queryArrayListGenerator("check_seat", "train", userQuery1, "source", userQuery2, "dest", userQuery3, "date", userQuery4, "class", userQuery5, "quota", "GN");

                MyAsyncTask mAsyncTask = new MyAsyncTask();
                mAsyncTask.execute(mArrayList);
            }
        });

        Typeface font = Typer.set(this).getFont(Font.ROBOTO_MEDIUM);
        Typeface font2 = Typer.set(this).getFont(Font.ROBOTO_LIGHT);
        SATrainNumberInput.setTypeface(font2);
        SASourceCodeInput.setTypeface(font2);
        SADestinationCodeInput.setTypeface(font2);
        SADateInput.setTypeface(font2);

        collapsingToolbar.setExpandedTitleTypeface(font);
        collapsingToolbar.setTitle("Seat Availability");
    }

    private class MyAsyncTask extends AsyncTask<ArrayList<String>, Void, ArrayList<ArrayList<String>>> {

        @Override
        protected ArrayList<ArrayList<String>> doInBackground(ArrayList<String>... params) {
            ArrayList<String> queryArrayList = params[0];
            URL queryUrl = NetworkUtils.urlBuilder3(queryArrayList);
            String jsonResponse = NetworkUtils.getResponseFromHttpUrl(queryUrl);
            ArrayList<ArrayList<String>> resultArrayList = new ArrayList<>();
            try {
                resultArrayList = NetworkUtils.getResultsFromJSONSeatAvailability(jsonResponse);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return resultArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<ArrayList<String>> al) {
            mAdapter = new SeatAvailabilityAdapter(SEAT_AVAILABILITY_CONTEXT, al);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(SEAT_AVAILABILITY_CONTEXT));
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

    public String getClassCodeFromInput(String spinnerInput) {
        String[] arr = spinnerInput.split(" ");
        String stationCode = arr[arr.length - 1];

        return stationCode;
    }

    public String getStationCodeFromInput(String stationInput) {
        String[] arr = stationInput.split(" ", 2);
        String stationCode = arr[0];

        return stationCode;
    }
}
