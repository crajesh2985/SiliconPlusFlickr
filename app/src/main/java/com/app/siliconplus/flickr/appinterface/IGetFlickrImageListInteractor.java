package com.app.siliconplus.flickr.appinterface;


import com.app.siliconplus.flickr.model.FlickrImageDetails;

import java.util.List;

public interface IGetFlickrImageListInteractor {
    void getFlickerImageList(int pageNo, IGetFlickrImageListInteractor.Callback callback);

    interface Callback {
        void onSuccess(List<FlickrImageDetails> flickrImageDetailses);
        void onError(int code, String error);
    }
}
