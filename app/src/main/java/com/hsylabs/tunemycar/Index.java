package com.hsylabs.tunemycar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.IOException;
import java.security.acl.Permission;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Manifest;

public class Index extends AppCompatActivity {
    private ViewStub stubGrid;
    private ViewStub stubList;
    private ListView listView;
    private GridView gridView;
    private ListViewAdapter listViewAdapter;
    private GridViewAdapter gridViewAdapter;
    private List<Product> productList;
    private int currentViewMode = 0;
    private DatabaseHelper mDBHelper;
    ImageButton team;
    Spinner spin;
    String car_name = null;

    static final int VIEW_MODE_LISTVIEW = 0;
    static final int VIEW_MODE_GRIDVIEW = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        spin = (Spinner) findViewById(R.id.spinner);
        stubList = (ViewStub) findViewById(R.id.stub_list);
        stubGrid = (ViewStub) findViewById(R.id.stub_grid);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle(R.string.app_name);

        Log.d("OMERE", "FDSF");
        mDBHelper = new DatabaseHelper(this);

        //      Check exists database
//        File database = Index.this.getDatabasePath(DatabaseHelper.DB_NAME);
//        if (database.exists()) {
//            mDBHelper.getReadableDatabase();
//            //Copy db
//            try {
//                if (mDBHelper.copyDatabase()) {
//                    Toast.makeText(this, "Copy database success1", Toast.LENGTH_SHORT).show();
//                } else {
//
//                    Log.d("Omer", "IN D2B");
//                    Toast.makeText(this, "Copy data error", Toast.LENGTH_SHORT).show();
//
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        //Inflate ViewStub before get view

        stubList.inflate();
        stubGrid.inflate();

        listView = (ListView) findViewById(R.id.mylistview);
        gridView = (GridView) findViewById(R.id.mygridview);

        //get list of product
        getProductList();

        //Get current view mode in share reference
        SharedPreferences sharedPreferences = getSharedPreferences("ViewMode", MODE_PRIVATE);
        currentViewMode = sharedPreferences.getInt("currentViewMode", VIEW_MODE_LISTVIEW);//Default is view listview
        //Register item lick
        listView.setOnItemClickListener(onItemClick);
        gridView.setOnItemClickListener(onItemClick);

        switchView();

        mDBHelper.openDatabase();
        ArrayList<String> lis = mDBHelper.getvehiclename();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, lis);
        spin.setAdapter(adapter);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + " selected", Toast.LENGTH_LONG).show();
                car_name = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void switchView() {

        if (VIEW_MODE_LISTVIEW == currentViewMode) {
            //Display listview
            stubList.setVisibility(View.VISIBLE);
            //Hide gridview
            stubGrid.setVisibility(View.GONE);
        } else {
            //Hide listview
            stubList.setVisibility(View.GONE);
            //Display gridview
            stubGrid.setVisibility(View.VISIBLE);
        }
        setAdapters();
    }

    private void setAdapters() {
        if (VIEW_MODE_LISTVIEW == currentViewMode) {
            listViewAdapter = new ListViewAdapter(this, R.layout.list_item, productList);
            listView.setAdapter(listViewAdapter);
        } else {
            gridViewAdapter = new GridViewAdapter(this, R.layout.grid_item, productList);
            gridView.setAdapter(gridViewAdapter);
        }
    }

    public List<Product> getProductList() {
        //pseudo code to get product, replace your code to get real product here
        productList = new ArrayList<>();
        productList.add(new Product(R.drawable.filluprecord, "Fill-Up Rec.", "This is description 1"));
        productList.add(new Product(R.drawable.car_service, "Service Rec.", "This is description 2"));
        productList.add(new Product(R.drawable.expense, "Expense Rec.", "This is description 3"));
        productList.add(new Product(R.drawable.trip, "Trip Rec.", "This is description 4"));
        productList.add(new Product(R.drawable.adding_car, "Vehicle", "This is description 5"));
        productList.add(new Product(R.drawable.faq, "FAQ", "This is description 6"));
        productList.add(new Product(R.drawable.browse, "Browse Rec.", "This is description 7"));
        productList.add(new Product(R.drawable.statistics, "Statistics", "This is description 8"));
        productList.add(new Product(R.drawable.chart, "Charts", "This is description 9"));
        productList.add(new Product(R.drawable.details, "Vehicle Info.", "This is description 10"));
        productList.add(new Product(R.drawable.about, "Predictions", "This is description 10"));
        productList.add(new Product(R.drawable.parts, "Vehicle Parts", "This is description 10"));

        return productList;
    }

    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String value = productList.get(position).getTitle();
            //    Do any thing when user click to item
            if(car_name == null) {
                Toast.makeText(Index.this, "Please add a vehicle first", Toast.LENGTH_SHORT).show();
                if (value.equals("Vehicle")) {
                    Intent i = new Intent(Index.this, addVehicle.class);
                    i.putExtra("name", value);
                    startActivityForResult(i, 123);
                }
            } else {
                if (value.equals("Vehicle")) {
                    Intent i = new Intent(Index.this, addVehicle.class);
                    i.putExtra("name", value);
                    startActivityForResult(i, 123);
                } else if (value.equals("Fill-Up Rec.")) {
                    Intent i = new Intent(Index.this, FillUpRecord.class);
                    i.putExtra("name", value);
                    startActivityForResult(i, 123);
                } else if (value.equals("Service Rec.")) {
                    Intent i = new Intent(Index.this, ServiceRecords.class);
                    i.putExtra("name", value);
                    startActivityForResult(i, 123);
                } else if (value.equals("Expense Rec.")) {
                    Intent i = new Intent(Index.this, ExpenseRecord.class);
                    i.putExtra("name", value);
                    startActivityForResult(i, 123);
                } else if (value.equals("FAQ")) {
                    Intent i = new Intent(Index.this, FAQ.class);
                    startActivity(i);
                } else if (value.equals("Trip Rec.")) {
                    Intent i = new Intent(Index.this, TripRecord.class);
                    i.putExtra("name", value);
                    startActivityForResult(i, 123);
                } else if (value.equals("Vehicle Parts")) {
                    Intent i = new Intent(Index.this, VehicleParts.class);
                    i.putExtra("name", value);
                    startActivityForResult(i, 123);
                } else if (value.equals("Vehicle Info.")) {
                    Intent i = new Intent(Index.this, VehicleDetails.class);
                    i.putExtra("name", value);
                    i.putExtra("car_name", car_name);
                    startActivityForResult(i, 123);
                } else if (value.equals("Browse Rec.")) {
                    Intent i = new Intent(Index.this, BrowseRecords.class);
                    i.putExtra("name", value);
                    i.putExtra("car_name", car_name);
                    startActivityForResult(i, 123);
                } else if (value.equals("Statistics")) {
                    Intent i = new Intent(Index.this, statictics.class);
                    i.putExtra("name", value);
                    i.putExtra("car_name", car_name);
                    startActivityForResult(i, 123);
                } else if (value.equals("Predictions")) {
                    Intent i = new Intent(Index.this, Predictions.class);
                    i.putExtra("name", value);
                    i.putExtra("car_name", car_name);
                    startActivityForResult(i, 123);
                }
                else if (value.equals("Charts")) {
                Intent i = new Intent(Index.this,Charts.class);
                i.putExtra("name", value);
                i.putExtra("car_name", car_name);
                startActivityForResult(i, 123);
            }
            }

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_menu_1:
                if (VIEW_MODE_LISTVIEW == currentViewMode) {
                    currentViewMode = VIEW_MODE_GRIDVIEW;
                } else {
                    currentViewMode = VIEW_MODE_LISTVIEW;
                }
                //Switch view
                switchView();
                //Save view mode in share reference
                SharedPreferences sharedPreferences = getSharedPreferences("ViewMode", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("currentViewMode", currentViewMode);
                editor.commit();

                break;
            case R.id.item_menu_2:
                Intent i = new Intent(Index.this, about.class);
                startActivity(i);
            case R.id.item_menu_0:
                finish();
        }
        return true;
    }

}