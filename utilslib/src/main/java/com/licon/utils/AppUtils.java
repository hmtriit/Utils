package com.licon.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.Settings;
import android.provider.Telephony;
import android.text.TextUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import con.licon.data.AppData;
import con.licon.data.IntentData;

/**
 * Created by FRAMGIA\khairul.alam.licon on 18/3/16.
 */
public class AppUtils {

    public static void openBrowser(Activity activity, String url) {
        if ((!TextUtils.isEmpty(url)) && (activity != null)) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse(url));
            activity.startActivity(intent);
        }
    }

    public static void shareDataOnSocialSite(Activity activity, String data, String url, String title) {
        List<Intent> targetShareIntents = new ArrayList<Intent>();

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType(IntentData.SHARE_TYPE_PLAIN_TEXT);

        List<ResolveInfo> resInfos = activity.getPackageManager().queryIntentActivities(shareIntent, 0);
        if (!resInfos.isEmpty()) {
            for (ResolveInfo resInfo : resInfos) {
                String packageName = resInfo.activityInfo.packageName;

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType(IntentData.SHARE_TYPE_PLAIN_TEXT);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setComponent(new ComponentName(packageName, resInfo.activityInfo.name));
                intent.setPackage(packageName);

                /** filtering & customizing selected applications */
                if (packageName.contains(IntentData.SHARE_FILTER_KEY_TWITTER)
                        || packageName.contains(IntentData.SHARE_FILTER_KEY_LINKEDIN)
                        || packageName.contains(IntentData.SHARE_FILTER_KEY_GOOGLE_PLUS)) {
                    intent.putExtra(Intent.EXTRA_TEXT, data);
                    targetShareIntents.add(intent);
                } else if (packageName.contains(IntentData.SHARE_FILTER_KEY_FACEBOOK)) {
                    intent.putExtra(Intent.EXTRA_TEXT, url); // hint: http://goo.gl/bDqvQ9
                    targetShareIntents.add(intent);
                }
            }

            if ((!targetShareIntents.isEmpty()) && (activity != null)) {
                Intent chooserIntent = Intent.createChooser(targetShareIntents.remove(0), title);
                chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
                        targetShareIntents.toArray(new Parcelable[]{}));
                activity.startActivity(chooserIntent);
            }
        }
    }

    public static void shareData(Activity activity, String data, String title) {
        if (activity != null) {
            Intent shareIntent = new Intent();
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, data);
            shareIntent.setType(IntentData.SHARE_TYPE_PLAIN_TEXT);
            activity.startActivity(Intent.createChooser(shareIntent, title));
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void sendSMS(Activity activity, String message) {
        if(activity != null) {
            if (isKitKat()) {
                Intent smsIntent = new Intent(Intent.ACTION_SEND);
                smsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                smsIntent.setType(IntentData.SHARE_TYPE_PLAIN_TEXT);
                smsIntent.putExtra(Intent.EXTRA_TEXT, message);

                String smsPackage = Telephony.Sms.getDefaultSmsPackage(activity);
                if (smsPackage != null) {
                    smsIntent.setPackage(smsPackage);
                }
                activity.startActivity(smsIntent);
            } else {
                Intent viewIntent = new Intent(Intent.ACTION_VIEW);
                viewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                viewIntent.putExtra(IntentData.SMS_BODY, message);
                viewIntent.setType(IntentData.SHARE_TYPE_MESSAGE_VND);
                activity.startActivity(viewIntent);
            }
        }
    }

    public static void sendMail(Activity activity, String subject, String body,
                                String[] emails_address, String title) {
        if((emails_address.length >= 1) && (activity != null)) {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType(IntentData.SHARE_TYPE_PLAIN_TEXT);
            emailIntent.putExtra(Intent.EXTRA_EMAIL, emails_address);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
            emailIntent.putExtra(Intent.EXTRA_TEXT, body);
            emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(Intent.createChooser(emailIntent, title));
        }
    }

    public static boolean createAppDirIfNotExists(String folder_name) throws FileNotFoundException {
        if (!TextUtils.isEmpty(folder_name)) {
            File pdfFolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),
                    folder_name);
            if (!pdfFolder.exists()) {
                pdfFolder.mkdir();
                return true;
            }
        }
        return false;
    }

    public static String getDeviceID(Context context) {
        String device_id = null;
        if (context != null) {
            device_id = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        }
        return device_id;
    }

    public static boolean isAppInstalled(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        try {
            packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            for (NetworkInfo ni : networkInfo) {
                if (AppData.CONNECTION_WIFI.equals(ni.getTypeName()))
                    if (ni.isConnected())
                        haveConnectedWifi = true;
                if (AppData.CONNECTION_MOBILE.equals(ni.getTypeName()))
                    if (ni.isConnected())
                        haveConnectedMobile = true;
            }
        }

        return haveConnectedWifi || haveConnectedMobile;
    }

    public static boolean isActiveNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        //getActiveNetworkInfo() can return null if there is no active network e.g. Airplane Mode.
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean isHostAvailable(String host_url) {
        if (!TextUtils.isEmpty(host_url)) {
            try {
                InetAddress ipAddr = InetAddress.getByName(host_url);
                return (ipAddr.equals("")) ? false : true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public static int getSDKVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }

    public static boolean isLollipopVersion() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean isKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static boolean isHoneyComb() {
        return Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB;
    }
}