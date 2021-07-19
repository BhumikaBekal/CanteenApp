package com.example.myproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class finalDBhelper extends SQLiteOpenHelper {
    public finalDBhelper(Context context ) {
        super(context,"Canteen.db",null,2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table finalOrders(orderno INT ,time TEXT,date TEXT,email TEXT,foodname TEXT,orderamt TEXT,orderQuantity TEXT)");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists finalOrders");

    }
    public boolean insertData(Integer orderno,String time,String date,String email,String foodname,String orderamt,String orderQuantity)
    {
        Log.d("msg","entered insert atleast");
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("orderno",orderno);
        contentValues.put("time",time);
        contentValues.put("date",date);
        contentValues.put("email",email);
        contentValues.put("foodname",foodname);
        contentValues.put("orderamt",orderamt);
        contentValues.put("orderQuantity",orderQuantity);
        long result=db.insert("finalOrders",null,contentValues);
        Log.d("msg","you made it till here");
        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }



    }
    public String getTotal(String email)
    {
        float total=0;
        SQLiteDatabase finalDBhelper=this.getWritableDatabase();
        Cursor cursor5=finalDBhelper.rawQuery("Select orderamt from finalOrders where email=?",new String[]{email});
        while(cursor5.moveToNext())
        {
            String p=cursor5.getString(0);
            Float n=Float.parseFloat(p);
            total=total+n;

        }
        return String.valueOf(total);
    }
}
