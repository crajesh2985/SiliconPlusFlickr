package com.app.siliconplus.flickr.appinterface;


public interface IGetFrobInteractor {
    void getFrobDetails(IGetFrobInteractor.Callback callback);

    interface Callback {
        void onSuccess(String frobValue);
        void onError(int code, String error);
    }
}
