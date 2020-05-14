package io.blacketron.notey.Controllers;

import android.content.Intent;
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

    public static final int ADAPTER_REQUEST_CODE = 1;

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

        return new NoteListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NoteListViewHolder noteListViewHolder, int i) {

        mNote = mNotes.get(i);

        noteListViewHolder.bind(mNote);

        noteListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = NoteActivity.newIntent(noteListViewHolder.itemView.getContext(), mNote.getId());

                mNoteListFragment.startActivityForResult(intent, ADAPTER_REQUEST_CODE);
            }
        });
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
