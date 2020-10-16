package com.example.proje002.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.proje002.util.Config;
public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper databaseHelper;
    static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = Config.DATABASE_NAME;
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseHelper getInstance(Context context) {
        if (databaseHelper == null) {
            synchronized (DatabaseHelper.class) {
                if (databaseHelper == null) {
                    databaseHelper = new DatabaseHelper(context);
                }
            }
        }
        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ARAC_TABLE = "CREATE TABLE " + Config.TABLE_ARAC + "("
                + Config.COLUMN_ARAC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_PILAKA + " TEXT NOT NULL, "
                + Config.COLUMN_MARKA + " TEXT NOT NULL "
                + ")";
        String CREATE_BAKIM_TABLE = "CREATE TABLE " + Config.TABLE_BAKIM + "("
                + Config.COLUMN_BAKIM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_REGISTRATION_NUMBER + " INTEGER NOT NULL, "
                + Config.COLUMN_BAKIM1 + " TEXT, "
                + Config.COLUMN_BAKIM2 + " TEXT, "
                + "FOREIGN KEY (" + Config.COLUMN_REGISTRATION_NUMBER + ") REFERENCES " + Config.TABLE_ARAC + "(" + Config.COLUMN_ARAC_ID + ") ON UPDATE CASCADE ON DELETE CASCADE "
                + ")";

        db.execSQL(CREATE_ARAC_TABLE);
        db.execSQL(CREATE_BAKIM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_ARAC);
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_BAKIM);
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        //db.execSQL("PRAGMA foreign_keys=ON;");
    }

}















