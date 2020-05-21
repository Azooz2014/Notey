package io.blacketron.notey.Controllers;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.blacketron.notey.Models.Note;
import io.blacketron.notey.Models.NotesStorage;
import io.blacketron.notey.R;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListViewHolder>{

    private List<Note> mNotes;
    private Note mNote;
    private NoteListFragment mNoteListFragment;

    public NoteListAdapter(List<Note> notes){

        this.mNotes = notes;
    }

    public NoteListAdapter(NoteListFragment noteListFragment, List<Note> notes){

        this.mNotes = notes;
        this.mNoteListFragment = noteListFragment;
    }

    @NonNull
    @Override
    public NoteListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_note_item, viewGroup, false);

        return new NoteListViewHolder(view,mNoteListFragment);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteListViewHolder noteListViewHolder, int i) {

        mNote = mNotes.get(i);

        noteListViewHolder.bind(mNote);
    }

    public void deleteNote(View view, Note note){

       NotesStorage notesStorage = NotesStorage.get(view.getContext());

       notesStorage.deleteNote(note);

       setNotes(notesStorage.getNotes());

       notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public void setNotes (List<Note> notes){
        mNotes = notes;
    }

    public Note getNote() {
        return mNote;
    }
}
