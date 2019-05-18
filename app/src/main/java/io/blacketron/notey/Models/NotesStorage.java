package io.blacketron.notey.Models;

import java.util.List;
import java.util.UUID;

public class NotesStorage {

    private static NotesStorage mNotesStorage;
    private List <Note> mNotes;

    public static NotesStorage get(){

        if(mNotesStorage == null){

            mNotesStorage = new NotesStorage();
        }

        return mNotesStorage;
    }

    public List<Note> getNotes(){
        return mNotes;
    }

    public Note getNote (UUID id){

        /*Iterating over a List of type Note and return the Note which matches the passed ID
        * from NoteFragment, if no id matches then return nothing.*/
        for (Note note : mNotes){

            if(note.getId().equals(id)){

                return note;
            }
        }

        return null;
    }

    private  NotesStorage(){

    }
}
