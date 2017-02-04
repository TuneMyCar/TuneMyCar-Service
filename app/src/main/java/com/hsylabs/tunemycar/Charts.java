package com.hsylabs.tunemycar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Charts extends AppCompatActivity {

    LineGraphSeries<DataPoint> series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);
        double miles,days;
        days=-0.5;

        GraphView graphView=(GraphView) findViewById(R.id.graphid);
        series = new LineGraphSeries<DataPoint>();

        for(int i=0;i<500;i++)
        {
            days=days+0.1;
            miles=Math.sin(days);
            series.appendData(new DataPoint(days,miles),true,500);

        }
        graphView.addSeries(series);

    }
}
