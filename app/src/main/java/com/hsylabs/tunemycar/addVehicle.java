package com.hsylabs.tunemycar;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class addVehicle extends AppCompatActivity {

    ImageButton save, cancel;
    EditText vname, vmodel, vlicenseplate, vvin, vinspolicy, vpprice, vpodometer, vsodometer, vnotes, vtankcapacity,vpdate,vsdate,vsprice;
    String make, years;
    private DatabaseHelper addDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);
        //connecting widgets
        Spinner make1 = (Spinner) findViewById(R.id.vehicle_make);
        Spinner year = (Spinner) findViewById(R.id.vehicle_year);
        save = (ImageButton) findViewById(R.id.save);
        cancel = (ImageButton) findViewById(R.id.cancel);
        vlicenseplate = (EditText) findViewById(R.id.vehicle_license_plate);
        vname = (EditText) findViewById(R.id.vehicle_name);
        vmodel = (EditText) findViewById(R.id.vehicle_model);
        vtankcapacity = (EditText) findViewById(R.id.vehicle_tank_capacity);
        vnotes = (EditText) findViewById(R.id.vehicle_notes);
        vvin = (EditText) findViewById(R.id.vehicle_vin);
        vinspolicy = (EditText) findViewById(R.id.vehicle_ins_policy);
        vpprice = (EditText) findViewById(R.id.vehicle_price);
        vpodometer = (EditText) findViewById(R.id.vehicle_odometer);
        vsodometer = (EditText) findViewById(R.id.vehicle_sell_odometer);
        vpdate=(EditText)findViewById(R.id.vehicle_date);
        vsprice=(EditText)findViewById(R.id.vehicle_sell_price);
        vsdate=(EditText)findViewById(R.id.vehicle_sell_date);
        addDatabaseHelper =new DatabaseHelper(this);
         //making spinner for dates and car brands
        String[] dates = new String[]{"1995", "1996", "1997","1998","1999","2000","2001","2002","2003","2004","2005","2006","2007","2008","2009","2010","2011","2012","2013","2014","2015","2016","2017"};
        String[] brand_make = new String[]{"Honda", "Toyota", "Suzuki","Dodge","Audi","Benz","BMW","Bugati","Ford","Hyundai","Lexus","Kia","Jeep","Porshe","Volvo","Nissan","Mitsubishi"};
       //setting widgets
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, brand_make);
        make1.setAdapter(adapter);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dates);
        year.setAdapter(adapter1);


        make = make1.getSelectedItem().toString();
        years = year.getSelectedItem().toString();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(addVehicle.this, Index.class);
                startActivity(intent);
            }
        });
        if(vname==null)
        {
            Toast.makeText(this,"Enter a Unique Name",Toast.LENGTH_LONG).show();
        }
    }
    public void add(View view)
    {
        try
        {
            addDatabaseHelper.insert_vehicle(vname.getText().toString(), make, vmodel.getText().toString(),
                    years,vlicenseplate.getText().toString(),vvin.getText().toString(),
                    vinspolicy.getText().toString(),vtankcapacity.getText().toString(),
                    vpprice.getText().toString(),vpodometer.getText().toString(),vpdate.getText().toString(),
                    vsprice.getText().toString(),vsodometer.getText().toString(),vsdate.getText().toString(),
                    vnotes.getText().toString());

            Toast.makeText(this,"Added",Toast.LENGTH_LONG).show();

        } catch (SQLiteException e)
        {
            Toast.makeText(addVehicle.this, "Error while inserting...", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(addVehicle.this, Index.class);
        startActivity(intent);
    }
}
