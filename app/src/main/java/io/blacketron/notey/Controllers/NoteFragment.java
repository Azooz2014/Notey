package io.blacketron.notey.Controllers;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.UUID;

import io.blacketron.notey.Models.Note;
import io.blacketron.notey.Models.NotesStorage;
import io.blacketron.notey.R;

public class NoteFragment extends Fragment implements View.OnClickListener{

    private static final String ARG_NOTE_ID = "note_id";

    private View mView;
//    private EditText mTxtFieldNote;
//    private EditText mTxtFieldTitle;
    private TextInputLayout mTitleFieldWrapper;
    private TextInputLayout mNoteFieldWrapper;

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

        if(noteId != null) {
            /*Fetching and getting Note saved in NotesStorage based on it's ID.*/
            mNote = NotesStorage.get(getContext()).getNote(noteId);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_note, container, false);

//        mTxtFieldNote = mView.findViewById(R.id.noteField);
//        mTxtFieldTitle = mView.findViewById(R.id.titleField);

        mTitleFieldWrapper = mView.findViewById(R.id.titleFieldWrapper);
        mNoteFieldWrapper = mView.findViewById(R.id.noteFieldWrapper);
        mBtnDone = mView.findViewById(R.id.btn_done);

        mBtnDone.setOnClickListener(this);
        
        if(mNote != null) {

//            mTxtFieldTitle.setText(mNote.getTitle());
//            mTxtFieldNote.setText(mNote.getNotes());
            mTitleFieldWrapper.getEditText().setText(mNote.getTitle());
            mNoteFieldWrapper.getEditText().setText(mNote.getNotes());
        }

        return mView;
    }

    @Override
    public void onClick(View v) {

        inputHandling();
    }

    /*Updates existing Note*/
    public void updateNote(){

//        mNote.setTitle(mTxtFieldTitle.getText().toString());
//        mNote.setNotes(mTxtFieldNote.getText().toString());
        mNote.setTitle(mTitleFieldWrapper.getEditText().getText().toString());
        mNote.setNotes(mNoteFieldWrapper.getEditText().getText().toString());
        NotesStorage.get(getContext()).updateNote(mNote);

    }

    private void inputHandling(){

        if(mNote == null && mTitleFieldWrapper.getEditText().getText().toString().isEmpty()
                && mNoteFieldWrapper.getEditText().getText().toString().isEmpty()){

            /*Snackbar snackBar = Snackbar.make(v, R.string.snackbar_error, Snackbar.LENGTH_SHORT);
            snackBar.getView().setBackground(getResources().getDrawable(R.drawable.background_snackbar_error));
            snackBar.show();*/

            mTitleFieldWrapper.setError(getResources().getText(R.string.txt_field_error));
            mNoteFieldWrapper.setErrorEnabled(true);
            mNoteFieldWrapper.setError(getResources().getText(R.string.txt_field_error));

        } else if(mNote != null){
            if (mTitleFieldWrapper.getEditText().getText().toString().isEmpty()
                    && mNoteFieldWrapper.getEditText().getText().toString().isEmpty()){

                mTitleFieldWrapper.setError(getResources().getText(R.string.txt_field_error));
                mNoteFieldWrapper.setErrorEnabled(true);
                mNoteFieldWrapper.setError(getResources().getText(R.string.txt_field_error));
            }else{

                mTitleFieldWrapper.setErrorEnabled(false);
                mNoteFieldWrapper.setErrorEnabled(false);

                updateNote();

                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        }

        else {

            mTitleFieldWrapper.setErrorEnabled(false);
            mNoteFieldWrapper.setErrorEnabled(false);

            mNote = new Note();

            mNote.setTitle(mTitleFieldWrapper.getEditText().getText().toString());
            mNote.setNotes(mNoteFieldWrapper.getEditText().getText().toString());

            NotesStorage.get(getContext()).addNote(mNote);

            getActivity().setResult(Activity.RESULT_OK);
            getActivity().finish();
        }
    }
}
