package com.hsylabs.tunemycar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class statictics extends AppCompatActivity {
    TextView totalrunningcost,runningcostperday,runningcostpermile,distanperday,purchasecost,sellingprice,totalcostofownership,
             costofownershipperday,costofownershippermile,totaldistance,totaltime,head,maxgalon,mingalon,avggalon,lastgalon,
            totalgalon,avgmpg,maxmpg,minmpg,lastmpg,galnpf,dayspfil;
    String newString;
    Double total_cost;
    private DatabaseHelper kDBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statictics);
        totalrunningcost=(TextView)findViewById(R.id.stats_total_running_cost);
        runningcostperday=(TextView)findViewById(R.id.stats_total_running_cost_per_day);
        runningcostpermile=(TextView)findViewById(R.id.stats_running_cost_per_mile);
        distanperday=(TextView)findViewById(R.id.stats_distance_per_day);
        purchasecost=(TextView)findViewById(R.id.stats_purchase_costs);
        sellingprice=(TextView)findViewById(R.id.stats_selling_price);
        totalcostofownership=(TextView)findViewById(R.id.stats_total_cost_ownership);
        costofownershipperday=(TextView)findViewById(R.id.stats_tcost_ownership_per_day);
        costofownershippermile=(TextView)findViewById(R.id.stats_cost_ownership_per_mile);
        totaldistance=(TextView)findViewById(R.id.stats_total_distance);
        avgmpg=(TextView)findViewById(R.id.stats_avg_mpg);
        maxmpg=(TextView)findViewById(R.id.stats_max_mpg);
        minmpg=(TextView)findViewById(R.id.stats_min_mpg);
        lastmpg=(TextView)findViewById(R.id.stats_last_mpg);
        maxgalon=(TextView)findViewById(R.id.stats_max_repuee_per_gallon);
        galnpf=(TextView)findViewById(R.id.stats_galon_per_fillup);
        dayspfil=(TextView)findViewById(R.id.stats_days_per_fillup);
        totalgalon=(TextView)findViewById(R.id.stats_total_gal);
        mingalon=(TextView)findViewById(R.id.stats_min_rper_per_gal);
        avggalon=(TextView)findViewById(R.id.stats_avg_rupe_per_galon);
        lastgalon=(TextView)findViewById(R.id.stats_last_rupe_gal);
        totaltime=(TextView)findViewById(R.id.stats_total_time);
        head=(TextView)findViewById(R.id.header);
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
        head.setText(newString);
        Log.d("omer",newString);
       // Double total=kDBHelper.getCostt(newString);
        //totalcost of ownership
        Double total_ownership=kDBHelper.gettotalownership(newString);
        totalcostofownership.setText(total_ownership.toString()+ " PKR");

        Double sell=kDBHelper.getsellingprice(newString);
        sellingprice.setText(sell.toString()+ " PKR");

        Double current_odometer=kDBHelper.getcurrentodometer(newString);
        Double purchase_odometer=kDBHelper.getPurchaseOdometer(newString);
        Double result=current_odometer-purchase_odometer;
        totaldistance.setText(result.toString()+ " mi");

        purchasecost.setText(total_ownership.toString()+ " PKR");

        totaltime.setText("365 Days");

        Double result_distanceperday=result/365;
        distanperday.setText(String.format("%.2f", result_distanceperday));

        Double result_maxgallon=kDBHelper.getmaxgallon(newString);
        maxgalon.setText(result_maxgallon.toString());

        Double result_mingalon=kDBHelper.getmingallon(newString);
        mingalon.setText(result_mingalon.toString());

        Double result_avggalon=(result_maxgallon-result_mingalon)/2;
        avggalon.setText(String.format("%.2f",result_avggalon));

        Double lastgalonn=kDBHelper.getlastgallon(newString);
        lastgalon.setText(lastgalonn.toString());

        Double totalgalonn=kDBHelper.gettotalgalon(newString);
        totalgalon.setText(totalgalonn.toString());

        Double currentgal=kDBHelper.getcurrentgalon(newString);
        Double lastodo=kDBHelper.getlastodometer(newString);
        Double MPG=(current_odometer-lastodo)/(currentgal-lastgalonn);
        maxmpg.setText(MPG.toString());

        lastmpg.setText(MPG.toString());

        Double minmpgg=MPG/2;
        minmpg.setText(String.format("%.2f",minmpgg));

        Double avgmpgg=(MPG-minmpgg)/2;
        avgmpg.setText(String.format("%.2f",avgmpgg));

        Double countfill=kDBHelper.getcountoffillups(newString);
        Double galpfillup=totalgalonn/countfill;
        galnpf.setText(galpfillup.toString());

        Double daypfill=30/countfill;
        dayspfil.setText(daypfill.toString());


    }
}
