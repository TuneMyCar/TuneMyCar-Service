package com.hsylabs.tunemycar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

public class Predictions extends AppCompatActivity {

    ImageButton cancel;
    TextView pnfo,pcarrange,pfdate,ptripcost,ptripmile,ptripcost100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predictions);
        cancel=(ImageButton)findViewById(R.id.cancel);
        pnfo=(TextView)findViewById(R.id.prediction_fillup_odometer);
        pcarrange=(TextView)findViewById(R.id.detail_car_range);
        ptripcost=(TextView)findViewById(R.id.detail_cost_per_day);
        pfdate=(TextView)findViewById(R.id.prediction_fillup_date);
        ptripmile=(TextView)findViewById(R.id.trip_cost_per_mile);
        ptripcost100=(TextView)findViewById(R.id.rip_cost_per_100mile);


    }
}
