package io.blacketron.notey.Controllers.Menu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.blacketron.notey.BuildConfig;
import io.blacketron.notey.R;

public class MenuFragment extends Fragment {

    public MenuFragment() {
        // Required empty public constructor
    }

    public static MenuFragment newInstance() {
        return new MenuFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menu_about, container, false);

        TextView aboutVersion = view.findViewById(R.id.about_version);
        TextView gitHub = view.findViewById(R.id.about_github);

        String updateVersion = getString(R.string.about_version, BuildConfig.VERSION_NAME);

        gitHub.setMovementMethod(LinkMovementMethod.getInstance());

        aboutVersion.setText(updateVersion);

        // Inflate the layout for this fragment
        return view;
    }
}