package com.licon.utils.sample;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.licon.utils.AppUtils;
import com.licon.utils.DateTimeUtils;
import com.licon.utils.UIUtils;

import java.util.Date;

public class MainActivity extends Activity {

    private static final String IMG_URL = "https://farm2.staticflickr.com/1603/25683312252_7cdca91a0b_k.jpg";
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.image_loaded);

        if (AppUtils.isNetworkAvailable(getApplicationContext())) {
            UIUtils.showDialogNotify(this,
                    getResources().getString(R.string.dialog_title),
                    getResources().getString(R.string.dialog_msg),
                    getResources().getString(R.string.dialog_button_yes),
                    getResources().getString(R.string.dialog_button_no),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            UIUtils.loadImageViewFromUrl(mImageView, IMG_URL,
                                    getResources().getDrawable(R.mipmap.ic_launcher));
                        }
                    },
                    UIUtils.getDefaultDismissListener());
        }

        Date date = DateTimeUtils.parseDateFromString("2010-10-15T09:27:37Z",
                DateTimeUtils.getSimpleDateFormats().get(0));
        Long date_long = DateTimeUtils.parseDateToLong(date);
        Log.d("Utils-DateTime: ", DateTimeUtils.getTimeDifferenceUnit(date_long,
                getApplicationContext()));
        Log.d("Utils-DateTime: ", DateTimeUtils.parseDateToString(date,
                DateTimeUtils.getSimpleDateFormats().get(6)));
    }
}