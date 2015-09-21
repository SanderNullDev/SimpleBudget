package com.gridlockdev.simplebudget.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by Sander on 21-9-2015.
 */
public class SquareRelativeLayout extends RelativeLayout {


    public SquareRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, (int) (widthMeasureSpec / 1.5));
    }
}
