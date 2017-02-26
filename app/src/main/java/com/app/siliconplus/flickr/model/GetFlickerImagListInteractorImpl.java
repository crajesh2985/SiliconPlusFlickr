package com.app.siliconplus.flickr.model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.app.siliconplus.flickr.appconstant.AppConstant;
import com.app.siliconplus.flickr.appinterface.IGetFlickrImageListInteractor;
import com.app.siliconplus.flickr.utils.CommonUtils;
import com.app.siliconplus.flickr.utils.SharepreferenceKeystore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class GetFlickerImagListInteractorImpl implements IGetFlickrImageListInteractor {
    private Context context;
    private Callback callback;
    private SharepreferenceKeystore sharepreferenceKeystore;

    public GetFlickerImagListInteractorImpl(Context context) {
        this.context = context;
        sharepreferenceKeystore = SharepreferenceKeystore.getInstance(context);
    }

    @Override
    public void getFlickerImageList(int pageNum, Callback callback) {
        this.callback = callback;
        int page_num = pageNum;
        String signatureText = AppConstant.FLICKR_SECRET
                + AppConstant.API_KEY + AppConstant.FLICKR_KEY
                + AppConstant.FORMAT + AppConstant.JSON
                + AppConstant.METHOD + AppConstant.METHOD_GET_IMAGE_LIST
                + AppConstant.NO_JSON_CALLBACK +1
                + AppConstant.PAGE + page_num
                + AppConstant.PER_PAGE + 40
                + AppConstant.SEARCH_TEXT+"animal";
        String md5Signature = CommonUtils.getSignatureMD5(signatureText);
        String imageRequestUrl = AppConstant.BASE_URL+
                AppConstant.METHOD + "=" + AppConstant.METHOD_GET_IMAGE_LIST +
                "&"+AppConstant.NO_JSON_CALLBACK + "=1"+
                "&"+AppConstant.API_KEY+"="+AppConstant.FLICKR_KEY+
                "&"+AppConstant.FORMAT+"="+AppConstant.JSON+
                "&"+AppConstant.SEARCH_TEXT+"="+"animal"+
                "&"+AppConstant.PER_PAGE+"="+40+
                "&"+AppConstant.PAGE+"="+page_num+
                "&"+AppConstant.API_SIG+"="+md5Signature;
        new GetImageListOperation().execute(imageRequestUrl);

    }


    private class GetImageListOperation extends AsyncTask<String, Void, String> {
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
                    JSONArray jsonArray = obj.getJSONObject("photos").getJSONArray("photo");
                    List<FlickrImageDetails> flickrImageDetailses = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); ++i) {
                        FlickrImageDetails flickrImageDetails = new FlickrImageDetails();
                        JSONObject rec = jsonArray.getJSONObject(i);
                        String imgPath = "http://farm"+ rec.getInt("farm")+".staticflickr.com/"+rec.getString("server")+"/"+rec.getString("id")+"_"+rec.getString("secret")+"_m.jpg";
                        flickrImageDetails.setImagePath(imgPath);
                        flickrImageDetails.setImageTittle(rec.getString("server"));
                        flickrImageDetailses.add(flickrImageDetails);
                    }
                    callback.onSuccess(flickrImageDetailses);
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
