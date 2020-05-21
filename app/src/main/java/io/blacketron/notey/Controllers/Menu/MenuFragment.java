package io.blacketron.notey.Controllers.Menu;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
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

        //To support drawables on devices running lower than API 21
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        View view = inflater.inflate(R.layout.fragment_menu_about, container, false);

        TextView aboutVersion = view.findViewById(R.id.about_version);
        TextView gitHub = view.findViewById(R.id.about_github);
        TextView aboutAuthor = view.findViewById(R.id.about_body);

        String updateVersion = getString(R.string.about_version, BuildConfig.VERSION_NAME);

        gitHub.setMovementMethod(LinkMovementMethod.getInstance());

        aboutVersion.setText(updateVersion);

        author(aboutAuthor);

        // Inflate the layout for this fragment
        return view;
    }


    private void author(TextView textView){

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){

            SpannableStringBuilder builder = new SpannableStringBuilder();
            builder.append(getResources().getString(R.string.menu_about_crafted))
                    .append(" ")// Left margin
                    .append(" ");
            builder.setSpan(new ImageSpan(getActivity(), R.drawable.ic_pixel_heart),
                    builder.length() - 1, builder.length(), 0);
            builder.append(" "); // Right margin
            builder.append(getResources().getString(R.string.menu_about_author));

            textView.setText(builder);
        }else{

            SpannableStringBuilder builder = new SpannableStringBuilder();
            builder.append(getResources().getString(R.string.menu_about_crafted))
                    .append(" ") // Left margin
                    .append(" ", new ImageSpan(getActivity(), R.drawable.ic_pixel_heart), 0)
                    .append(" ") // Right margin
                    .append(getResources().getString(R.string.menu_about_author));

            textView.setText(builder);
        }
    }
}