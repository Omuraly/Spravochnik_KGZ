package com.example.spravochnik_kgz;


import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * db_version 1.0.0
 */

public class DB {

    private static final String DB_NAME = "mydb";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "mytab";
    private static final String DB_TABLE_EN = "mytaben";
    private static final String DB_TABLE_RU = "mytabru";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESC = "desc";

    private static final String DB_CREATE =
            "create table " + DB_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_NAME + " text, " +
                    COLUMN_DESC + " text" +
                    ");";

    private static final String DB_CREATE_EN =
            "create table " + DB_TABLE_EN + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_NAME + " text, " +
                    COLUMN_DESC + " text" +
                    ");";

    private static final String DB_CREATE_RU =
            "create table " + DB_TABLE_RU + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_NAME + " text, " +
                    COLUMN_DESC + " text" +
                    ");";

    private final Context mCtx;
    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    public DB(Context ctx) {
        mCtx = ctx;
    }

    // открыть подключение
    public void open() {
        mDBHelper = new DBHelper(mCtx);
        mDB = mDBHelper.getWritableDatabase();
    }

    // закрыть подключение
    public void close() {
        if (mDBHelper != null) mDBHelper.close();
    }

    // получить все данные из таблицы DB_TABLE
    public Cursor getAllData() {
        return mDB.query(DB_TABLE, null, null, null, null, null, null);
    }


    public  Cursor getSepficItem(String letter){
        return  mDB.query(true, DB_TABLE, new String[] { COLUMN_ID, COLUMN_NAME },
                COLUMN_NAME + " LIKE ?",
                new String[] { letter+"%" }, null, null, null,
                null);
    }

    public  Cursor getSepficItemRU(String letter){
        return  mDB.query(true, DB_TABLE_RU, new String[] { COLUMN_ID, COLUMN_NAME },
                COLUMN_NAME + " LIKE ?",
                new String[] { letter+"%" }, null, null, null,
                null);
    }

    public  Cursor getSepficItemEn(String letter){
        return  mDB.query(true, DB_TABLE_EN, new String[] { COLUMN_ID, COLUMN_NAME },
                COLUMN_NAME + " LIKE ?",
                new String[] { letter+"%" }, null, null, null,
                null);
    }
    // добавить запись в DB_TABLE
    public void addRec(String txt, String desc) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, txt);
        cv.put(COLUMN_DESC, desc);
        mDB.insert(DB_TABLE, null, cv);
    }


    // удалить запись из DB_TABLE
    public void delRec(long id) {
        mDB.delete(DB_TABLE, COLUMN_ID + " = " + id, null);
    }

    public Cursor getDetailItem(String descText) {
        return  mDB.query(true, DB_TABLE, new String[] { COLUMN_ID, COLUMN_DESC},
                COLUMN_NAME + " LIKE ?",
                new String[] { descText }, null, null, null,
                null);
    }

    public Cursor getDetailItemRU(String descText) {
        return  mDB.query(true, DB_TABLE_RU, new String[] { COLUMN_ID, COLUMN_DESC},
                COLUMN_NAME + " LIKE ?",
                new String[] { descText }, null, null, null,
                null);
    }

    public Cursor getDetailItemEn(String descText) {
        return  mDB.query(true, DB_TABLE_EN, new String[] { COLUMN_ID, COLUMN_DESC},
                COLUMN_NAME + " LIKE ?",
                new String[] { descText }, null, null, null,
                null);
    }


    public Cursor getAllItemKG() {
        return mDB.query
                (DB_TABLE, new String[]{COLUMN_ID, COLUMN_NAME},null, null, null, null, null);
    }

    public Cursor getAllItemRU() {
        return mDB.query
                (DB_TABLE_RU, new String[]{COLUMN_ID, COLUMN_NAME},null,null,null,null,null);
    }

    public Cursor getDetailItemEn() {
        return mDB.query
                (DB_TABLE_EN, new String[]{COLUMN_ID, COLUMN_NAME},null,null,null,null,null);
    }

    // класс по созданию и управлению БД
    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "myDB", null, 1);
        }

        // создаем и заполняем БД
        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(DB_CREATE);
            db.execSQL(DB_CREATE_EN);
            db.execSQL(DB_CREATE_RU);

            String mCSVfile = "book1.csv";
            String mCSVfileRU = "book1ru.csv";
            String mCSVfileEn = "book1en.csv";
            AssetManager manager = mCtx.getAssets();

            //заполнение БД КР
            InputStream inStream = null;
            try {
                inStream = manager.open(mCSVfile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            BufferedReader buffer = new BufferedReader(new InputStreamReader(inStream));

            String line = "";
            db.beginTransaction();
            try {
                while ((line = buffer.readLine()) != null) {
                    Log.d("CSVParser", "READING!!!!!!! " + line);
                    String[] colums = line.split(";");
                    if (colums.length != 2) {
                        Log.d("CSVParser", "Skipping Bad CSV Row " + colums.length);
                        continue;
                    }

                    Log.d("CSVParser", "1st COLUM " + colums[0]);
                    Log.d("CSVParser", "2nd COLUM " + colums[1]);

                    ContentValues cv = new ContentValues();
                    cv.put(COLUMN_NAME, colums[0].trim());
                    cv.put(COLUMN_DESC, colums[1].trim());
                    db.insert(DB_TABLE, null, cv);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            db.setTransactionSuccessful();
            db.endTransaction();

            // Создание и заполнение БД РУ
            InputStream inStreamRU = null;
            try {
                inStreamRU = manager.open(mCSVfileRU);
            } catch (IOException e) {
                e.printStackTrace();
            }


            BufferedReader buffer3 = new BufferedReader(new InputStreamReader(inStreamRU));

            String line3 = "";
            db.beginTransaction();
            try {
                while ((line3 = buffer3.readLine()) != null) {
                    Log.d("CSVParser", "READING!!!!!!! " + line);
                    String[] colums = line3.split(";");
                    if (colums.length != 2) {
                        Log.d("CSVParser", "Skipping Bad CSV Row " + colums.length);
                        continue;
                    }

                    Log.d("CSVParser", "1st COLUM " + colums[0]);
                    Log.d("CSVParser", "2nd COLUM " + colums[1]);

                    ContentValues cv = new ContentValues();
                    cv.put(COLUMN_NAME, colums[0].trim());
                    cv.put(COLUMN_DESC, colums[1].trim());
                    db.insert(DB_TABLE_RU, null, cv);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            db.setTransactionSuccessful();
            db.endTransaction();

            //Создание и заполнение БД АНГЛ.
            InputStream inStreamEn = null;
            try {
                inStreamEn = manager.open(mCSVfileEn);
            } catch (IOException e) {
                e.printStackTrace();
            }

            BufferedReader buffer2 = new BufferedReader(new InputStreamReader(inStreamEn));

            String line2 = "";
            db.beginTransaction();
            try {
                while ((line2 = buffer2.readLine()) != null) {
                    Log.d("CSVParser", "READING!!!!!!! " + line);
                    String[] colums = line2.split(";");
                    if (colums.length != 2) {
                        Log.d("CSVParser", "Skipping Bad CSV Row " + colums.length);
                        continue;
                    }

                    Log.d("CSVParser", "1st COLUM " + colums[0]);
                    Log.d("CSVParser", "2nd COLUM " + colums[1]);

                    ContentValues cv = new ContentValues();
                    cv.put(COLUMN_NAME, colums[0].trim());
                    cv.put(COLUMN_DESC, colums[1].trim());
                    db.insert(DB_TABLE_EN, null, cv);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            db.setTransactionSuccessful();
            db.endTransaction();





        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}