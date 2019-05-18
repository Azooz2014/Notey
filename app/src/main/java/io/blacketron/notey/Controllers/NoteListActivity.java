package io.blacketron.notey.Controllers;

import android.support.v4.app.Fragment;

import io.blacketron.notey.R;
import io.blacketron.notey.Utils.SingleFragmentActivity;

public class NoteListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {

        return new NoteListFragment();
    }

    @Override
    protected int fragmentLayoutRes() {
        return R.id.todo_fragment_container;
    }
}
