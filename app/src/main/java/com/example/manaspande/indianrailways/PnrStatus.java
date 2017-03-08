package com.example.manaspande.indianrailways;

import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PnrStatus extends AppCompatActivity {

    @BindView(R.id.pnrStatusInput) EditText pnrStatusInput;
    @BindView(R.id.fab) FloatingActionButton submitPnr;
    @BindView(R.id.pnrRecyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pnr_status);
        ButterKnife.bind(this);

        submitPnr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userQuery = pnrStatusInput.getText().toString();
                ArrayList<String> mArrayList = queryArrayListGenerator("pnr_status", "pnr", userQuery);

                PnrAsyncTask mAsyncTask = new PnrAsyncTask();
                mAsyncTask.execute(mArrayList);
            }
        });
    }

    private class PnrAsyncTask extends AsyncTask<ArrayList<String>, Void, String> {

        @Override
        protected String doInBackground(ArrayList<String>... params) {
            ArrayList<String> queryArrayList = params[0];
            URL queryUrl = NetworkUtils.urlBuilder(queryArrayList);
            String JSONresponse = NetworkUtils.getResponseFromHttpUrl(queryUrl);

            return JSONresponse;
        }

        @Override
        protected void onPostExecute(String response) {
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
