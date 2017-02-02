package com.hsylabs.tunemycar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.hsylabs.tunemycar.adapter.ListBrowseAdapter;
import com.hsylabs.tunemycar.model.Browse;

import java.util.List;

public class BrowseRecords extends AppCompatActivity {
    TextView browse_car_name, price;
    String newString;
    private ListView lvBrowse;
    private ListBrowseAdapter adapter;
    private List<Browse> mBrowseList;
    private DatabaseHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_records);

        browse_car_name = (TextView) findViewById(R.id.browse_car);
        price = (TextView) findViewById(R.id.price);

        mDBHelper = new DatabaseHelper(this);

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
        Log.d("HERE,", "DSADSADSADSADSA");

        browse_car_name.setText(newString);

        Double price1;
        price1 = mDBHelper.getCost(newString);
        price.setText(price1.toString());

        lvBrowse = (ListView)findViewById(R.id.listview_browse);
        Log.d("HERE,", "DSADSADSADSADSA");

        //Get product list in db when db exists
        mBrowseList = mDBHelper.getBrowse(newString);
        //Init adapter
        adapter = new ListBrowseAdapter(this, mBrowseList);
        //Set adapter for listview
        lvBrowse.setAdapter(adapter);
    }
}
