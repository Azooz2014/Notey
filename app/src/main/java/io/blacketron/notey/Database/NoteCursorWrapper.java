package io.blacketron.notey.Database;

import android.database.Cursor;
import android.database.CursorWrapper;
import java.util.UUID;
import io.blacketron.notey.Models.Note;

public class NoteCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public NoteCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Note getNote (){

        String uuidString = getString(getColumnIndex(NoteDbSchema.NoteTable.Columns.UUID));
        String title = getString(getColumnIndex(NoteDbSchema.NoteTable.Columns.TITLE));
        String notes = getString(getColumnIndex(NoteDbSchema.NoteTable.Columns.NOTES));

        Note note = new Note(UUID.fromString(uuidString));
        note.setTitle(title);
        note.setNotes(notes);

        return note;
    }
}
