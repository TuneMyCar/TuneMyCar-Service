package com.hsylabs.tunemycar;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.ParagraphStyle;
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

    double mul = 0;
    double mul2 = 0;
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

        // TOTAL COST FUNCTION


        fvolume.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String inputText = fvolume.getText().toString();
                try {
                    mul = Double.parseDouble(inputText);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                String fpp = fprice.getText().toString();
                try {
                    mul2 = Double.parseDouble(fpp);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
//                BigDecimal result = new BigDecimal("0.3").multiply( new BigDecimal("3.0") );
                double res = mul * mul2;
                ftotalcost.setText(String.format("%.2f", res));

            }
        });


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
