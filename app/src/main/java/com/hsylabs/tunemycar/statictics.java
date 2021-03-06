package com.hsylabs.tunemycar;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class statictics extends AppCompatActivity {
    ImageButton can;
    TextView totalrunningcost,runningcostperday,runningcostpermile,distanperday,purchasecost,sellingprice,totalcostofownership,
             costofownershipperday,costofownershippermile,totaldistance,totaltime,head,maxgalon,mingalon,avggalon,lastgalon,
            totalgalon,avgmpg,maxmpg,minmpg,lastmpg,galnpf,dayspfil,totalrupe,stasperday,statspermile,avgrupeepergallon,lastrupeepergalon,
            minrupeepergalon,maxrupeepergalon;
    String newString;
    Double total_cost;
    private DatabaseHelper kDBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statictics);
        can=(ImageButton)findViewById(R.id.cancel);
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
        totalrupe=(TextView)findViewById(R.id.stats_total_rupee);
        head=(TextView)findViewById(R.id.header);
        stasperday=(TextView)findViewById(R.id.stats_total_running_cost_per_day);
        statspermile=(TextView)findViewById(R.id.stats_running_cost_per_mile);
        avgrupeepergallon=(TextView)findViewById(R.id.stats_rupee_per_mile);
        lastrupeepergalon=(TextView)findViewById(R.id.stats_rs_fillup);
        minrupeepergalon=(TextView)findViewById(R.id.stats_rupee_per_day);
        maxrupeepergalon=(TextView)findViewById(R.id.stats_mile_per_rupee);
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
        Double total=kDBHelper.getCostt(newString);
        totalrunningcost.setText(total.toString());
        //totalcost of ownership
        Double total_ownership=kDBHelper.gettotalownership(newString);
        Double restotl=total_ownership+total;
        totalcostofownership.setText(restotl.toString()+ " PKR");

        Double ownershipperday=restotl/365;
        costofownershipperday.setText(String.format("%.2f",ownershipperday)+" PKR");

        Double sell=kDBHelper.getsellingprice(newString);
        sellingprice.setText(sell.toString()+ " PKR");

        Double current_odometer=kDBHelper.getcurrentodometer(newString);
        Double purchase_odometer=kDBHelper.getPurchaseOdometer(newString);
        Double result=current_odometer-purchase_odometer;
        totaldistance.setText(result.toString()+ " mi");

        Double ownershippermile=restotl/result;
        costofownershippermile.setText(String.format("%.2f",ownershippermile)+" PKR");

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
        Double chotaMPG=currentgal-lastgalonn;
        if(chotaMPG<=0)
        {
            Log.d("Omer", "ypp");
        }
        Double MPG=(current_odometer-lastodo)/chotaMPG;

            maxmpg.setText(String.format("%.2f",MPG));

            lastmpg.setText(String.format("%.2f",MPG));

            Double minmpgg=MPG/2;
            minmpg.setText(String.format("%.2f",minmpgg));

            Double avgmpgg=(MPG-minmpgg)/2;
            avgmpg.setText(String.format("%.2f",avgmpgg));



        Double countfill=kDBHelper.getcountoffillups(newString);
        Double galpfillup=totalgalonn/countfill;
        galnpf.setText(String.format("%.2f",galpfillup));

        Double daypfill=30/countfill;
        dayspfil.setText(String.format("%.2f",daypfill));

        totalrupe.setText(total.toString());

        Double runningcostperday=total/30;
        stasperday.setText(String.format("%.2f",runningcostperday));

        Double runpermile=total/MPG;
        statspermile.setText(String.format("%.2f",runpermile));


        Double nfill=current_odometer+MPG;
        Double res=nfill/total;
        avgrupeepergallon.setText(String.format("%.2f",res));

        Double res1=nfill/countfill;
        lastrupeepergalon.setText(String.format("%.2f",res1));

        Double res2=MPG/total;
        maxrupeepergalon.setText(String.format("%.2f",res2));

        Double re4=total/30;
        minrupeepergalon.setText(String.format("%.2f",re4));
    }
}
