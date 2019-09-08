package io.blacketron.notey.Controllers;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;

import java.util.List;

import io.blacketron.notey.Models.Note;
import io.blacketron.notey.R;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListViewHolder> {

    private NoteListViewHolder mNoteListViewHolder;

    private List<Note> mNotes;
    private SwipeLayout mSwipeLayout;
    private ImageButton mDeleteButton;

    public NoteListAdapter(List<Note> notes){

        this.mNotes = notes;
    }

    @NonNull
    @Override
    public NoteListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_note_item, viewGroup, false);

        mNoteListViewHolder = new NoteListViewHolder(view);

        mDeleteButton = view.findViewById(R.id.trash);

        mSwipeLayout = view.findViewById(R.id.swipe_layout);
        mSwipeLayout.addDrag(SwipeLayout.DragEdge.Right, view.findViewById(R.id.trash));
        mSwipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        return mNoteListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteListViewHolder noteListViewHolder, int i) {

        Note note = mNotes.get(i);

        noteListViewHolder.bind(note);

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(v.getContext(), "Delete Button Clicked!", Toast.LENGTH_SHORT).show();

                Log.d("Delete Button","Delete Button Clicked!");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }
}
