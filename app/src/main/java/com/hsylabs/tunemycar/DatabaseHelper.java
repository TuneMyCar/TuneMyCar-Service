package com.hsylabs.tunemycar;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
    private boolean checkDatabase()
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
    public void copyDatabase()throws IOException
    {
        //opening local db as input stream
        InputStream myInput=mycontext.getAssets().open(DB_NAME);
        //path to the just created empty database
        String outFileNme=DB_PATH+DB_NAME;
        //Open the empty db as output stream
        OutputStream myOutput=new FileOutputStream(outFileNme);
        //transfer bytes from the input file to the output file
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Closing the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
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
        contentValues.put("Purchase_POdometer",po);
        contentValues.put("Purchase_Date",pd);
        contentValues.put("Selling_Price",sp);
        contentValues.put("Selling_Odometer",so);
        contentValues.put("Selling_Date",sd);
        contentValues.put("Notes", n);
        this.getWritableDatabase().insertOrThrow("addVehicle", "", contentValues);
    }
}
