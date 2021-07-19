package com.example.myproject;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DBhelper extends SQLiteOpenHelper {
    public static final String DBNAME="Canteen.db";
    public DBhelper(Context context){
        super(context,"Canteen.db",null,2);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("Create table if not exists Users(firstName TEXT,lastName TEXT,email TEXT primary key,password TEXT,phoneNo TEXT)");
        MyDB.execSQL("Create table if not exists Orders(orderId INTEGER  ,email TEXT,itemName TEXT,itemPrice TEXT,itemQuantity TEXT)");
        MyDB.execSQL("Create table if not exists finalOrders(orderno INT ,time TEXT,date TEXT,email TEXT,foodname TEXT,orderamt TEXT,orderQuantity TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("Drop table if exists Users");
        MyDB.execSQL("Drop table if exists Orders");
        MyDB.execSQL("Drop table if exists finalOrders");
        onCreate(MyDB);
    }
    public boolean insertData(String firstName,String lastName,String email,String password,String phoneNo)
    {
        Log.d("checking","sign up data added successfully");

        SQLiteDatabase  MyDB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("firstName",firstName);
        contentValues.put("lastName",lastName);
        contentValues.put("email",email);
        contentValues.put("password",password);
        contentValues.put("phoneNo",phoneNo);
        long result=MyDB.insert("Users",null,contentValues);
        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public boolean checkEmail(String email){
        SQLiteDatabase MyDB=this.getWritableDatabase();
        Cursor cursor=MyDB.rawQuery("Select * from Users where email=? ",new String[]{email});
        if(cursor.getCount()>0){
            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean checkEmailPassword(String email,String password)
    {
        SQLiteDatabase MyDB=this.getWritableDatabase();
        Cursor cursor=MyDB.rawQuery("Select * from Users where email=? and password=?",new String[]{email,password});
        if(cursor.getCount()>0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public Cursor getData(String email)
    {
        SQLiteDatabase MyDB=this.getWritableDatabase();
        Cursor cursor=MyDB.rawQuery("Select * from Users where email=?",new String[]{email});
        return cursor;
    }




}
