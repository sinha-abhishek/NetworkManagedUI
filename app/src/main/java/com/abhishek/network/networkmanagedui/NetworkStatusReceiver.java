package com.abhishek.network.networkmanagedui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.ColorFilter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.abhishek.network.networkmanagedui.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhishek on 26/04/16.
 */
public class NetworkStatusReceiver extends BroadcastReceiver {
    private static List<View> viewsToChange = new ArrayList<>();
    private static boolean isDisconnected = false;
    private static final String NETWORK_TAG = "networkdependent";
    private static NetworkCallback networkCallback = null;
    private static ColorFilter disableColorFilter;
    private static boolean applyFilter;

    public interface NetworkCallback {
        void onNetworkChange(boolean isConnected);
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isConnected = Utils.isNetworkAvailable(context);
        if (isConnected) {
            Log.i("NET", "connecte" + isConnected);
            isDisconnected = false;
            ChangeViews(false,applyFilter,disableColorFilter);
            Toast.makeText(context, "Network connected", Toast.LENGTH_SHORT).show();
        } else {
            isDisconnected = true;
            ChangeViews(true,applyFilter,disableColorFilter);
            Toast.makeText(context, "Cannot connect to network. Some actions may not be allowed", Toast.LENGTH_LONG).show();
            Log.i("NET", "not connecte" + isConnected);
        }
        if (networkCallback != null) {
            networkCallback.onNetworkChange(isConnected);
        }
    }


    private static void ChangeViews(boolean disable, boolean applyFilter, ColorFilter colorFilter) {
        for (View view:
                viewsToChange) {
            try {
                if (disable) {
                    view.setClickable(false);
                    if(applyFilter && view.getBackground() != null) {
                        view.getBackground().setColorFilter(colorFilter);
                    }
                } else {
                    view.setClickable(true);
                    if (applyFilter && view.getBackground() != null)
                    view.getBackground().clearColorFilter();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public synchronized static List<View> RegisterParentView(View view, Context context) {
        return RegisterParentView(view,context,null,false,null);
    }

    public synchronized static List<View> RegisterParentView(View view, Context context, NetworkCallback callback) {
        return RegisterParentView(view,context,callback,false,null);
    }

    public synchronized static List<View> RegisterParentView(View view, Context context, NetworkCallback callback, boolean doApplyFilter, ColorFilter colorFilter) {
        isDisconnected = !Utils.isNetworkAvailable(context);
        ViewGroup vg = (ViewGroup)view;
        viewsToChange.clear();
        viewsToChange = Utils.findViewWithTagRecursively(vg, NETWORK_TAG, isDisconnected, applyFilter,colorFilter);
        networkCallback = callback;
        applyFilter = doApplyFilter;
        disableColorFilter = colorFilter;
        return viewsToChange;
    }


}
