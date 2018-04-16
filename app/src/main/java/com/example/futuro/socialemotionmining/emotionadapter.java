package com.example.futuro.socialemotionmining;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


public class emotionadapter extends SQLiteOpenHelper{
    public SQLiteDatabase db;

    //static final String DATABASE_NAME = "Db_Addcompany.db";
    static final String DATABASE_NAME = "application.db";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;
    private static final String TABLE_USER = "tblemotion";
    private static final String COLUMN_APP_ID = "app_id";
    private static final String COLUMN_APP_NAME = "app_name";
    private static final String COLUMN_APP_COM = "comment";
    private static final String COLUMN_APP_EMO = "emotion";
    private static final String COLUMN_APP_SCORE = "score";
        private static final String COLUMN_USER_NAME = "uname";
    private static final String COLUMN_DATE = "date";
    //  static final String DATABASE_CREATE = "create table " + "LOGINN" + "( " + "ID" + " integer primary key autoincrement," + "USERNAME  text,PASSWORD text,VEHICLENO text,PHONENO text); ";
    // static final String DATABASE_CREATE = "create table " + "login" + "( " + "ID" + "  INTEGER DEFAULT 100," + "USERNAME  text,PASSWORD text,VEHICLENO text,PHONENO text); ";
  public static String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + "("
            + COLUMN_APP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_APP_NAME + " TEXT,"+ COLUMN_APP_COM + " VARCHAR,"+ COLUMN_APP_EMO+ " TEXT,"
          + COLUMN_APP_SCORE + " VARCHAR," + COLUMN_USER_NAME + " VARCHAR,"+COLUMN_DATE + " VARCHAR "+")";

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
    public static final String USER_NAME = "USERNAME";
    private static  final String TABLE_NAME="login";

    String result,vhclereg,phoneno,lastid,id;
    String selectQueryphone="SELECT PHONENO FROM login";
    String selectQueryuser = "SELECT USERNAME FROM login";
    String selectQueryvehicle = "SELECT VEHICLENO FROM login";
    //String selectid="SELECT COUNT(SCORE) FROM tblemo WHERE ";

    String selectlastid="SELECT LAST_INSERT_ROWID()";

    public emotionadapter(Context context) {
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


    public String fetchcount(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(CREATE_USER_TABLE);
        Cursor c = db.rawQuery(
                "SELECT SUM(score) FROM " + TABLE_USER + " WHERE "
                        + COLUMN_APP_NAME + "='" + username + "'", null);
        if (c != null) {
            c.moveToFirst();

            do {
                id = c.getString(0);
            } while (c.moveToNext());
        }
        return id;
    }


    public void insertEntry( String uName, String com, String emo, String score,String name, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(CREATE_USER_TABLE);
        ContentValues newValues = new ContentValues();
        newValues.put(COLUMN_APP_NAME, uName);
        newValues.put(COLUMN_APP_COM, com);
        newValues.put(COLUMN_APP_EMO, emo);
        newValues.put(COLUMN_APP_SCORE, score);
        newValues.put(COLUMN_USER_NAME,name);
        newValues.put(COLUMN_DATE,date);

        db.insert(TABLE_USER ,null, newValues);
db.close();
    }



}




