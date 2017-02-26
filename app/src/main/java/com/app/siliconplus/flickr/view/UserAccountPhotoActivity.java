package com.app.siliconplus.flickr.view;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.siliconplus.flickr.R;
import com.app.siliconplus.flickr.adapter.FlickrImageAdapter;
import com.app.siliconplus.flickr.model.FlickrImageDetails;
import com.app.siliconplus.flickr.model.GetUserFlickerImagListInteractorImpl;
import com.app.siliconplus.flickr.presenter.presenterimpl.FlickerImageListImpl;
import com.app.siliconplus.flickr.presenter.presenterinterface.FlickrImageListPresenter;
import com.app.siliconplus.flickr.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class UserAccountPhotoActivity extends BaseActivity implements FlickrImageListPresenter.View, View.OnClickListener {

    private FlickrImageListPresenter mFlickrImageListPresenter;
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;
    RecyclerView recyclerView;
    TextView pageOne, pageTwo, pageThree, errorMsg;
    LinearLayout pagerView;
    FlickrImageAdapter rcAdapter;
    List<FlickrImageDetails> fIDetailses = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        mFlickrImageListPresenter = new FlickerImageListImpl(
                this,
                new GetUserFlickerImagListInteractorImpl(this)
        );

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        pageOne = (TextView) findViewById(R.id.tv_page_one);
        pageTwo = (TextView) findViewById(R.id.tv_page_two);
        pageThree = (TextView) findViewById(R.id.tv_page_three);
        errorMsg = (TextView)findViewById(R.id.tv_error_msg);
        pagerView = (LinearLayout)findViewById(R.id.ll_pager_view);
        pagerView.setVisibility(View.GONE);
        recyclerView.setHasFixedSize(true);
        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        rcAdapter = new FlickrImageAdapter(UserAccountPhotoActivity.this, fIDetailses);
        recyclerView.setAdapter(rcAdapter);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);
        pageOne.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        pageTwo.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        pageThree.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        recyclerView.setItemAnimator(null);
        pageOne.setOnClickListener(this);
        pageTwo.setOnClickListener(this);
        pageThree.setOnClickListener(this);
        if (CommonUtils.isOnline(UserAccountPhotoActivity.this)) {
            mFlickrImageListPresenter.getFlickerImageList(1);
        }
    }


    @Override
    public void onGetFlickerSuccessful(List<FlickrImageDetails> flickrImageDetailses) {
        int pagesValue = sharepreferenceKeystore.getIntValue("user_total_page");
        if (pagesValue >= 3) {
            pagerView.setVisibility(View.VISIBLE);
            pageOne.setVisibility(View.VISIBLE);
            pageTwo.setVisibility(View.VISIBLE);
            pageThree.setVisibility(View.VISIBLE);
        } else if (pagesValue == 2) {
            pagerView.setVisibility(View.VISIBLE);
            pageOne.setVisibility(View.VISIBLE);
            pageTwo.setVisibility(View.VISIBLE);
            pageThree.setVisibility(View.GONE);
        } else if (pagesValue == 1) {
            pagerView.setVisibility(View.VISIBLE);
            pageOne.setVisibility(View.VISIBLE);
            pageTwo.setVisibility(View.GONE);
            pageThree.setVisibility(View.GONE);
        } else {
            pageOne.setVisibility(View.GONE);
            pageTwo.setVisibility(View.GONE);
            pageThree.setVisibility(View.GONE);
            errorMsg.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            pagerView.setVisibility(View.GONE);
        }
        fIDetailses = flickrImageDetailses;
        rcAdapter.updateRecord(flickrImageDetailses);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(int code, String message) {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_page_one:
                if (CommonUtils.isOnline(UserAccountPhotoActivity.this)) {
                    mFlickrImageListPresenter.getFlickerImageList(1);
                    pageOne.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    pageTwo.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    pageThree.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                }

                break;
            case R.id.tv_page_two:
                if (CommonUtils.isOnline(UserAccountPhotoActivity.this)) {
                    mFlickrImageListPresenter.getFlickerImageList(2);
                    pageOne.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    pageTwo.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    pageThree.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                }
                break;
            case R.id.tv_page_three:
                if (CommonUtils.isOnline(UserAccountPhotoActivity.this)) {
                    mFlickrImageListPresenter.getFlickerImageList(3);
                    pageOne.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    pageTwo.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    pageThree.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }
                break;

        }
    }

}
