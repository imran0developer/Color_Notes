package com.unitapplications.colornotes.data;



import static com.unitapplications.colornotes.params.Params.TABLE_NAME;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.unitapplications.colornotes.Model.Models;
import com.unitapplications.colornotes.params.Params;

import java.util.ArrayList;

public class MyDbHandler extends SQLiteOpenHelper {
    // creating a constructor for our database handler.
    public MyDbHandler(Context context) {
        super(context, Params.DB_NAME, null, Params.DB_VERSION);
    }



    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + Params.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Params.KEY_COLUM + " TEXT NOT NULL ,"
                + Params.KEY_COLUM1 + " TEXT NOT NULL,"
                + Params.KEY_DATE_TIME + " TEXT NOT NULL )";


        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }

    // this method is use to add new course to our sqlite database.
    public void addNote(Models models) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(Params.KEY_COLUM, models.getColum());
        values.put(Params.KEY_COLUM1, models.getColum1());
        values.put(Params.KEY_DATE_TIME, models.getDateTime());

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    public ArrayList<Models> getAllNotes() {
        ArrayList<Models> noteList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Generate the query to read from the database
        String select = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(select, null);

        //Loop through now
        if (cursor.moveToFirst()) {
            do {
                Models models = new Models();
                models.setId(Integer.parseInt(cursor.getString(0)));
                models.setColum(cursor.getString(1));
                models.setColum1(cursor.getString(2));
                models.setDateTime(cursor.getString(3));

                noteList.add(models);
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return noteList;
    }


    // below is the method for deleting our course.
    public void deleteNoteById(int id) {

        // on below line we are creating
        // a variable to write our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.
        db.delete(TABLE_NAME, Params.KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        Log.d("tag2", "deleted");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}