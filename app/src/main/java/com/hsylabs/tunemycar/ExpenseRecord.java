package com.hsylabs.tunemycar;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.icu.text.SimpleDateFormat;
import android.icu.util.GregorianCalendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.SimpleTimeZone;

public class ExpenseRecord extends AppCompatActivity {
    private DatabaseHelper nDBHelper;
    ImageButton save, cancel;
    EditText eodometer,eprice,evolume,epaymenttype,enotes,estation,etotalcost,edatetime;
    Spinner spi;
    String license_plate,mm,y;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_record);
        Spinner expe = (Spinner) findViewById(R.id.expense_expenses);
        save = (ImageButton) findViewById(R.id.save);
        cancel = (ImageButton) findViewById(R.id.cancel);
        eodometer=(EditText)findViewById(R.id.expense_odometer);
        etotalcost=(EditText)findViewById(R.id.expense_total_cost);
        estation=(EditText)findViewById(R.id.expense_center_name);
        enotes=(EditText)findViewById(R.id.expense_notes);
        epaymenttype=(EditText)findViewById(R.id.expense_payment_time);
        edatetime=(EditText) findViewById(R.id.expense_date_time);
        spi=(Spinner)findViewById(R.id.expense_vehicle_name);
        nDBHelper = new DatabaseHelper(this);

        String[] dates = new String[]{"Accident", "Car Wash", "Fine","Insurance","MOT","Parking","Payment","Registration","Tax","Tools","Tow"};


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dates);
        expe.setAdapter(adapter1);
        y =expe.toString();

        // getting current date
//        long date = System.currentTimeMillis();
//        SimpleDateFormat sdf = new SimpleDateFormat("MM MM dd, yyyy h:mm a");
//        String dateString = sdf.format(date);
//        edatetime.setText(dateString);

        ArrayList<String> lis=nDBHelper.getvehiclename();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,lis);
        spi.setAdapter(adapter);
        mm=spi.toString();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExpenseRecord.this, Index.class);
                startActivity(intent);
            }
        });
        license_plate= nDBHelper.getPlate(mm);
//        Calendar calendar = new GregorianCalendar(edatetime.getYear(), edatetime.getMonth(), edatetime.getDayOfMonth();
    }
    public void adddd(View view)
    {
        try
        {
            nDBHelper.expense_record(eodometer.getText().toString(),y,etotalcost.getText().toString(),mm,edatetime.getText().toString() ,epaymenttype.getText().toString(),estation.getText().toString(), enotes.getText().toString(),license_plate);
            Toast.makeText(this,"Added",Toast.LENGTH_LONG).show();

        } catch (SQLiteException e)
        {
            Toast.makeText(ExpenseRecord.this, "Error while inserting...", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(ExpenseRecord.this, Index.class);
        startActivity(intent);
    }
}
