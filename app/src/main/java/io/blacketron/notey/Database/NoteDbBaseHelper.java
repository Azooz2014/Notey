package io.blacketron.notey.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class NoteDbBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "noteBase.db";

    public NoteDbBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + NoteDbSchema.NoteTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                NoteDbSchema.NoteTable.Columns.UUID + ", " +
                NoteDbSchema.NoteTable.Columns.TITLE + ", " +
                NoteDbSchema.NoteTable.Columns.NOTES + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
