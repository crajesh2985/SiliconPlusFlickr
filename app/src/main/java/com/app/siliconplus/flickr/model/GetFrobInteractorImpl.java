package com.app.siliconplus.flickr.model;

import android.content.Context;
import android.os.AsyncTask;

import com.app.siliconplus.flickr.appconstant.AppConstant;
import com.app.siliconplus.flickr.appinterface.IGetFrobInteractor;
import com.app.siliconplus.flickr.utils.CommonUtils;
import com.app.siliconplus.flickr.utils.SharepreferenceKeystore;

import org.json.JSONException;
import org.json.JSONObject;


public class GetFrobInteractorImpl implements IGetFrobInteractor {
    private Context context;
    private Callback callback;
    private SharepreferenceKeystore sharepreferenceKeystore;

    public GetFrobInteractorImpl(Context context) {
        this.context = context;
        sharepreferenceKeystore = SharepreferenceKeystore.getInstance(context);
    }

    @Override
    public void getFrobDetails(Callback callback) {
        this.callback = callback;
        String signatureText = AppConstant.FLICKR_SECRET +
                AppConstant.API_KEY + AppConstant.FLICKR_KEY +
                AppConstant.FORMAT + AppConstant.JSON +
                AppConstant.METHOD +AppConstant.METHOD_GET_FROB +
                AppConstant.NO_JSON_CALLBACK +1;
        String md5Signature = CommonUtils.getSignatureMD5(signatureText);
        String frobRequestUrl = AppConstant.BASE_URL+
                AppConstant.METHOD + "=" + AppConstant.METHOD_GET_FROB +
                "&"+AppConstant.NO_JSON_CALLBACK + "=1"+
                "&"+AppConstant.API_KEY+"="+AppConstant.FLICKR_KEY+
                "&"+AppConstant.FORMAT+"="+AppConstant.JSON+
                "&"+AppConstant.API_SIG+"="+md5Signature;
        new GetFrobOperation().execute(frobRequestUrl);
    }

    private class GetFrobOperation extends AsyncTask<String, Void, String> {
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
                    String frobValue = obj.getJSONObject("frob").getString("_content");
                    sharepreferenceKeystore.put("flickr_frob", frobValue);
                    callback.onSuccess(frobValue);
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
