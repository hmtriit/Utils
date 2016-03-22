package com.licon.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.ImageView;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by FRAMGIA\khairul.alam.licon on 22/3/16.
 */
public class UIUtils {

    public static void showDialogNotify(Activity activity, String title, String msg,
                                        String strOk, String strCancel,
                                        DialogInterface.OnClickListener onOkClickListener,
                                        DialogInterface.OnClickListener onCancelClickListener) {
        if (activity == null) {
            return;
        } else if (activity.isFinishing()) {
            return;
        }
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle(title);
        dialog.setMessage(msg);
        if (!TextUtils.isEmpty(strCancel)) {
            dialog.setNegativeButton(strCancel, onCancelClickListener);
        }
        if (!TextUtils.isEmpty(strOk)) {
            dialog.setPositiveButton(strOk, onOkClickListener);
        }
        dialog.show();
    }

    public static void showDialogNotifyOnUIThread(final Activity activity, final String title, final String msg,
                                                  final String strOk, final String strCancel,
                                                  final DialogInterface.OnClickListener onOkClickListener,
                                                  final DialogInterface.OnClickListener onCancelClickListener) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showDialogNotify(activity, title, msg, strOk, strCancel,
                        onOkClickListener, onCancelClickListener);
            }
        });
    }

    public static DialogInterface.OnClickListener getDefaultDismissListener() {
        DialogInterface.OnClickListener onCancelClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        };
        return onCancelClickListener;
    }

    public static void loadImageViewFromUrl(ImageView imageView, String image_url, Drawable errorDrawable) {
        new LoadImage(imageView, errorDrawable).execute(image_url);
    }

    private static class LoadImage extends AsyncTask<String, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;
        private Drawable placeholer;

        public LoadImage(ImageView imageView, Drawable errorImage) {
            imageViewReference = new WeakReference<ImageView>(imageView);
            placeholer = errorImage;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return downloadBitmap(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }

            if (imageViewReference != null) {
                ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    if (bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                    } else {
                        imageView.setImageDrawable(placeholer);
                    }
                }
            }
        }
    }

    private static Bitmap downloadBitmap(String url) {
        HttpURLConnection urlConnection = null;
        try {
            URL uri = new URL(url);
            urlConnection = (HttpURLConnection) uri.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream != null) {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (Exception e) {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }
}