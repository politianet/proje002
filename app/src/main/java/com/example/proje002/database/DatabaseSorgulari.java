package com.example.proje002.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import com.example.proje002.model.Arac;
import com.example.proje002.model.Bakim;
import com.example.proje002.util.Config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DatabaseSorgulari {
    private Context context;

    public DatabaseSorgulari(Context context) {
        this.context = context;
    }

    // ARAC

    public long ekleArac(Arac arac) {
        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_PILAKA, arac.getPilaka());
        contentValues.put(Config.COLUMN_MARKA, arac.getMarka());
        try {
            id = sqLiteDatabase.insertOrThrow(Config.TABLE_ARAC, null, contentValues);
        } catch (SQLException e) {
            Toast.makeText(context, "Araç Eklenemedi: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }
        return id;
    }

    public List<Arac> getAllArac() {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(Config.TABLE_ARAC, null,
                    null, null, null, null, null, null);
            if (cursor != null)
                if (cursor.moveToFirst()) {
                    List<Arac> aracList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_ARAC_ID));
                        String pilaka = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PILAKA));
                        String marka = cursor.getString(cursor.getColumnIndex(Config.COLUMN_MARKA));
                        aracList.add(new Arac(id, pilaka, marka));
                    } while (cursor.moveToNext());
                    return aracList;
                }
        } catch (Exception e) {
            Toast.makeText(context, "Araç Eklenemedi", Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null)
                cursor.close();
            sqLiteDatabase.close();
        }
        return Collections.emptyList();
    }

    public Arac getAracById(long idArac) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor cursor = null;
        Arac arac = null;
        try {
            cursor = sqLiteDatabase.query(Config.TABLE_ARAC, null,
                    Config.COLUMN_ARAC_ID + " = ? ", new String[]{String.valueOf(idArac)},
                    null, null, null);
            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_ARAC_ID));
                String pilaka = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PILAKA));
                String marka = cursor.getString(cursor.getColumnIndex(Config.COLUMN_MARKA));
                arac = new Arac(id, pilaka, marka);
            }
        } catch (Exception e) {
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null)
                cursor.close();
            sqLiteDatabase.close();
        }
        return arac;
    }

    public long guncelleAracInfo(Arac arac) {
        long rowCount = 0;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_PILAKA, arac.getPilaka());
        contentValues.put(Config.COLUMN_MARKA, arac.getMarka());
        try {
            rowCount = sqLiteDatabase.update(Config.TABLE_ARAC, contentValues,
                    Config.COLUMN_ARAC_ID + " = ? ",
                    new String[]{String.valueOf(arac.getId())});
            Log.e("ROWCOUNT", "araç güncellendi " + rowCount);
        } catch (SQLiteException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }
        return rowCount;
    }

    public long silAracById(long idArac) {
        long silRowCount = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        try {
            silRowCount = sqLiteDatabase.delete(Config.TABLE_ARAC,
                    Config.COLUMN_ARAC_ID + " = ? ",
                    new String[]{String.valueOf(idArac)});
        } catch (SQLiteException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }
        return silRowCount;
    }

    public long getAracNumarasi() {
        long count = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        try {
            count = DatabaseUtils.queryNumEntries(sqLiteDatabase, Config.TABLE_ARAC);
        } catch (SQLiteException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }
        return count;
    }
    public boolean silAllArac() {
        boolean deleteStatus = false;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        try {
            sqLiteDatabase.delete(Config.TABLE_ARAC, null, null);
            long count = DatabaseUtils.queryNumEntries(sqLiteDatabase, Config.TABLE_ARAC);
            if (count == 0)
                deleteStatus = true;
        } catch (SQLiteException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }
        return deleteStatus;
    }

    //BAKIM

    public long ekleBakim(Bakim bakim, int idArac) {
        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_BAKIM1, bakim.getBakim1());
        contentValues.put(Config.COLUMN_BAKIM2, bakim.getBakim2());
        contentValues.put(Config.COLUMN_REGISTRATION_NUMBER, idArac);
        try {
            id = sqLiteDatabase.insertOrThrow(Config.TABLE_BAKIM, null, contentValues);
        } catch (SQLiteException e) {
            Toast.makeText(context, "Bakım Eklenemedi " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }
        return id;
    }

    public List<Bakim> getAllBakimByRegNo(long registrationNo) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        List<Bakim> bakimList = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(Config.TABLE_BAKIM,
                    new String[]{Config.COLUMN_BAKIM_ID, Config.COLUMN_BAKIM1, Config.COLUMN_BAKIM2},
                    Config.COLUMN_REGISTRATION_NUMBER + " = ? ",
                    new String[]{String.valueOf(registrationNo)},
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                bakimList = new ArrayList<>();
                do {
                    int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_BAKIM_ID));
                    String bakim1 = cursor.getString(cursor.getColumnIndex(Config.COLUMN_BAKIM1));
                    String bakim2 = cursor.getString(cursor.getColumnIndex(Config.COLUMN_BAKIM2));
                    bakimList.add(new Bakim(id, bakim1, bakim2));
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if (cursor != null)
                cursor.close();
            sqLiteDatabase.close();
        }
        return bakimList;
    }

    public Bakim getBakimById(int idBakim) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor cursor = null;
        Bakim bakim = null;
        try {
            cursor = sqLiteDatabase.query(Config.TABLE_BAKIM, null,
                    Config.COLUMN_BAKIM_ID + " = ? ", new String[]{String.valueOf(idBakim)},
                    null, null, null);

            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_BAKIM_ID));
                String bakim1 = cursor.getString(cursor.getColumnIndex(Config.COLUMN_BAKIM1));
                String bakim2 = cursor.getString(cursor.getColumnIndex(Config.COLUMN_BAKIM2));
                bakim = new Bakim(id, bakim1, bakim2);
            }
        } catch (Exception e) {
            Toast.makeText(context, "Bakım Getirilemedi", Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null)
                cursor.close();
            sqLiteDatabase.close();
        }
        return bakim;
    }

    public long guncelleBakimInfo(Bakim bakim) {
        long rowCount = 0;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_BAKIM1, bakim.getBakim1());
        contentValues.put(Config.COLUMN_BAKIM2, bakim.getBakim2());
        Log.e("ROWCOUNT", "Bakım Guncellendi " + contentValues);
        try {
            rowCount = sqLiteDatabase.update(Config.TABLE_BAKIM, contentValues,
                    Config.COLUMN_BAKIM_ID + " = ? ",
                    new String[]{String.valueOf(bakim.getId())});
            Log.e("ROWCOUNT", "Bakım Güncellenemedi " + rowCount);
        } catch (SQLiteException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }
        return rowCount;
    }

    public boolean silBakimByid(long id) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        int row = sqLiteDatabase.delete(Config.TABLE_BAKIM,
                Config.COLUMN_BAKIM_ID + " = ? ", new String[]{String.valueOf(id)});
        return row > 0;
    }

    public boolean silAllBakimByRegNum(long registrationNum) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        int row = sqLiteDatabase.delete(Config.TABLE_BAKIM,
                Config.COLUMN_REGISTRATION_NUMBER + " = ? ", new String[]{String.valueOf(registrationNum)});
        return row > 0;
    }

    public long getNumberOfBakim() {
        long count = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        try {
            count = DatabaseUtils.queryNumEntries(sqLiteDatabase, Config.TABLE_BAKIM);
        } catch (SQLiteException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }
        return count;
    }


}




























