package com.app.siliconplus.flickr.view;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.app.siliconplus.flickr.R;
import com.app.siliconplus.flickr.adapter.FlickrImageAdapter;
import com.app.siliconplus.flickr.model.FlickrImageDetails;
import com.app.siliconplus.flickr.model.GetFlickerImagListInteractorImpl;
import com.app.siliconplus.flickr.presenter.presenterimpl.FlickerImageListImpl;
import com.app.siliconplus.flickr.presenter.presenterinterface.FlickrImageListPresenter;
import com.app.siliconplus.flickr.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class PublicImageActivity extends BaseActivity implements FlickrImageListPresenter.View, View.OnClickListener {

    private FlickrImageListPresenter mFlickrImageListPresenter;
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;
    RecyclerView recyclerView;
    TextView pageOne, pageTwo, pageThree;
    FlickrImageAdapter rcAdapter;
    List<FlickrImageDetails> fIDetailses = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        mFlickrImageListPresenter = new FlickerImageListImpl(
                this,
                new GetFlickerImagListInteractorImpl(this)
        );
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        pageOne = (TextView) findViewById(R.id.tv_page_one);
        pageTwo = (TextView) findViewById(R.id.tv_page_two);
        pageThree = (TextView) findViewById(R.id.tv_page_three);
        recyclerView.setHasFixedSize(true);
        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        rcAdapter = new FlickrImageAdapter(PublicImageActivity.this, fIDetailses);
        recyclerView.setAdapter(rcAdapter);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);
        pageOne.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        pageTwo.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        pageThree.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        recyclerView.setItemAnimator(null);
        pageOne.setOnClickListener(this);
        pageTwo.setOnClickListener(this);
        pageThree.setOnClickListener(this);
        if (CommonUtils.isOnline(PublicImageActivity.this)) {
            mFlickrImageListPresenter.getFlickerImageList(1);
        }
    }


    @Override
    public void onGetFlickerSuccessful(List<FlickrImageDetails> flickrImageDetailses) {
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
                if (CommonUtils.isOnline(PublicImageActivity.this)) {
                    mFlickrImageListPresenter.getFlickerImageList(1);
                    pageOne.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    pageTwo.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    pageThree.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                }

                break;
            case R.id.tv_page_two:
                if (CommonUtils.isOnline(PublicImageActivity.this)) {
                    mFlickrImageListPresenter.getFlickerImageList(2);
                    pageOne.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    pageTwo.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    pageThree.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                }
                break;
            case R.id.tv_page_three:
                if (CommonUtils.isOnline(PublicImageActivity.this)) {
                    mFlickrImageListPresenter.getFlickerImageList(3);
                    pageOne.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    pageTwo.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    pageThree.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }
                break;

        }
    }
}
