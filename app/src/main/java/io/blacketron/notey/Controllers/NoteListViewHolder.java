package io.blacketron.notey.Controllers;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import io.blacketron.notey.Models.Note;
import io.blacketron.notey.R;

public class NoteListViewHolder extends RecyclerView.ViewHolder {

    private TextView mTodoTitle;
    private TextView mTodoNote;

    public NoteListViewHolder(@NonNull View itemView) {
        super(itemView);

        mTodoTitle = itemView.findViewById(R.id.todo_title);
        mTodoNote = itemView.findViewById(R.id.todo_note);


        /*mTodoTitle.setOnClickListener(this);
        mTodoNote.setOnClickListener(this);*/
    }

    /*public TextView getTodoTitle() {
        return mTodoTitle;
    }

    public TextView getTodoNote() {
        return mTodoNote;
    }*/

    public void bind(Note note) {

        mTodoTitle.setText(note.getTitle());
        mTodoNote.setText(note.getNotes());
    }
}
