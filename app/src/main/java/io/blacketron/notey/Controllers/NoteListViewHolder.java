package io.blacketron.notey.Controllers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.card.MaterialCardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import io.blacketron.notey.Models.Note;
import io.blacketron.notey.R;

public class NoteListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public static final int VIEW_HOLDER_REQUEST_CODE = 1;

    private TextView mTodoTitle;
    private TextView mTodoNote;

    private MaterialCardView mNoteContainer;

    private Note mNote;

    private NoteListFragment mNoteListFragment;
    
    public NoteListViewHolder(@NonNull View itemView) {
        super(itemView);

        mTodoTitle = itemView.findViewById(R.id.todo_title);
        mTodoNote = itemView.findViewById(R.id.todo_note);

        mNoteContainer = itemView.findViewById(R.id.todo_container);
        /*mTodoTitle.setOnClickListener(this);
        mTodoNote.setOnClickListener(this);*/
        mNoteContainer.setOnClickListener(this);
    }

    public NoteListViewHolder(@NonNull View itemView, NoteListFragment noteListFragment) {
        super(itemView);

        this.mNoteListFragment = noteListFragment;

        mTodoTitle = itemView.findViewById(R.id.todo_title);
        mTodoNote = itemView.findViewById(R.id.todo_note);

        mNoteContainer = itemView.findViewById(R.id.todo_container);
        /*mTodoTitle.setOnClickListener(this);
        mTodoNote.setOnClickListener(this);*/
        mNoteContainer.setOnClickListener(this);
    }

    /*public TextView getTodoTitle() {
        return mTodoTitle;
    }

    public TextView getTodoNote() {
        return mTodoNote;
    }*/

    public void onClick(View v) {

        Intent intent = NoteActivity.newIntent(itemView.getContext(), mNote.getId());

        mNoteListFragment.startActivityForResult(intent, VIEW_HOLDER_REQUEST_CODE);
    }

    public void bind(Note note) {

        this.mNote = note;

        mTodoTitle.setText(note.getTitle());
        mTodoNote.setText(note.getNotes());
    }
}
