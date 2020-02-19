package io.blacketron.notey.Controllers;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.UUID;

import io.blacketron.notey.Models.Note;
import io.blacketron.notey.Models.NotesStorage;
import io.blacketron.notey.R;

public class NoteFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_NOTE_ID = "note_id";

    private EditText mTxtFieldNote;
    private EditText mTxtFieldTitle;
    private Button mBtnDone;

    private Note mNote;

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
        UUID noteId = (UUID) getArguments()
                .getSerializable(ARG_NOTE_ID);

        if (noteId != null) {
            /*Fetching and getting Note saved in NotesStorage based on it's ID.*/
            mNote = NotesStorage.get(getContext()).getNote(noteId);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_note, container, false);

        mTxtFieldNote = view.findViewById(R.id.noteField);
        mTxtFieldTitle = view.findViewById(R.id.titleField);
        mBtnDone = view.findViewById(R.id.btn_done);

        mBtnDone.setOnClickListener(this);

        if (mNote != null) {

            mTxtFieldTitle.setText(mNote.getTitle());
            mTxtFieldNote.setText(mNote.getNotes());
        }

        return view;
    }

    @Override
    public void onClick(View v) {


        if (mNote == null && (!mTxtFieldTitle.getText().toString().isEmpty() || !mTxtFieldNote.getText().toString().isEmpty())) {

            mNote = new Note();

            mNote.setTitle(mTxtFieldTitle.getText().toString());
            mNote.setNotes(mTxtFieldNote.getText().toString());

            NotesStorage.get(getContext()).addNote(mNote);

            getActivity().setResult(Activity.RESULT_OK);
            getActivity().finish();

        } else if (mNote != null) {

            updateNote();

            getActivity().setResult(Activity.RESULT_OK);
            getActivity().finish();
        } else {

            Snackbar.make(v, R.string.snackbar_error, Snackbar.LENGTH_SHORT).show();

        }
    }

    /*Updates existing Note*/
    public void updateNote() {

        mNote.setTitle(mTxtFieldTitle.getText().toString());
        mNote.setNotes(mTxtFieldNote.getText().toString());
        NotesStorage.get(getContext()).updateNote(mNote);

    }

}
