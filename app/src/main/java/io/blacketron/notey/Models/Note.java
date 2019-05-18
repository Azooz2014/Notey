package io.blacketron.notey.Models;

import java.util.UUID;

public class Note {

    private UUID mId;
    private String mTitle;
    private String mNotes;
    //Later feature to be added is to cross out the note when "Done" checkbox is checked.
    //private boolean isDone;

    public Note(){

        mId = UUID.randomUUID();
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getNotes() {
        return mNotes;
    }

    public void setNotes(String notes) {
        mNotes = notes;
    }

    public UUID getId() {
        return mId;
    }
}
