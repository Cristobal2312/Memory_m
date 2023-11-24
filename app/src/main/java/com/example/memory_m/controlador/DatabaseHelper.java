package com.example.memory_m.controlador;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.memory_m.modelo.Alarm;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "Alarm_Manager";
    private static final String TABLE_NAME = "Alarm";

    private static final String COLUMN_ALARM_ID = "Alarm_id";
    private static final String COLUMN_ALARM_HOUR = "Alarm_hour";
    private static final String COLUMN_ALARM_MINUTE = "Alarm_minute";
    private static final String COLUMN_ALARM_STATUS = "Alarm_status";
    private static final String COLUMN_ALARM_NAME = "Alarm_name";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String script = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ALARM_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_ALARM_HOUR + " INTEGER,"
                + COLUMN_ALARM_MINUTE + " INTEGER,"
                + COLUMN_ALARM_STATUS + " BOOLEAN,"
                + COLUMN_ALARM_NAME + " STRING"
                + ")";
        db.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addAlarm(Alarm alarm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ALARM_HOUR, alarm.getHour());
        values.put(COLUMN_ALARM_MINUTE, alarm.getMinute());
        values.put(COLUMN_ALARM_STATUS, alarm.getStatus());
        values.put(COLUMN_ALARM_NAME, alarm.getName());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<Alarm> getAllAlarms() {
        List<Alarm> alarmList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Alarm alarm = new Alarm();
                alarm.setId(cursor.getInt(0));
                alarm.setHour(cursor.getInt(1));
                alarm.setMinute(cursor.getInt(2));
                alarm.setStatus(cursor.getInt(3) != 0);
                alarm.setName(cursor.getString(4));
                alarmList.add(alarm);

            } while (cursor.moveToNext());
        }

        cursor.close();
        return alarmList;
    }

    public int updateAlarm(Alarm alarm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ALARM_HOUR, alarm.getHour());
        values.put(COLUMN_ALARM_MINUTE, alarm.getMinute());
        values.put(COLUMN_ALARM_STATUS, alarm.getStatus());
        values.put(COLUMN_ALARM_NAME, alarm.getName());
        return db.update(TABLE_NAME, values, COLUMN_ALARM_ID + "=?",
                new String[]{String.valueOf(alarm.getId())});
    }

    public void deleteAlarm(Alarm alarm) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ALARM_ID + " = ?", new String[]{String.valueOf(alarm.getId())});
        db.close();
    }
}
