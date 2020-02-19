package io.blacketron.notey.Controllers;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import io.blacketron.notey.Models.Note;
import io.blacketron.notey.Models.NotesStorage;
import io.blacketron.notey.R;
import io.blacketron.notey.Utils.SwipeController;
import io.blacketron.notey.Utils.SwipeControllerActions;

//TODO: Improve cardview to wrap long notes like google keep does.
public class NoteListFragment extends Fragment implements View.OnClickListener{

    public static final int LIST_FRAGMENT_REQUEST_CODE = 0;

    private View mView;

    private FloatingActionButton mFab;

    private ImageView mBackground;

    private RecyclerView mRecyclerView;

    private NoteListAdapter mNoteListAdapter;

    private SwipeController mSwipeController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_notes_list, container, false);

        mFab = mView.findViewById(R.id.fab);
        mFab.setOnClickListener(this);

        mBackground = mView.findViewById(R.id.empty_list_background);

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

            Snackbar.make(mView, R.string.snackbar_pass, Snackbar.LENGTH_SHORT).show();
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

            mNoteListAdapter = new NoteListAdapter(notes);
            mRecyclerView.setAdapter(mNoteListAdapter);
        }else {

            mNoteListAdapter.setNotes(notes);
            mNoteListAdapter.notifyDataSetChanged();
        }
    }

    private void updateBackground(){

        if(mNoteListAdapter.getItemCount() > 0){

            mBackground.setVisibility(View.GONE);
        } else{
            mBackground.setVisibility(View.VISIBLE);
        }
    }

    public void swipeHandler(){

          mSwipeController = new SwipeController(this.getContext(),new SwipeControllerActions() {
            @Override
            public void onRightClicked(View view, int position) {

                mNoteListAdapter.deleteNote(view, mNoteListAdapter.getNote());
                mNoteListAdapter.notifyItemRemoved(position);
                mNoteListAdapter.notifyItemRangeChanged(position, mNoteListAdapter.getItemCount());

                updateBackground();

                Snackbar.make(view, R.string.snackbar_deleted, Snackbar.LENGTH_SHORT).show();
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
}
