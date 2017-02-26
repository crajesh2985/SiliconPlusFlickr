package com.app.siliconplus.flickr.appinterface;



public interface IGetOAuthInteractor {
    void getOAuthUserDetails(String frobvalue,IGetOAuthInteractor.Callback callback);

    interface Callback {
        void onSuccess(String response);
        void onError(int code, String error);
    }
}
