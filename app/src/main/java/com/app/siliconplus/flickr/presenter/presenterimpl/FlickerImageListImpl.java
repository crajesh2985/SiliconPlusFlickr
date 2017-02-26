package com.app.siliconplus.flickr.presenter.presenterimpl;

import com.app.siliconplus.flickr.appinterface.IGetFlickrImageListInteractor;
import com.app.siliconplus.flickr.appinterface.IGetFrobInteractor;
import com.app.siliconplus.flickr.appinterface.IGetOAuthInteractor;
import com.app.siliconplus.flickr.model.FlickrImageDetails;
import com.app.siliconplus.flickr.presenter.presenterinterface.FlickrImageListPresenter;
import com.app.siliconplus.flickr.presenter.presenterinterface.OAuthDetailsPresenter;

import java.util.List;


public class FlickerImageListImpl implements FlickrImageListPresenter {
    private IGetFlickrImageListInteractor mIGetFlickrImageListInteractor;

    private View mView;

    public FlickerImageListImpl(View view, IGetFlickrImageListInteractor getFlickrImageListInteractor) {
        this.mView = view;
        mIGetFlickrImageListInteractor = getFlickrImageListInteractor;
    }


    @Override
    public void getFlickerImageList(int pageNumber) {
        mIGetFlickrImageListInteractor.getFlickerImageList( pageNumber, new IGetFlickrImageListInteractor.Callback() {
            @Override
            public void onSuccess(List<FlickrImageDetails> flickrImageDetailses) {
                mView.onGetFlickerSuccessful(flickrImageDetailses);
            }

            @Override
            public void onError(int code, String error) {

            }
        });
    }


}
