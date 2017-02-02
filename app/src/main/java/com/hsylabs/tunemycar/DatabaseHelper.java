package com.hsylabs.tunemycar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.hsylabs.tunemycar.model.Browse;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omer Bashir Jamal on 2/1/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper
{
    //Androids default system path to find the databse
    private static String DB_PATH = "/data/data/com.hsylabs.tunemycar/databases/";
    public static String DB_NAME="myCarDB.sqlite";
    private SQLiteDatabase myDataBase;
    private final Context mycontext;
    //Constructor to take and keep the records f Android Application
    public DatabaseHelper(Context context)
    {
        super(context, DB_NAME, null, 1);
        this.mycontext = context;

        if(checkDatabase()) {
            openDatabase();
        } else {
            try {
                this.getReadableDatabase();
                copyDatabase();
                this.close();
                openDatabase();
            } catch (IOException e) {
                throw new Error("Error while copying...");
            }
            Toast.makeText(context, "Initial DB connected", Toast.LENGTH_LONG).show();
        }
    }

    public void createDatabase()throws IOException
    {
        boolean dbExist=checkDatabase();
        if(dbExist)
        {
            //do nothing database already exist
        }
        else
        {
            this.getReadableDatabase();
            try {
                copyDatabase();
            }catch (IOException e)
            {
                throw new Error("Error Copying Database...!!");
            }
        }
    }
    public boolean checkDatabase()
    {
        SQLiteDatabase checkDB=null;
        try{
            String myPath=DB_PATH + DB_NAME;
            checkDB=SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
        }catch (SQLException e)
        {
            e.printStackTrace();
            Log.d("Omer","Error in check Database");
        }
        if(checkDB!=null)
        {
            checkDB.close();
        }
        return checkDB !=null ? true:false;
    }
    //Copying Database from asset folder to your app;
    public boolean copyDatabase()throws IOException
    {
        try {
            //opening local db as input stream
            InputStream myInput=mycontext.getAssets().open(DB_NAME);
            //path to the just created empty database
            String outFileNme=DB_PATH+DB_NAME;
            //Open the empty db as output stream
            OutputStream myOutput=new FileOutputStream(outFileNme);
            //transfer bytes from the input file to the output file
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = myInput.read(buffer))>0){
                myOutput.write(buffer, 0, length);
            }
            Log.d("Omer", "IN DB");

            //Closing the streams
            myOutput.flush();
            myOutput.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
    public void openDatabase()throws SQLException
    {
        String myPath=DB_PATH+DB_NAME;
        myDataBase=SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
    }
    @Override
    public synchronized void close()
    {
        if(myDataBase!=null)
        {
            myDataBase.close();
        }
        super.close();
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void insert_vehicle(String name, String make, String model, String year, String lp, String vin, String ins,String tc,String pp,String po,String pd,String sp,String so,String sd,String n) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Vehicle_Name", name);
        contentValues.put("Make", make);
        contentValues.put("Model", model);
        contentValues.put("Year", year);
        contentValues.put("License_Plate", lp);
        contentValues.put("VIN",vin);
        contentValues.put("Ins_Policy",ins);
        contentValues.put("Tank_Capacity", tc);
        contentValues.put("Purchase_Price",pp);
        contentValues.put("Purchase_Odometer",po);
        contentValues.put("Purchase_Date",pd);
        contentValues.put("Selling_Price",sp);
        contentValues.put("Selling_Odometer",so);
        contentValues.put("Selling_Date",sd);
        contentValues.put("Notes", n);
        this.getWritableDatabase().insertOrThrow("add_vehicle", "", contentValues);
    }

    public ArrayList<String> getvehiclename()
    {
        openDatabase();
        ArrayList<String>list=new ArrayList<String>();
        myDataBase=this.getReadableDatabase();
        Cursor cursor = myDataBase.rawQuery("SELECT * From add_vehicle", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            String name=cursor.getString(cursor.getColumnIndex("Vehicle_Name"));
            list.add(name);
            cursor.moveToNext();
        }
        cursor.close();
        myDataBase.close();
        return list;

    }
    public String getPlate(String a)
    {
        String place=null;
        openDatabase();
        Cursor cursor = myDataBase.rawQuery("SELECT * From add_vehicle where Vehicle_Name ='"+a+"'", null);
        cursor.moveToFirst();

        if (cursor.moveToFirst()){
            place = cursor.getString( cursor.getColumnIndex("License_Plate"));
        }
        myDataBase.close();
        return place;
    }
    public void fill_up_record(String aa,String lp, String make, String vname, String model, String year, String tank, String notes,String odometer,String a)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Odometer",aa);
        contentValues.put("Price",lp);
        contentValues.put("Volume",make);
        contentValues.put("Total_Cost",vname);
        contentValues.put("Vehicle_Name",model);
        contentValues.put("Date_Time",year);
        contentValues.put("Payment_Type",tank);
        contentValues.put("Filling_Center_Name",notes);
        contentValues.put("Notes",odometer);
        contentValues.put("License_Plate",a);

        this.getWritableDatabase().insertOrThrow("fillup_record", "", contentValues);
    }
    public void service_record(String a,String b,String c,String d,String e,String f,String g,String h,String i)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Odometer",a);
        contentValues.put("Services",b);
        contentValues.put("Total_Cost",c);
        contentValues.put("Vehicle_Name",d);
        contentValues.put("Date_Time",e);
        contentValues.put("Payment_Type",f);
        contentValues.put("Service_Center_Name",g);
        contentValues.put("Notes",h);
        contentValues.put("License_Plate",i);
        this.getWritableDatabase().insertOrThrow("service_record", "", contentValues);
    }

    public void expense_record(String a,String b,String c,String d,String e,String f,String g,String h,String i)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Odometer",a);
        contentValues.put("Expense_Name",b);
        contentValues.put("Total_Cost",c);
        contentValues.put("Vehicle_Name",d);
        contentValues.put("Date_Time",e);
        contentValues.put("Payment_Type",f);
        contentValues.put("Expense_Center_Name",g);
        contentValues.put("Notes",h);
        contentValues.put("License_Plate",i);
        this.getWritableDatabase().insertOrThrow("expense_record", "", contentValues);
    }
    public void trip_record(String a,String b,String c,String d, String dep, String depodo, String deploc,String arr, String arrodo, String arrloc, String arrtax, String notes1, String lp)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Type",a);
        contentValues.put("Purpose",b);
        contentValues.put("Client",c);
        contentValues.put("Vehicle",d);
        contentValues.put("Departure_Date_Time", dep);
        contentValues.put("Departure_Odometer", depodo);
        contentValues.put("Departure_Location",deploc);
        contentValues.put("Arrival_Date_Time",arr);
        contentValues.put("Arrival_Odometer",arrodo);
        contentValues.put("Arrival_Location",arrloc);
        contentValues.put("Tax_Deduction_Amount",arrtax);
        contentValues.put("Notes",notes1);
        contentValues.put("License_Plate",lp);
        this.getWritableDatabase().insertOrThrow("Trip_Record", "", contentValues);
    }
    public String[] getVehicleDetail(String a)
    {
        String sd;
        openDatabase();
        Cursor cursor = myDataBase.rawQuery("SELECT * From add_vehicle WHERE Vehicle_Name='"+a+"'", null);
        cursor.moveToFirst();
        String[] array = new String[15];
        for (int index = 0; index < array.length; index++)
        {
            sd = cursor.getString(index).toString();
            array[index] = sd;
        }
        cursor.moveToNext();
        cursor.close();
        return array;
    }
    public List<Browse> getBrowse(String a) {
        Browse browse = null;
        List<Browse> tablesList = new ArrayList<>();
        openDatabase();
        Log.d("DSADSA", a);
        Cursor cursor = myDataBase.rawQuery("SELECT * FROM fillup_record WHERE Vehicle_Name='"+a+"'", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            browse = new Browse(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            tablesList.add(browse);
            cursor.moveToNext();
        }
        cursor.close();
        myDataBase.close();
        return tablesList;
    }

    public Double getCost(String a) {
        openDatabase();
        Cursor cursor = myDataBase.rawQuery("SELECT sum(Total_Cost) FROM fillup_record WHERE Vehicle_Name='"+a+"'", null);
        cursor.moveToFirst();
        Double price = null;

        Log.d("DSADSA", a);
        while (!cursor.isAfterLast()) {
            price = Double.parseDouble(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        myDataBase.close();
        return price;
    }
}
