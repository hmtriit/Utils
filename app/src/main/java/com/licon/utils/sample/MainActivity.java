package com.licon.utils.sample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.licon.mylibrary.LibActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.d("result:", "" + LibActivity.getTime());
    }
}