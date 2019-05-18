package io.blacketron.notey.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.blacketron.notey.Models.Note;
import io.blacketron.notey.R;

public class NoteListFragment extends Fragment implements View.OnClickListener{


    //TODO: this class handles the functionality of notes list screen.
    //TODO: RecyclerView classes to be handled in separate classes.

    private Note mNote;

    private FloatingActionButton mFab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);

        mFab = view.findViewById(R.id.fab);
        mFab.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        Intent intent = NoteActivity.newIntent(getContext());
        startActivity(intent);
    }
}
