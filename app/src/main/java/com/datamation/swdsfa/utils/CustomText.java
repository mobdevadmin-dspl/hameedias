package com.datamation.swdsfa.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;


public class CustomText extends android.support.v7.widget.AppCompatEditText {
    public CustomText(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public CustomText(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public CustomText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("fonts/Cuprum-Regular.ttf", context);
        setTypeface(customFont);
    }
}
