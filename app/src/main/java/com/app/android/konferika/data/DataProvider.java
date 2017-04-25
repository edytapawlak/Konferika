package com.app.android.konferika.data;

import android.content.ContentProvider;

/**
 * Created by edyta on 25.04.17.
 */

public class DataProvider extends ContentProvider {

    public static final int CODE_DATA = 100;
    public static final int CODE_SPECYFIC_DATA = 101;

    private DatabaseOpenHelper mOpenHelper;

    @Override
    public boolean onCreate() {
        mOpenHelper = new DatabaseOpenHelper(getContext());
        return true;
    }
}
