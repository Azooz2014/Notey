package io.blacketron.notey.Controllers;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import io.blacketron.notey.Controllers.Menu.MenuActivity;
import io.blacketron.notey.R;
import io.blacketron.notey.Utils.SingleFragmentActivity;

public class NoteListActivity extends SingleFragmentActivity {

    public static Intent newIntent (Context context){
        return new Intent(context, NoteListActivity.class);
    }

    @Override
    protected Fragment createFragment() {

        return new NoteListFragment();
    }

    @Override
    protected int fragmentLayoutRes() {
        return R.id.todo_fragment_container;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_about:
                startActivity(MenuActivity.newIntent(this));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
