package com.itcinfotech.ssfpartner.utility;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;

import com.itcinfotech.ssfpartner.R;


public class TypefaceSingleton {
    private static TypefaceSingleton instance = new TypefaceSingleton();
    private TypefaceSingleton() {}
    private Typeface mTypeFace = null;

    public static TypefaceSingleton getInstance() {
        return instance;
    }
    public Typeface getTypeface(Context context) {
        if(mTypeFace == null){
            mTypeFace = ResourcesCompat.getFont(context, R.font.font_lato);
        }
        return mTypeFace;
    }
}
