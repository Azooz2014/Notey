package io.blacketron.notey.Controllers.Menu;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import io.blacketron.notey.R;
import io.blacketron.notey.Utils.SingleFragmentActivity;

public class AboutActivity extends SingleFragmentActivity {

    public static Intent newIntent (Context context){
        return new Intent(context, AboutActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        return AboutFragment.newInstance();
    }

    @Override
    protected int fragmentLayoutRes() {
        return R.id.todo_fragment_container;
    }
}