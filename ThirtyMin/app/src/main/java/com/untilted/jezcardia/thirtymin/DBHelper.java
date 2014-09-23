package com.untilted.jezcardia.thirtymin;

/**
 * Created by Jezcardia on 023 23 Sep.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "actionDB.db";
    public static final String TABLE_ACTIONS = "actions";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_POINTS = "points";
    public static final String COLUMN_TIME = "time";

    public DBHelper(Context context, String name,
                    SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " +
                TABLE_ACTIONS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME
                + " TEXT," + COLUMN_POINTS + " INTEGER" + COLUMN_TIME + " INTEGER" + ")";
        sqLiteDatabase.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIONS);
        onCreate(sqLiteDatabase);
    }

    /* CRUD methods */

    // Create
    public void addAction(Action action) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, action.getName());
        values.put(COLUMN_POINTS, action.getPoints());
        values.put(COLUMN_TIME, action.getTime());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_ACTIONS, null, values);
        db.close();
    }

    // Read
    public Action findAction(String name) {
        String query = "Select * FROM " + TABLE_ACTIONS + " WHERE " + COLUMN_NAME + " =  \"" + name + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Action action = new Action();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            action.set_id(Integer.parseInt(cursor.getString(0)));
            action.setName(cursor.getString(1));
            action.setPoints(Integer.parseInt(cursor.getString(2)));
            action.setTime(Integer.parseInt(cursor.getString(3)));
            cursor.close();
        } else {
            action = null;
        }
        db.close();
        return action;
    }

    // Read All
    public List<Action> getAllActions() {
        List<Action> actionList = new ArrayList<Action>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ACTIONS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Action action = new Action();
                action.set_id(Integer.parseInt(cursor.getString(0)));
                action.setName(cursor.getString(1));
                action.setPoints(Integer.parseInt(cursor.getString(2)));
                action.setTime(Integer.parseInt(cursor.getString(3)));

                // Adding action to list
                actionList.add(action);
            } while (cursor.moveToNext());
        }

        // return contact list
        return actionList;
    }

    // Update
    public int updateAction(Action action){
        SQLiteDatabase db = this.getReadableDatabase();

    // New value for one column
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, action.getName());
        values.put(COLUMN_POINTS, action.getPoints());
        values.put(COLUMN_TIME, action.getTime());

    // Which row to update, based on the ID
        String selection = COLUMN_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(action.get_id()) };

        int count = db.update(
                TABLE_ACTIONS,
                values,
                selection,
                selectionArgs);

        return count;
    }

    // Delete
    public boolean deleteAction(String name) {

        boolean result = false;

        String query = "Select * FROM " + TABLE_ACTIONS + " WHERE " + COLUMN_NAME + " =  \"" + name + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Action action = new Action();

        if (cursor.moveToFirst()) {
            action.set_id(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_ACTIONS, COLUMN_ID + " = ?",
                    new String[] { String.valueOf(action.get_id()) });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

}
