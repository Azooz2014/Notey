package io.blacketron.notey.Controllers;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import io.blacketron.notey.Models.Note;
import io.blacketron.notey.R;

public class NoteListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView mTodoTitle;
    private TextView mTodoNote;

    private Note mNote;

    public NoteListViewHolder(@NonNull View itemView) {
        super(itemView);

        mTodoTitle = itemView.findViewById(R.id.todo_title);
        mTodoNote = itemView.findViewById(R.id.todo_note);

        mTodoTitle.setOnClickListener(this);
        mTodoNote.setOnClickListener(this);
    }

    /*public TextView getTodoTitle() {
        return mTodoTitle;
    }

    public TextView getTodoNote() {
        return mTodoNote;
    }*/

    @Override
    public void onClick(View v) {

        Intent intent = NoteActivity.newIntent(itemView.getContext(), mNote.getId());

        itemView.getContext().startActivity(intent);
    }

    public void bind(Note note){

        this.mNote = note;

        mTodoTitle.setText(note.getTitle());
        mTodoNote.setText(note.getNotes());
    }
}
