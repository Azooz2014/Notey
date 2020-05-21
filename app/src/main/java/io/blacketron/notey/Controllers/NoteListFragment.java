package io.blacketron.notey.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.blacketron.notey.Models.Note;
import io.blacketron.notey.Models.NotesStorage;
import io.blacketron.notey.R;
import io.blacketron.notey.Utils.SwipeController;
import io.blacketron.notey.Utils.SwipeControllerActions;

public class NoteListFragment extends Fragment implements View.OnClickListener{

    public static final int LIST_FRAGMENT_REQUEST_CODE = 0;

    private View mView;

    private FloatingActionButton mFab;

    private ImageView mBackground;

    private TextView mListHint;

    private RecyclerView mRecyclerView;

    private NoteListAdapter mNoteListAdapter;

    private SwipeController mSwipeController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //To support drawables on devices running lower than API 21
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        mView = inflater.inflate(R.layout.fragment_notes_list, container, false);

        mFab = mView.findViewById(R.id.fab);
        mFab.setOnClickListener(this);

        mBackground = mView.findViewById(R.id.empty_list_background);

        mListHint = mView.findViewById(R.id.list_hint);

        mRecyclerView = mView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        swipeHandler();

        updateList();

        updateBackground();

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();

        updateList();
        updateBackground();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){

            case LIST_FRAGMENT_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK){

                    snackBarPass(R.string.snackbar_pass);

                } else{

                    Snackbar.make(mView, R.string.snackbar_cancelled, Snackbar.LENGTH_SHORT).show();
                }
            break;

            case NoteListViewHolder.VIEW_HOLDER_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK){

                    snackBarPass(R.string.snackbar_update);
                } else{

                    snackBarError(R.string.snackbar_unchanged);
                }
            break;
        }
    }

    @Override
    public void onClick(View v) {

        Intent intent = NoteActivity.newIntent(getContext());
        startActivityForResult(intent,LIST_FRAGMENT_REQUEST_CODE);
    }

    private void updateList(){

        NotesStorage notesStorage = NotesStorage.get(getContext());
        List<Note> notes = notesStorage.getNotes();

        if(mNoteListAdapter == null){

            mNoteListAdapter = new NoteListAdapter(this,notes);
            mRecyclerView.setAdapter(mNoteListAdapter);
        }else {

            mNoteListAdapter.setNotes(notes);
            mNoteListAdapter.notifyDataSetChanged();
        }
    }

    private void updateBackground(){

        if(mNoteListAdapter.getItemCount() > 0){

            mBackground.setVisibility(View.GONE);
            mListHint.setVisibility(View.GONE);
        } else{
            mBackground.setVisibility(View.VISIBLE);
            mListHint.setVisibility(View.VISIBLE);
        }
    }

    //TODO: swipeHandler deletes wrong item.
    public void swipeHandler(){

          mSwipeController = new SwipeController(this.getContext(),new SwipeControllerActions() {
            @Override
            public void onRightClicked(View view, int position) {

                mNoteListAdapter.deleteNote(view, mNoteListAdapter.getNote());
                mNoteListAdapter.notifyItemRemoved(position);
                mNoteListAdapter.notifyItemRangeChanged(position, mNoteListAdapter.getItemCount());

                updateBackground();

                snackBarError(R.string.snackbar_deleted);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mSwipeController);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.onDraw(c, parent, state);

                mSwipeController.onDraw(c);
            }
        });
    }

    private void snackBarPass(int msgResId){

        Snackbar snackBar = Snackbar.make(mView, msgResId, Snackbar.LENGTH_SHORT);
        snackBar.getView().setBackground(getResources().getDrawable(R.drawable.background_snackbar_pass));
        TextView snackBarText = snackBar.getView()
                .findViewById(android.support.design.R.id.snackbar_text);
        snackBarText.setTextColor(getResources().getColor(R.color.textColorPrimary));
        snackBarText.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.snackbar_text_size));
        snackBar.show();
    }

    private void snackBarError(int msgResId){

        Snackbar snackBar = Snackbar.make(mView, msgResId, Snackbar.LENGTH_SHORT);
        snackBar.getView().setBackground(getResources().getDrawable(R.drawable.background_snackbar_error));
        snackBar.show();
    }
}
