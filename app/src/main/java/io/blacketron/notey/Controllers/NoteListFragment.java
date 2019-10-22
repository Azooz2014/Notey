package io.blacketron.notey.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import io.blacketron.notey.Models.Note;
import io.blacketron.notey.Models.NotesStorage;
import io.blacketron.notey.R;

public class NoteListFragment extends Fragment implements View.OnClickListener{

    private View mView;

    private FloatingActionButton mFab;

    private ImageView mBackground;

    private RecyclerView mRecyclerView;

    private NoteListAdapter mNoteListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_notes_list, container, false);

        mFab = mView.findViewById(R.id.fab);
        mFab.setOnClickListener(this);

        mBackground = mView.findViewById(R.id.empty_list_background);

        mRecyclerView = mView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        updateList();

        hideBackground();

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();

        updateList();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Snackbar.make(mView, R.string.snackbar_pass, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {

        Intent intent = NoteActivity.newIntent(getContext());
        startActivityForResult(intent,0);
    }

    private void updateList(){

        NotesStorage notesStorage = NotesStorage.get(getContext());
        List<Note> notes = notesStorage.getNotes();

        if(mNoteListAdapter == null){

            mNoteListAdapter = new NoteListAdapter(notes);
            mRecyclerView.setAdapter(mNoteListAdapter);
        }else {

            mNoteListAdapter.setNotes(notes);
            mNoteListAdapter.notifyDataSetChanged();
        }
    }

    public void hideBackground(){

        if(mNoteListAdapter.getItemCount() > 0){

            mBackground.setVisibility(View.GONE);
        }
    }
}
