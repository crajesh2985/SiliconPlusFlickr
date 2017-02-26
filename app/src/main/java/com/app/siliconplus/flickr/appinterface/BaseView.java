package com.app.siliconplus.flickr.appinterface;


public interface BaseView {

    void showProgress();
    void hideProgress();
    void showError(int code, String message);
}
