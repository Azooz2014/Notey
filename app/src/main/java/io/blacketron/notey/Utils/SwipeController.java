package io.blacketron.notey.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;

import io.blacketron.notey.R;

import static android.support.v7.widget.helper.ItemTouchHelper.ACTION_STATE_SWIPE;
import static android.support.v7.widget.helper.ItemTouchHelper.LEFT;

enum ButtonsState {

    GONE,
    LEFT_VISIBLE,
    RIGHT_VISIBLE
}

public class SwipeController extends ItemTouchHelper.Callback {

    private boolean mSwipeBack = false;
    private ButtonsState mButtonShowedState = ButtonsState.GONE;
    private static final float mButtonWidth = 300;
    private RectF mButtonInstance = null;
    private SwipeControllerActions mButtonActions;
    private RecyclerView.ViewHolder mCurrentItemViewHolder = null;
    private Context mContext;

    public SwipeController(Context context, SwipeControllerActions buttonActions) {
        this.mButtonActions = buttonActions;
        this.mContext = context;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, LEFT);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    //Responsible of swiping the view back to it's original position.
    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {

        if (mSwipeBack) {

            mSwipeBack = mButtonShowedState != ButtonsState.GONE;

            return 0;
        }

        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {

        //Responsible of stopping the card view to close on the newly drawn buttons.
        if (actionState == ACTION_STATE_SWIPE) {

            if (mButtonShowedState != ButtonsState.GONE) {
                if (mButtonShowedState == ButtonsState.LEFT_VISIBLE)
                    dX = Math.max(dX, mButtonWidth);
                if (mButtonShowedState == ButtonsState.RIGHT_VISIBLE)
                    dX = Math.min(dX, -mButtonWidth);
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            } else {
                setTouchListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }

        if (mButtonShowedState == ButtonsState.GONE) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
        mCurrentItemViewHolder = viewHolder;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setTouchListener(final Canvas c, final RecyclerView recyclerView,
                                  final RecyclerView.ViewHolder viewHolder,
                                  final float dX, final float dY, final int actionState,
                                  final boolean isCurrentlyActive) {

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mSwipeBack = event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP;
                if (mSwipeBack) {
                    if (dX < -mButtonWidth) mButtonShowedState = ButtonsState.RIGHT_VISIBLE;
                    else if (dX > mButtonWidth) mButtonShowedState = ButtonsState.LEFT_VISIBLE;

                    if (mButtonShowedState != ButtonsState.GONE) {
                        setTouchDownListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                        setItemsClickable(recyclerView, false);
                    }
                }
                return false;
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setTouchDownListener(final Canvas c,
                                      final RecyclerView recyclerView,
                                      final RecyclerView.ViewHolder viewHolder,
                                      final float dX, final float dY,
                                      final int actionState, final boolean isCurrentlyActive) {

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    setTouchUpListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
                return false;
            }
        });
    }


    @SuppressLint("ClickableViewAccessibility")
    private void setTouchUpListener(final Canvas c,
                                    final RecyclerView recyclerView,
                                    final RecyclerView.ViewHolder viewHolder,
                                    final float dX, final float dY,
                                    final int actionState, final boolean isCurrentlyActive) {

        recyclerView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    SwipeController.super.onChildDraw(c, recyclerView, viewHolder, 0, dY, actionState, isCurrentlyActive);

                    recyclerView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return false;
                        }
                    });

                    setItemsClickable(recyclerView, true);
                    mSwipeBack = false;

                    if (mButtonActions != null && mButtonInstance != null
                            && mButtonInstance.contains(event.getX(), event.getY())) {

                        if (mButtonShowedState == ButtonsState.LEFT_VISIBLE) {
                            mButtonActions.onLeftClicked(v, viewHolder.getAdapterPosition());
                        } else if (mButtonShowedState == ButtonsState.RIGHT_VISIBLE) {
                            mButtonActions.onRightClicked(v, viewHolder.getAdapterPosition());
                        }
                    }

                    mButtonShowedState = ButtonsState.GONE;
                    mCurrentItemViewHolder = null;
                }
                return false;
            }
        });
    }

    private void setItemsClickable(RecyclerView recyclerView,
                                   boolean isClickable) {

        for (int i = 0; i < recyclerView.getChildCount(); ++i) {
            recyclerView.getChildAt(i).setClickable(isClickable);
        }
    }

    private void drawButtons(Canvas c, RecyclerView.ViewHolder viewHolder) {
        float padding = 20;
        float buttonWidthWithPadding = mButtonWidth - padding;
        float corners = 28;

        View itemView = viewHolder.itemView;
        Paint paint = new Paint();

        //Uncomment if going to use left button.
                                /*RectF leftButton = new RectF(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + buttonWidthWithoutPadding, itemView.getBottom());
        paint.setColor(Color.BLUE);
        c.drawRoundRect(leftButton, corners, corners, paint);
        drawText("EDIT", c, leftButton, paint);*/

        RectF rightButton = new RectF(itemView.getRight() - buttonWidthWithPadding, itemView.getTop(), itemView.getRight(), itemView.getBottom());
        paint.setColor(Color.RED);
        c.drawRoundRect(rightButton, corners, corners, paint);
        //drawText("DELETE", c, rightButton, paint);
        drawIcon(R.drawable.ic_delete_white_24dp, c, itemView);

        //Uncomment if going to use left button.
                              /* mButtonInstance = null;
-        if (mButtonShowedState == ButtonsState.LEFT_VISIBLE) {
-            mButtonInstance = leftButton;
-        }*/
        if (mButtonShowedState == ButtonsState.RIGHT_VISIBLE) {
            mButtonInstance = rightButton;
        }
    }

    private void drawText(String text, Canvas c, RectF button, Paint paint) {
        float textSize = 60;
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setTextSize(textSize);

        float textWidth = paint.measureText(text);
        c.drawText(text, button.centerX() - (textWidth / 2), button.centerY() + (textSize / 2), paint);
    }

    private void drawIcon(int drawableResource, Canvas c, View itemView) {

        Drawable drawable = ContextCompat.getDrawable(mContext, drawableResource);

        int intrinsicHeight = drawable.getIntrinsicHeight();
        int intrinsicWidth = drawable.getIntrinsicWidth();

        int top = itemView.getTop() + (itemView.getHeight() - intrinsicHeight) / 2;
        int margin = (itemView.getWidth() - intrinsicWidth) / 16;
        int left = itemView.getRight() - intrinsicWidth - margin;
        int right = itemView.getRight() - margin;
        int bottom = top + intrinsicHeight;

        drawable.setBounds(left, top, right, bottom);
        drawable.draw(c);
    }

    public void onDraw(Canvas c) {
        if (mCurrentItemViewHolder != null) {
            drawButtons(c, mCurrentItemViewHolder);
        }
    }
}
