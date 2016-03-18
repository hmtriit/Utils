package com.licon.utils.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.licon.utils.AppUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(AppUtils.isNetworkConnected(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "connected!", Toast.LENGTH_SHORT).show();
        }
    }
}