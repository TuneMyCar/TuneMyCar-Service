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
    private DatabaseHelper kkDBHelper;
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
        kkDBHelper = new DatabaseHelper(this);
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
      //  Log.d("new String",newString);
//        Double tc=kkDBHelper.gettankcapacity(newString);
//        pcarrange.setText(tc.toString());
//        Double current_odometer=kkDBHelper.getcurrentodometer(newString);
//        Double lastodo=kkDBHelper.getlastodometer(newString);
//        Double currentgal=kkDBHelper.getcurrentgalon(newString);
//
//        Double lastgalonn=kkDBHelper.getlastgallon(newString);
//
//        Double MPG=(current_odometer-lastodo)/(currentgal-lastgalonn);
 //       Double range=(tc/MPG)*100;
  //      pcarrange.setText(range.toString());

   //     Double nextfillup=current_odometer+range;
 //       pnfo.setText(nextfillup.toString());

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Predictions.this,Index.class);
                startActivity(i);
            }
        });

    }
}
