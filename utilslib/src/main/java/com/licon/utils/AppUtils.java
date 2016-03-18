package com.licon.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.text.TextUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import con.licon.data.IntentData;

/**
 * Created by FRAMGIA\khairul.alam.licon on 18/3/16.
 */
public class AppUtils {
    public static void openBrowser(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

    public static void shareDataUsingIntent(String data, String url, Activity activity) {
        List<Intent> targetShareIntents = new ArrayList<Intent>();

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType(IntentData.SHARE_TYPE_PLAIN_TEXT);

        List<ResolveInfo> resInfos = activity.getPackageManager().queryIntentActivities(shareIntent, 0);
        if(!resInfos.isEmpty()) {
            for(ResolveInfo resInfo : resInfos) {
                String packageName = resInfo.activityInfo.packageName;

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType(IntentData.SHARE_TYPE_PLAIN_TEXT);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setComponent(new ComponentName(packageName, resInfo.activityInfo.name));
                intent.setPackage(packageName);

                /** filtering & customizing selected applications */
                if(packageName.contains(IntentData.SHARE_FILTER_KEY_TWITTER)
                        || packageName.contains(IntentData.SHARE_FILTER_KEY_LINKEDIN)
                        || packageName.contains(IntentData.SHARE_FILTER_KEY_GOOGLE_PLUS)) {
                    intent.putExtra(Intent.EXTRA_TEXT, data);
                    targetShareIntents.add(intent);
                } else if (packageName.contains(IntentData.SHARE_FILTER_KEY_FACEBOOK)) {
                    intent.putExtra(Intent.EXTRA_TEXT, url); // hint: http://goo.gl/bDqvQ9
                    targetShareIntents.add(intent);
                }
            }

            if(!targetShareIntents.isEmpty()) {
                Intent chooserIntent = Intent.createChooser(targetShareIntents.remove(0),
                        activity.getString(R.string.share_via));
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetShareIntents.toArray(new Parcelable[]{}));
                chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(chooserIntent);
            }
        }
    }

    public static boolean createAppDirIfNotExists(String folder_name) throws FileNotFoundException {
        if(!TextUtils.isEmpty(folder_name)) {
            File pdfFolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), folder_name);
            if (!pdfFolder.exists()) {
                pdfFolder.mkdir();
                return true;
            }
        }
        return false;
    }

    public static boolean isNetworkConnected(Context context) {
        if(context != null) {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        }
        return false;
    }

    public static boolean isHostAvailable(String host_url) {
        if(!TextUtils.isEmpty(host_url)) {
            try {
                InetAddress ipAddr = InetAddress.getByName(host_url);
                return (ipAddr.equals("")) ? false : true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }
}