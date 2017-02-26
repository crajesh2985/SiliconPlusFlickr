package com.app.siliconplus.flickr.presenter.presenterinterface;


import com.app.siliconplus.flickr.appinterface.BaseView;
import com.app.siliconplus.flickr.model.FlickrImageDetails;

import java.util.List;

public interface FlickrImageListPresenter {

    interface View extends BaseView {
        void onGetFlickerSuccessful(List<FlickrImageDetails> flickrImageDetailses);
    }

    void getFlickerImageList(int pageNumber);


}
