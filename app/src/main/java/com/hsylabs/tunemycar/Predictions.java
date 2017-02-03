package com.hsylabs.tunemycar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Predictions extends AppCompatActivity {

    ImageButton cancel;
    TextView pnfo,pcarrange,ptripcost,ptripmile,ptripcost100;
    private DatabaseHelper kDBHelper;
    String newString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predictions);
        cancel=(ImageButton)findViewById(R.id.cancel);
        pnfo=(TextView)findViewById(R.id.prediction_fillup_odometer);
        pcarrange=(TextView)findViewById(R.id.detail_car_range);
        ptripcost=(TextView)findViewById(R.id.detail_cost_per_day);
        ptripmile=(TextView)findViewById(R.id.trip_cost_per_mile);
        ptripcost100=(TextView)findViewById(R.id.rip_cost_per_100mile);
        kDBHelper = new DatabaseHelper(this);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString("car_name");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("car_name");
        }
        Double lastgalonn=kDBHelper.getlastgallon(newString);
        Double currentgal=kDBHelper.getcurrentgalon(newString);
        Double lastodo=kDBHelper.getlastodometer(newString);
        Double current_odometer=kDBHelper.getcurrentodometer(newString);
        Double chotaMPG=currentgal-lastgalonn;
        if(chotaMPG<=0)
        {
            Log.d("Omer", "ypp");
        }
        Double MPG=(current_odometer-lastodo)/chotaMPG;
        if(MPG<0)
        {
            MPG=MPG*(-1);
        }
        Double nfill=current_odometer+MPG;
        pnfo.setText(String.format("%.2f",nfill));

        Double tc=kDBHelper.gettankcapacity(newString);
        Double car_range=(tc/MPG)*100;
        pcarrange.setText(String.format("%.2f",car_range));


        Double tripcostperday=car_range/30;
        ptripcost.setText(String.format("%.2f",tripcostperday));

        Double tdistance=kDBHelper.getlastodometer(newString);
        Double permile=car_range/tdistance;
        ptripmile.setText(String.format("%.2f",permile));

        Double per100=car_range/(tdistance*100);
        ptripcost100.setText(String.format("%.2f",per100));
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Predictions.this,Index.class);
                startActivity(i);
            }
        });

    }
}
