package com.app.siliconplus.flickr.presenter.presenterimpl;

import com.app.siliconplus.flickr.appinterface.IGetFrobInteractor;
import com.app.siliconplus.flickr.appinterface.IGetOAuthInteractor;
import com.app.siliconplus.flickr.presenter.presenterinterface.OAuthDetailsPresenter;


public class OAuthPresenterImpl implements OAuthDetailsPresenter {
    private IGetFrobInteractor mGetFrobInteractor;
    private IGetOAuthInteractor mGetOAuthInteractor;

    private OAuthDetailsPresenter.View mView;

    public OAuthPresenterImpl(View view, IGetFrobInteractor getFrobInteractor, IGetOAuthInteractor getOAuthInteractor) {
        this.mView = view;
        mGetFrobInteractor = getFrobInteractor;
        mGetOAuthInteractor = getOAuthInteractor;
    }


    @Override
    public void getFrobDetails() {
        mGetFrobInteractor.getFrobDetails(new IGetFrobInteractor.Callback() {
            @Override
            public void onSuccess(String frobValue) {
                mView.onGetFrobDetailSuccessful(frobValue);
            }

            @Override
            public void onError(int code, String error) {

            }
        });
    }

    @Override
    public void getOAuthUserDetails(String frob) {

        mGetOAuthInteractor.getOAuthUserDetails(frob, new IGetOAuthInteractor.Callback() {
            @Override
            public void onSuccess(String response) {
                mView.onGetOAuthUserDetailsSuccessful(response);
            }

            @Override
            public void onError(int code, String error) {

            }
        });
    }
}
