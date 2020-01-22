package com.datamation.swdsfa.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;


public class CustomFont extends android.support.v7.widget.AppCompatTextView {
    public CustomFont(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public CustomFont(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public CustomFont(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("fonts/Cuprum-Regular.ttf", context);
        setTypeface(customFont);
    }
}
