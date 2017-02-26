package com.app.siliconplus.flickr.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.siliconplus.flickr.R;
import com.app.siliconplus.flickr.model.GetFrobInteractorImpl;
import com.app.siliconplus.flickr.model.GetOAuthInteractorImpl;
import com.app.siliconplus.flickr.presenter.presenterimpl.OAuthPresenterImpl;
import com.app.siliconplus.flickr.presenter.presenterinterface.OAuthDetailsPresenter;
import com.app.siliconplus.flickr.utils.CommonUtils;

public class LoginActivity extends BaseActivity implements View.OnClickListener, OAuthDetailsPresenter.View  {
    private TextView tvLogin, tvImageInPublic, tvImageInAccount;
    private OAuthDetailsPresenter mOAuthDetailsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mOAuthDetailsPresenter = new OAuthPresenterImpl(
                this,
                new GetFrobInteractorImpl(this),
                new GetOAuthInteractorImpl(this)
        );
        tvLogin = (TextView)findViewById(R.id.tv_login);
        tvImageInPublic  = (TextView)findViewById(R.id.tv_public_image);
        tvImageInAccount = (TextView)findViewById(R.id.tv_account_image);
        tvLogin.setOnClickListener(this);
        tvImageInPublic.setOnClickListener(this);
        tvImageInAccount.setOnClickListener(this);
        if(sharepreferenceKeystore.get("flickr_user_token") !=null
                && sharepreferenceKeystore.get("flickr_user_token").length()>3){
            tvLogin.setText(R.string.str_logout);
            tvImageInAccount.setVisibility(View.VISIBLE);
        }else{
            tvLogin.setText(R.string.str_login);
            tvImageInAccount.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_login:
                checkAuthendication();
                break;
            case R.id.tv_public_image:
                if (CommonUtils.isOnline(LoginActivity.this)) {
                    Intent intent = new Intent(LoginActivity.this, PublicImageActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.tv_account_image:
                if (CommonUtils.isOnline(LoginActivity.this)) {
                    Intent intentUserAccount = new Intent(LoginActivity.this, UserAccountPhotoActivity.class);
                    startActivity(intentUserAccount);
                }
                break;
        }
    }

    private void checkAuthendication() {
        if(sharepreferenceKeystore.get("flickr_user_token") !=null
                && sharepreferenceKeystore.get("flickr_user_token").length()>3){
            tvLogin.setText(R.string.str_login);
            sharepreferenceKeystore.clear();
            tvImageInAccount.setVisibility(View.GONE);
        }else{
            if (CommonUtils.isOnline(LoginActivity.this)) {
                mOAuthDetailsPresenter.getFrobDetails();
            }
        }
    }

    @Override
    public void onGetFrobDetailSuccessful(String frobValue) {
        if (CommonUtils.isOnline(LoginActivity.this)) {
            redirectToAuthentication(frobValue);
        }
    }

    @Override
    public void onGetOAuthUserDetailsSuccessful(String response) {
        tvLogin.setText(R.string.str_logout);
        tvImageInAccount.setVisibility(View.VISIBLE);
    }

    private void redirectToAuthentication(String frobValue) {
        if(frobValue !=null && frobValue.length() >1){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(CommonUtils.authenticationURL(frobValue))));
        }
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
    protected void onResume() {
        super.onResume();
        if(sharepreferenceKeystore.get("flickr_user_token") !=null
                && sharepreferenceKeystore.get("flickr_user_token").length()>3){
            tvLogin.setText(R.string.str_logout);
            tvImageInAccount.setVisibility(View.VISIBLE);
        }else{
            if(sharepreferenceKeystore.get("flickr_frob") !=null
                    && sharepreferenceKeystore.get("flickr_frob").length()>3){
                if (CommonUtils.isOnline(LoginActivity.this)) {
                    mOAuthDetailsPresenter.getOAuthUserDetails(sharepreferenceKeystore.get("flickr_frob"));
                }
            }else{
                tvLogin.setText(R.string.str_login);
            }
        }
    }
}
