package io.blacketron.notey.Controllers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.UUID;

import io.blacketron.notey.Models.Note;
import io.blacketron.notey.Models.NotesStorage;
import io.blacketron.notey.R;

public class NoteFragment extends Fragment {

    private static final String ARG_NOTE_ID = "note_id";
    private Note mNote;

    //TODO: this class handles the functionality of new note screen.

    public static NoteFragment newInstance(UUID noteId) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_NOTE_ID, noteId);

        NoteFragment noteFragment = new NoteFragment();
        noteFragment.setArguments(args);

        return noteFragment;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*Retrieving whatever UUID object was saved to ARG_NOTE_ID String key when fragment
         * first created or re-created.*/
        UUID crimeId = (UUID) getArguments()
                .getSerializable(ARG_NOTE_ID);

        /*Fetching and getting Note saved in NotesStorage based on it's ID.*/
//        mNote = NotesStorage.get().getNote(crimeId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_note, container, false);

        return view;
    }
}
