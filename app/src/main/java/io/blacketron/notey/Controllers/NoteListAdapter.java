package io.blacketron.notey.Controllers;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.List;

import io.blacketron.notey.Models.Note;
import io.blacketron.notey.Models.NotesStorage;
import io.blacketron.notey.R;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListViewHolder> {

    private NoteListViewHolder mNoteListViewHolder;

    private List<Note> mNotes;
    private ImageButton mDeleteButton;
    private Note mNote;

    public NoteListAdapter(List<Note> notes) {

        this.mNotes = notes;
    }

    @NonNull
    @Override
    public NoteListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_note_item, viewGroup, false);

        mNoteListViewHolder = new NoteListViewHolder(view);

//        mDeleteButton = view.findViewById(R.id.trash);

        return mNoteListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final NoteListViewHolder noteListViewHolder, int i) {

        mNote = mNotes.get(i);

        noteListViewHolder.bind(mNote);

        /*mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteNote(v, note);

                Toast.makeText(v.getContext(), "Todo Deleted!", Toast.LENGTH_SHORT).show();

            }
        });*/
    }

    public void deleteNote(View view, Note note) {

        NotesStorage notesStorage = NotesStorage.get(view.getContext());

        notesStorage.deleteNote(note);

        setNotes(notesStorage.getNotes());

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public void setNotes(List<Note> notes) {
        mNotes = notes;
    }

    public Note getNote() {
        return mNote;
    }

}
