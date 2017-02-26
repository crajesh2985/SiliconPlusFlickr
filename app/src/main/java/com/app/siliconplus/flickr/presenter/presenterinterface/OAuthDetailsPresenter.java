package com.app.siliconplus.flickr.presenter.presenterinterface;


import com.app.siliconplus.flickr.appinterface.BaseView;

public interface OAuthDetailsPresenter {

    interface View extends BaseView {
        void onGetFrobDetailSuccessful(String frobValue);
        void onGetOAuthUserDetailsSuccessful(String result);
    }

    void getFrobDetails();
    void getOAuthUserDetails(String frob);


}
