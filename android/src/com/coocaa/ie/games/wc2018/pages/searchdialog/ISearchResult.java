package com.coocaa.ie.games.wc2018.pages.searchdialog;

import android.content.Context;

import com.coocaa.ie.games.wc2018.pages.searchdialog.impl.SearchResultImpl;

/**
 * Created by Eric on 2018/5/30.
 */

public interface ISearchResult {
    public static SearchResultImpl INSTANCE = new SearchResultImpl();

    void startSearch(Context context, final String qId, String qus, String ans);

    void hideSearchDialg();
}
