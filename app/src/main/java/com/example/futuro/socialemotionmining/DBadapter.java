package com.example.futuro.socialemotionmining;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBadapter extends SQLiteOpenHelper{
    public SQLiteDatabase db;

    static final String DATABASE_NAME = "application.db";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;
    private static final String TABLE_USER = "tblapp";
    private static final String COLUMN_APP_ID = "app_id";
    private static final String COLUMN_APP_NAME = "app_name";
    private static final String COLUMN_APP_CAT = "app_cat";
    private static final String COLUMN_APP_DESC = "app_desc";
    private static final String COLUMN_APP_ICON = "app_icon";

  public static String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + "("
            + COLUMN_APP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_APP_NAME + " TEXT,"+ COLUMN_APP_CAT + " TEXT,"+ COLUMN_APP_DESC+ " TEXT,"
          + COLUMN_APP_ICON + " TEXT" + ")";
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;






    public DBadapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);

        // Create tables again
        onCreate(db);

    }


    public void insertEntry(String uName, String cat,String desc,byte[] icon) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put(COLUMN_APP_NAME, uName);
        newValues.put(COLUMN_APP_CAT, cat);
        newValues.put(COLUMN_APP_DESC, desc);
        newValues.put(COLUMN_APP_ICON, icon);
        db.insert(TABLE_USER ,null, newValues);
db.close();
    }





    public ArrayList<String> fetchUser(String username) {
ArrayList<String >de=new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT * FROM " + TABLE_USER + " WHERE "
                        + COLUMN_APP_NAME + "='" +username+"'" ,  null);


        if (c != null) {
            c.moveToFirst();

            do {
       String appname = c.getString(1);
                String cat=c.getString(2);
                String decs=c.getString(3);
                de.add(appname);
                de.add(cat);
                de.add(decs);
            } while (c.moveToNext());
        }

    return de;
}
    public byte[] fetchImage(String username) {
        byte[] blob = new byte[0];
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT * FROM " + TABLE_USER + " WHERE "
                        + COLUMN_APP_NAME + "='" +username+"'" ,  null);



        if (c != null) {
            c.moveToFirst();

            do {

                 blob = c.getBlob(c.getColumnIndex("app_icon"));

            } while (c.moveToNext());
        }

        return blob;
    }







    public List<MyObject> read(String searchTerm) {

        List<MyObject> recordsList = new ArrayList<MyObject>();

        // select query
        String sql = "";
        sql += "SELECT * FROM " + TABLE_USER;
        sql += " WHERE " + COLUMN_APP_NAME + " LIKE '%" + searchTerm + "%'";
        sql += " ORDER BY " + COLUMN_APP_NAME + " DESC";
        sql += " LIMIT 0,5";

        SQLiteDatabase db = this.getWritableDatabase();

        // execute the query
        Cursor cursor = db.rawQuery(sql, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {


                String objectName = cursor.getString(cursor.getColumnIndex(COLUMN_APP_NAME));
                MyObject myObject = new MyObject(objectName);

                // add to list
                recordsList.add(myObject);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // return the list of records
        return recordsList;
    }



}




