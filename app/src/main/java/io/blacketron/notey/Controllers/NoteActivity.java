package io.blacketron.notey.Controllers;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import java.util.UUID;

import io.blacketron.notey.R;
import io.blacketron.notey.Utils.SingleFragmentActivity;

public class NoteActivity extends SingleFragmentActivity {


    private static final String EXTRA_NOTE_ID = "io.blacketron.notey.note_id";

    /*An Intent method tells the NoteFragment what Note to display
    * when starting NoteActivity by passing noteID as an Intent extra*/
    public static Intent newIntent (Context context, UUID noteID){

        Intent intent = new Intent(context, NoteActivity.class);

        intent.putExtra(EXTRA_NOTE_ID, noteID);

        return  intent;
    }

    public static Intent newIntent (Context context){
        return new Intent(context, NoteActivity.class);
    }

    @Override
    protected Fragment createFragment() {

        UUID noteID = (UUID) getIntent().getSerializableExtra(EXTRA_NOTE_ID);

        return NoteFragment.newInstance(noteID);
    }

    @Override
    protected int fragmentLayoutRes() {
        return R.id.todo_fragment_container;
    }

    //Action Bar Up Button option.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // setResult and close the activity when Action Bar Up Button clicked.
        if (item.getItemId() == android.R.id.home) {
            setResult(RESULT_CANCELED);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
