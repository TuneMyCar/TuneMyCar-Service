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

public class ServiceRecords extends AppCompatActivity {
    private DatabaseHelper nDBHelper;
    ImageButton save, cancel;
    EditText sodometer,stotalcost,sdatetime,spaymenttype,scentername,snotes;

    String m;
    String license_plate,mm,y;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_records);
        Spinner make1 = (Spinner) findViewById(R.id.service_services);
        Spinner year = (Spinner) findViewById(R.id.service_vehicle_name);

        save = (ImageButton) findViewById(R.id.save);
        cancel = (ImageButton) findViewById(R.id.cancel);
        sodometer=(EditText)findViewById(R.id.service_odometer);
        stotalcost=(EditText)findViewById(R.id.service_total_cost);
        sdatetime=(EditText)findViewById(R.id.service_date_time);
        spaymenttype=(EditText)findViewById(R.id.service_payment_type);
        scentername=(EditText)findViewById(R.id.service_center_name);
        snotes=(EditText)findViewById(R.id.service_notes);
        nDBHelper = new DatabaseHelper(this);

        String[] dates = new String[]{"A/C System", "Air Filter", "Battery","Belts","Body/Chasis","Brake Fluid","Brakes Front","Brakes Rear","Doors","Engine Oil","Oil Filter","Radiator","Tires","Lights","Wheel ALignment","Horns","Fuel Pumps"};


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dates);
        make1.setAdapter(adapter1);
        y = make1.toString();

        ArrayList<String> lis=nDBHelper.getvehiclename();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,lis);
        year.setAdapter(adapter);
        mm=year.toString();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ServiceRecords.this,Index.class);
                startActivity(intent);
            }
        });
        license_plate= nDBHelper.getPlate(mm);
    }
    public void adddd(View view)
    {
        try
        {
            nDBHelper.service_record(sodometer.getText().toString(),y,stotalcost.getText().toString(),mm,sdatetime.getText().toString(),spaymenttype.getText().toString(),scentername.getText().toString(),snotes.getText().toString(),license_plate);
            Toast.makeText(this,"Added",Toast.LENGTH_LONG).show();

        } catch (SQLiteException e)
        {
            Toast.makeText(ServiceRecords.this, "Error while inserting...", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(ServiceRecords.this, Index.class);
        startActivity(intent);
    }
}
