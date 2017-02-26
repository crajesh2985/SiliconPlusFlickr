package com.app.siliconplus.flickr.model;

import android.content.Context;
import android.os.AsyncTask;

import com.app.siliconplus.flickr.appconstant.AppConstant;
import com.app.siliconplus.flickr.appinterface.IGetFrobInteractor;
import com.app.siliconplus.flickr.appinterface.IGetOAuthInteractor;
import com.app.siliconplus.flickr.utils.CommonUtils;
import com.app.siliconplus.flickr.utils.SharepreferenceKeystore;

import org.json.JSONException;
import org.json.JSONObject;


public class GetOAuthInteractorImpl implements IGetOAuthInteractor {
    private Context context;
    private Callback callback;
    private SharepreferenceKeystore sharepreferenceKeystore;

    public GetOAuthInteractorImpl(Context context) {
        this.context = context;
        sharepreferenceKeystore = SharepreferenceKeystore.getInstance(context);
    }

    @Override
    public void getOAuthUserDetails(String frobvalue, Callback callback) {
        this.callback = callback;
        String signatureText = AppConstant.FLICKR_SECRET +
                AppConstant.API_KEY + AppConstant.FLICKR_KEY +
                AppConstant.FORMAT + AppConstant.JSON +
                AppConstant.FROB + frobvalue +
                AppConstant.METHOD +AppConstant.METHOD_GET_TOKEN +
                AppConstant.NO_JSON_CALLBACK +1;
        String md5Signature = CommonUtils.getSignatureMD5(signatureText);
        String frobRequestUrl = AppConstant.BASE_URL+
                AppConstant.METHOD + "=" + AppConstant.METHOD_GET_TOKEN +
                "&"+AppConstant.NO_JSON_CALLBACK + "=1"+
                "&"+AppConstant.API_KEY+"="+AppConstant.FLICKR_KEY+
                "&"+AppConstant.FORMAT+"="+AppConstant.JSON+
                "&"+AppConstant.FROB+"="+frobvalue+
                "&"+AppConstant.API_SIG+"="+md5Signature;
        new GetTokenOperation().execute(frobRequestUrl);
    }



    private class GetTokenOperation extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... url) {
            String response = null;
            response = CommonUtils.callHttpConnectivity(url[0], "GET");
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            if(result != null) {
                try {
                    final JSONObject obj = new JSONObject(result);
                    String userToken = obj.getJSONObject("auth").getJSONObject("token").getString("_content");
                    String userId = obj.getJSONObject("auth").getJSONObject("user").getString("nsid");
                    sharepreferenceKeystore.put("flickr_user_token", userToken);
                    sharepreferenceKeystore.put("flickr_user_id", userId);
                    callback.onSuccess(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }




}
