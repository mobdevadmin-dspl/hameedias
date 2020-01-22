package com.datamation.swdsfa.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.datamation.swdsfa.controller.*;


@SuppressLint("AppCompatCustomView")
public class CustomizeFont extends TextView {
    public CustomizeFont(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public CustomizeFont(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public CustomizeFont(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("fonts/Roboto-Light.ttf", context);
        setTypeface(customFont);
    }
}
