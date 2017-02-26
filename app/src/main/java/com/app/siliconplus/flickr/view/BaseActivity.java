package com.app.siliconplus.flickr.view;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.app.siliconplus.flickr.R;
import com.app.siliconplus.flickr.utils.SharepreferenceKeystore;


public class BaseActivity extends AppCompatActivity {
    private FrameLayout frameLayout;
    private final int mBaseLayout = R.layout.baselayout;
    public SharepreferenceKeystore sharepreferenceKeystore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.baselayout);
        setTitle(null);
        sharepreferenceKeystore = SharepreferenceKeystore.getInstance(this);

        frameLayout = (FrameLayout)findViewById(R.id.basecontainer);
        Toolbar topToolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(topToolBar);

        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        topToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @Override
    public void setContentView(int layoutResID) {
        if (layoutResID == mBaseLayout) {
            View content = getWindow().findViewById(android.R.id.content);
            if (content != null) {
                super.setContentView(mBaseLayout);
            }
        }else {
            LayoutInflater inflater = getLayoutInflater();
            inflater.inflate(layoutResID, frameLayout);
        }

    }


}
