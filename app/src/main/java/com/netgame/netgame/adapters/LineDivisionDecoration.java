package com.netgame.netgame.adapters;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.netgame.netgame.R;

/**
 * Created by arkanay on 19/11/17.
 */

public class LineDivisionDecoration extends RecyclerView.ItemDecoration {

    private Drawable lineDivision;

    public LineDivisionDecoration(Context context){
        lineDivision = ContextCompat.getDrawable(context, R.drawable.line_division);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + lineDivision.getIntrinsicHeight();

            lineDivision.setBounds(left, top, right, bottom);
            lineDivision.draw(c);
        }
    }
}
