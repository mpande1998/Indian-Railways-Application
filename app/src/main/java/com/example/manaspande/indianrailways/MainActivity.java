package com.example.manaspande.indianrailways;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.pnrStatusSubmitButton) Button pnrStatusSubmitButton;
    @BindView(R.id.trainRouteSubmitButton) Button trainRouteSubmitButton;
    @BindView(R.id.seatAvailability) Button seatAvailability;
    @BindView(R.id.fareEnquiry) Button fareEnquiry;
    @BindView(R.id.trainArrivalsAtStation) Button trainArrivalsAtStation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        trainRouteSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TrainRoute.class);
                startActivity(intent);
            }
        });

        pnrStatusSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PnrStatus.class);
                startActivity(intent);
            }
        });

        seatAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SeatAvailability.class);
                startActivity(intent);
            }
        });

        fareEnquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FareEnquiry.class);
                startActivity(intent);
            }
        });

        trainArrivalsAtStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TrainArrivalsAtStation.class);
                startActivity(intent);
            }
        });
    }

}
