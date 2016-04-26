package com.abhishek.network.networkmanagedui.utils;

import android.content.Context;
import android.graphics.ColorFilter;
import android.net.ConnectivityManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhishek on 26/04/16.
 */
public class Utils {

    public static List<View> findViewWithTagRecursively(ViewGroup root, Object tag, boolean disable,
                                                        boolean applyFilter, ColorFilter colorFilter){
        List<View> allViews = new ArrayList<View>();

        final int childCount = root.getChildCount();
        for(int i=0; i<childCount; i++){
            final View childView = root.getChildAt(i);

            if(childView instanceof ViewGroup){
                allViews.addAll(findViewWithTagRecursively((ViewGroup)childView, tag, disable, applyFilter, colorFilter));
            }
            else{
                final Object tagView = childView.getTag();
                if(tagView != null && tagView.equals(tag)) {
                    if (disable) {
                        childView.setClickable(false);
                        if(applyFilter && childView.getBackground() != null) {
                            childView.getBackground().setColorFilter(colorFilter);
                        }
                    } else {
                        childView.setClickable(true);
                        if(applyFilter && childView.getBackground() != null ) {
                            childView.getBackground().clearColorFilter();
                        }
                    }
                    allViews.add(childView);
                }
            }
        }

        return allViews;
    }

    public static boolean isNetworkAvailable(Context context) {
        boolean isNetworkConnected = false;
        try {
            ConnectivityManager connManager = (ConnectivityManager) context.getSystemService
                    (Context.CONNECTIVITY_SERVICE);
            if (connManager.getActiveNetworkInfo() != null && connManager.getActiveNetworkInfo()
                    .isAvailable() && connManager.getActiveNetworkInfo().isConnected()) {
                isNetworkConnected = true;
            }
        } catch (Exception ex) {
            isNetworkConnected = false;
        }
        return isNetworkConnected;
    }
}
