package com.hsylabs.tunemycar;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class VehicleDetails extends AppCompatActivity {
    TextView lp,vin,ins,tc,pp,po,pd,sp,so,sd,n,m,model,year,header;
    private DatabaseHelper kDBHelper;
    ImageButton del;
    String newString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_details);
        lp=(TextView)findViewById(R.id.detail_license_plate);
        m=(TextView)findViewById(R.id.detail_name);
        model=(TextView)findViewById(R.id.detail_model);
        year=(TextView)findViewById(R.id.detail_year);
        vin=(TextView)findViewById(R.id.detail_vin);
        ins=(TextView)findViewById(R.id.detail_ins_policy);
        tc=(TextView) findViewById(R.id.detail_tank_capacity);
        pp=(TextView)findViewById(R.id.detail_purchase_price);
        po=(TextView)findViewById(R.id.detail_purchase_odometer);
        pd=(TextView)findViewById(R.id.detail_purchase_date);
        sp = (TextView)findViewById(R.id.detail_sell_price);
        so=(TextView)findViewById(R.id.detail_sell_odometer);
        sd=(TextView)findViewById(R.id.detail_sell_date);
        n=(TextView)findViewById(R.id.detail_notes);
        header = (TextView)findViewById(R.id.header);
        del = (ImageButton) findViewById(R.id.del);
        kDBHelper = new DatabaseHelper(this);



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

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(VehicleDetails.this)
                        .setMessage("Are you sure you want to delete?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                VehicleDetails.this.finish();
                                if(kDBHelper.deleteRecord(newString)) {
                                    Toast.makeText(VehicleDetails.this, "Deleted", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(VehicleDetails.this, Index.class);
                                    startActivity(i);
                                }
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
        header.setText(newString);

        String[] data = {""};

        data = kDBHelper.getVehicleDetail(newString);

        m.setText(data[1]);
        model.setText(data[2]);
        year.setText(data[3]);
        lp.setText(data[4]);
        vin.setText(data[5]);
        ins.setText(data[6]);
        tc.setText(data[7]+" gal");
        pp.setText("RS. "+data[8]);
        po.setText(data[9]+" km");
        pd.setText(data[10]);
        sp.setText("RS. "+data[11]);
        so.setText(data[12]+" km");
        sd.setText(data[13]);
        n.setText(data[14]);

    }

}
