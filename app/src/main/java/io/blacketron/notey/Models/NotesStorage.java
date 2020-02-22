package io.blacketron.notey.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.blacketron.notey.Database.NoteCursorWrapper;
import io.blacketron.notey.Database.NoteDbBaseHelper;
import io.blacketron.notey.Database.NoteDbSchema;

public class NotesStorage {

    private static NotesStorage sNotesStorage;
    private SQLiteDatabase mDatabase;
    private static ContentValues sValues;

    private NotesStorage(Context context) {

        //Generating Dummy data to fill and test RecyclerView.

        /*for(int i = 1; i <= 100; i++){

            Note note = new Note();
            note.setTitle("odo #" + i);
            note.setNotes("Note #" + i);
            mNotes.add(note);

            Log.i("UUID", "Note " + i + " UUID:" + note.getId());*/

        mDatabase = new NoteDbBaseHelper(context).getWritableDatabase();
    }

    private static ContentValues getContentValues(Note note) {

        sValues = new ContentValues();

        sValues.put(NoteDbSchema.NoteTable.Columns.UUID, note.getId().toString());
        sValues.put(NoteDbSchema.NoteTable.Columns.TITLE, note.getTitle());
        sValues.put(NoteDbSchema.NoteTable.Columns.NOTES, note.getNotes());

        return sValues;
    }

    public void addNote(Note note) {

        sValues = getContentValues(note);

        mDatabase.insert(NoteDbSchema.NoteTable.NAME, null, sValues);
    }

    public void deleteNote(Note note) {

        mDatabase.delete(NoteDbSchema.NoteTable.NAME,
                NoteDbSchema.NoteTable.Columns.UUID + " = ?",
                new String[]{note.getId().toString()});

    }

    public void undoDeleteNote(){

        //TODO: find a way to re-add the last deleted row back to the DB , in the same Row index.

    }

    public static NotesStorage get(Context context) {

        if (sNotesStorage == null) {

            sNotesStorage = new NotesStorage(context);
        }

        return sNotesStorage;
    }

    public List<Note> getNotes() {

        NoteCursorWrapper cursor = queryNotes(null, null);

        List<Note> notes = new ArrayList<>();

        try {
            //moving the cursor to the first row in DB table.
            cursor.moveToFirst();

            //Iterating and add note objects while the cursor in not after the last row in DB table.
            while (!cursor.isAfterLast()) {
                notes.add(cursor.getNote());

                //Moving the cursor to the next row.
                cursor.moveToNext();
            }
        } finally {

           /*closing query after cursor is done from reading it,
            and release the resource that's holding.*/
            cursor.close();
        }
        return notes;
    }

    public Note getNote(UUID id) {

        NoteCursorWrapper cursor = queryNotes(NoteDbSchema.NoteTable.Columns.UUID + " = ?"
                , new String[]{id.toString()});

        try {

            //If there's no rows in DB table return nothing.
            if (cursor.getCount() == 0) {
                return null;
            }

            //Move the cursor to the first row.
            cursor.moveToFirst();

            //Returning note object that matches the corresponding UUID passed in the query.
            return cursor.getNote();

        } finally {

            /*closing query after cursor is done from reading it,
            and release the resource that's holding.*/
            cursor.close();
        }
    }

    public void updateNote(Note note) {

        String uuidString = note.getId().toString();

        sValues = getContentValues(note);

        mDatabase.update(NoteDbSchema.NoteTable.NAME, sValues,
                NoteDbSchema.NoteTable.Columns.UUID + " = ?", new String[]{uuidString});
    }

    //Querying database table.
    private NoteCursorWrapper queryNotes(String where, String[] whereArgs) {

        Cursor cursor = mDatabase.query(
                NoteDbSchema.NoteTable.NAME,
                null,// when columns is null it selects all the columns.
                where,/*A filter declaring which rows to return, Passing null will return all*/
                whereArgs,/*WhereArgs – You may include ?s in selection, which will be replaced
                by the values from selectionArgs, in order that they appear in the selection.
                The values will be bound as Strings.*/
                null,
                null,
                null
        );

        return new NoteCursorWrapper(cursor);
    }
}
