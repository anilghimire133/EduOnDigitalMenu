package com.example.ayush.digitalmenu;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.ayush.digitalmenu.ItemDetail.PojoClass;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    final Context mContext;
    static String name = "digitalmenu";
    static int version = 1;

    String createtable = "CREATE TABLE if not exists user (\n" +
            "            Id       INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "            Menu_Id  INTEGER (8),\n" +
            "            Name     VARCHAR (80),\n" +
            "            Price    VARCHAR (8),\n" +
            "            Quantity VARCHAR (3),\n" +
            "            Total    VARCHAR (8),\n" +
            "            Likes    VARCHAR (8),\n" +
            "            Image    VARCHAR (250)\n" +
            "            );";


    public Double sub_total() {

        SQLiteDatabase db = this.getWritableDatabase();
        String subtotal = "Select Sum(Total) from user";

        Cursor cursor = db.rawQuery(subtotal, null);
        cursor.moveToFirst();

        int i = cursor.getInt(0);

        return Double.valueOf(i);
    }

    String order_table = "CREATE TABLE user (\n" +
            "    Id         INTEGER  PRIMARY KEY AUTOINCREMENT,\n" +
            "    Name       VARCHAR (50),\n" +
            "    Price      VARCHAR (5),\n" +
            "    Quantity   INTEGER (3),\n" +
            "    Total      VARCHAR (5),\n" +
            "    Image      VARCHAR (100),\n" +
            "    SubTotal   VARCHAR (8),\n" +
            "    VAT        VARCHAR (8),\n" +
            "    GrandTotal VARCHAR (10) \n" +
            ");\n";

    public DatabaseHelper(Context context) {
        super(context, name, null, version);
        this.mContext = context;
        getWritableDatabase().execSQL(createtable);
//        getWritableDatabase().execSQL(order_table);
    }

    public void insertUser(ContentValues cv) {

//---------------------------------------- If same item already exits then below code works ----------------------------------------------------//

        Cursor cursor = getReadableDatabase().rawQuery("select * from user where Name=?",
                new String[]{cv.getAsString("Name")}); // ItemDetailActivity ko cv ma pathako ma getAsString le Name vanne key khojer tesko data liyera ako ho

        if (cursor.getCount() != 0) {
            //updte quantity here
            getWritableDatabase().update("user", cv, "Name= ?", new String[]{cv.getAsString("Name")});
            Toast.makeText(mContext, "Same item already exists, Updating "+cv.getAsString("Name") + " quantity as "+cv.getAsString("Quantity"), Toast.LENGTH_LONG).show();

        } else {
            //insert new item here
            getWritableDatabase().insert("user", "", cv);
        }
// -------------------------------------- Ends over here ------------------------------------------------//
    }

    public void updateUser(ContentValues cv, String Id) {

        getWritableDatabase().update("user", cv, "Id=" + Id, null);
//        getWritableDatabase().update("user", cv, "Id = ?", new String[]{Id}); // Yo use garda pani hunx
    }

    public int deleteUser(String Id) {
        return getWritableDatabase().delete("user", "Id=" + Id, null);
    }

    public void delete_table(){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from user");
    }

    public ArrayList<PojoClass> getUserList() {

        ArrayList<PojoClass> list = new ArrayList<PojoClass>();

        String sql = "select * from user";

        Cursor c = getWritableDatabase().rawQuery(sql, null);

        while (c.moveToNext()) {

            PojoClass info = new PojoClass();
            info.setId(c.getString(c.getColumnIndex("Id")));
            info.setName(c.getString(c.getColumnIndex("Name")));
            info.setItem_id(c.getString(c.getColumnIndex("Menu_Id")));
            info.setPrice(c.getString(c.getColumnIndex("Price")));
            info.setQuantity(c.getString(c.getColumnIndex("Quantity")));
            info.setTotal(c.getString(c.getColumnIndex("Total")));
            info.setLikes(c.getString(c.getColumnIndex("Likes")));
            info.setImage(c.getString(c.getColumnIndex("Image")));
            info.setSubtotal(sub_total().toString());

            list.add(info);
        }
        c.close();
        return list;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
