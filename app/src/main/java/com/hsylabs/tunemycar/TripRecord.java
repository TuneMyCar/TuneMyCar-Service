package com.hsylabs.tunemycar;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class TripRecord extends AppCompatActivity {
    private DatabaseHelper nDBHelper;
    ImageButton save, cancel;
    EditText tpurpose,tclient,tddatetime,tdodometer,tdlocation,tadatetime,taodometer,talocation,tamount,tnotes;
    Spinner sp;
    String m, mm,y,license_plate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_record);
        Spinner typ = (Spinner) findViewById(R.id.trip_type);
        save = (ImageButton) findViewById(R.id.save);
        cancel = (ImageButton) findViewById(R.id.cancel);
        tpurpose=(EditText)findViewById(R.id.trip_purpose);
        tclient=(EditText)findViewById(R.id.trip_client);
        tddatetime=(EditText)findViewById(R.id.trip_departure_date_time);
        tdodometer=(EditText)findViewById(R.id.trip_departure_odometer);
        tdlocation=(EditText)findViewById(R.id.trip_departure_location);
        tadatetime=(EditText)findViewById(R.id.trip_arrival_date_time);
        taodometer=(EditText)findViewById(R.id.trip_arrival_odometer);
        talocation=(EditText)findViewById(R.id.trip_arrival_location);
        tamount=(EditText)findViewById(R.id.trip_tax_amount);
        tnotes=(EditText)findViewById(R.id.trip_notes);
        sp = (Spinner) findViewById(R.id.trip_vehicle);

        nDBHelper = new DatabaseHelper(this);
        String[] dates = new String[]{"Business","Charity","Medical","Moving","Personal","Other"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dates);
        typ.setAdapter(adapter1);

        m = typ.toString();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TripRecord.this, Index.class);
                startActivity(intent);
            }
        });
        ArrayList<String> lis=nDBHelper.getvehiclename();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,lis);
        sp.setAdapter(adapter);
        mm=sp.toString();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TripRecord.this, Index.class);
                startActivity(intent);
            }
        });
        license_plate= nDBHelper.getPlate(mm);
    }
    public void adddddb(View view)
    {
        try
        {
            nDBHelper.trip_record(m,tpurpose.getText().toString(),tclient.getText().toString(),mm,tddatetime.getText().toString(),tdodometer.getText().toString(),tdlocation.getText().toString(),tadatetime.getText().toString(),taodometer.getText().toString(),talocation.getText().toString(),tamount.getText().toString(),tnotes.getText().toString(),license_plate);
            Toast.makeText(this,"Added",Toast.LENGTH_LONG).show();

        } catch (SQLiteException e)
        {
            Toast.makeText(TripRecord.this, "Error while inserting...", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(TripRecord.this, Index.class);
        startActivity(intent);
    }
}
