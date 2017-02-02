package com.hsylabs.tunemycar;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FillUpRecord extends AppCompatActivity {
    private DatabaseHelper nDBHelper;
    ImageButton save, cancel;
    EditText fodometer,fprice,fvolume,fdatetime,fpaymenttype,fnotes,fstation;
    TextView ftotalcost;
    Spinner spi;
    public static final String DBNAME = "myCarDB.sqlite";
    String license_plate,mm,y;
    int m,k,total, sum=0;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_up_record);
        //  Spinner fcategory = (Spinner) findViewById(R.id.fill_up_category);
        Spinner fvehicle = (Spinner) findViewById(R.id.fill_up_category);

        save = (ImageButton) findViewById(R.id.save);
        cancel = (ImageButton) findViewById(R.id.cancel);
        fodometer=(EditText)findViewById(R.id.fill_up_odometer);
        fprice=(EditText)findViewById(R.id.fill_up_price);
        fvolume=(EditText)findViewById(R.id.fill_up_volume);
        fdatetime=(EditText)findViewById(R.id.fill_up_date_time);
        fpaymenttype=(EditText)findViewById(R.id.fill_up_payment_type);
        ftotalcost=(EditText)findViewById(R.id.fill_up_total_cost);
        fnotes=(EditText)findViewById(R.id.fill_up_notes);
        fstation=(EditText)findViewById(R.id.fill_up_station_address);
        spi=(Spinner)findViewById(R.id.fill_up_vehicle);

        nDBHelper = new DatabaseHelper(this);

        String[] dates = new String[]{"Gasoline", "Diesel", "Biodiesel","Bioalcohol","Gas"};


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dates);
        fvehicle.setAdapter(adapter1);
        y = fvehicle.toString();

        ArrayList<String> lis=nDBHelper.getvehiclename();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,lis);
        spi.setAdapter(adapter);
        mm=spi.toString();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FillUpRecord.this, Index.class);
                startActivity(intent);
            }
        });

        license_plate= nDBHelper.getPlate(mm);

    }

    public void addd(View view)
    {
        try
        {
            nDBHelper.fill_up_record(fodometer.getText().toString(),fprice.getText().toString(),fvolume.getText().toString(),ftotalcost.getText().toString() ,mm,fdatetime.getText().toString(),fpaymenttype.getText().toString(),fstation.getText().toString(),fnotes.getText().toString(),license_plate);
            Toast.makeText(this,"Added",Toast.LENGTH_LONG).show();

        } catch (SQLiteException e)
        {
            Toast.makeText(FillUpRecord.this, "Error while inserting...", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(FillUpRecord.this, Index.class);
        startActivity(intent);
    }
}
