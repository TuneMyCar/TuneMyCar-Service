package com.hsylabs.tunemycar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Charts extends AppCompatActivity {

    LineGraphSeries<DataPoint> series;
    DatabaseHelper mDBHelper;
    String newString;
    double days = 0;
    String miles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);

        mDBHelper = new DatabaseHelper(this);
        String[] arr={""};

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

        arr = mDBHelper.getMiles(newString);
        GraphView graphView=(GraphView) findViewById(R.id.graphid);
        series = new LineGraphSeries<DataPoint>();
        int res = 0;

        for(int i=0;i<arr.length-2;i++)
        {
            days++;
            miles=arr[i];
            res = Integer.parseInt(arr[i]);
           Log.d("OMer", String.valueOf(res));
            int d=Integer.parseInt(miles);
            series.appendData(new DataPoint(days,res),true,100);
        }

        graphView.addSeries(series);

    }
}
