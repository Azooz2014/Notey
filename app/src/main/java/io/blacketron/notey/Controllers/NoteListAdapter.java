package io.blacketron.notey.Controllers;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.blacketron.notey.Models.Note;
import io.blacketron.notey.R;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListViewHolder> {

    private NoteListViewHolder mNoteListViewHolder;

    private List<Note> mNotes;

    public NoteListAdapter(List<Note> notes){

        this.mNotes = notes;
    }

    @NonNull
    @Override
    public NoteListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_note_item, viewGroup, false);

        mNoteListViewHolder = new NoteListViewHolder(view);

        return mNoteListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteListViewHolder noteListViewHolder, int i) {

        Note note = mNotes.get(i);

        noteListViewHolder.bind(note);
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }
}
