package io.blacketron.notey.Utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import io.blacketron.notey.R;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(fragmentLayoutRes());

        if(fragment == null){

            fragment = createFragment();

            fm.beginTransaction()
                    .add(fragmentLayoutRes(), fragment)
                    .commit();
        }
    }

    protected abstract int fragmentLayoutRes();
}
