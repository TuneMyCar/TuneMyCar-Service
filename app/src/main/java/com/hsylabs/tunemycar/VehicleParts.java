package com.hsylabs.tunemycar;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class VehicleParts extends AppCompatActivity {
    ImageButton can;
    EditText name,number,type,barand,quantitu,cost;
    private DatabaseHelper dbhelp;
    String newString,mm;
    Spinner spi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_parts);
        spi = (Spinner) findViewById(R.id.vehicle_part_vehicle_name);
        name=(EditText)findViewById(R.id.vehicle_part_name);
        number=(EditText)findViewById(R.id.vehicle_part_number);
        type=(EditText)findViewById(R.id.vehicle_part_type);
        barand=(EditText)findViewById(R.id.vehicle_part_type_brand);
        quantitu=(EditText)findViewById(R.id.vehicle_part_quantity);
        cost=(EditText)findViewById(R.id.vehicle_part_total_cost);
        can=(ImageButton)findViewById(R.id.cancel);
        dbhelp=new DatabaseHelper(this);
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

        ArrayList<String> lis=dbhelp.getvehiclename();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,lis);
        spi.setAdapter(adapter);
        mm=spi.getSelectedItem().toString();
        can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VehicleParts.this, Index.class);
                startActivity(intent);
            }
        });
    }
    public void ad(View view)
    {
        try
        {
            dbhelp.vehicle_part(name.getText().toString(),number.getText().toString(),type.getText().toString(),barand.getText().toString() ,quantitu.getText().toString(),cost.getText().toString());
            Toast.makeText(this,"Added",Toast.LENGTH_LONG).show();

        } catch (SQLiteException e)
        {
            Toast.makeText(VehicleParts.this, "Error while inserting...", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(VehicleParts.this, Index.class);
        startActivity(intent);
    }
}
