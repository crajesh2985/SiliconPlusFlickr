package com.app.siliconplus.flickr.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.siliconplus.flickr.R;
import com.app.siliconplus.flickr.model.FlickrImageDetails;
import com.app.siliconplus.flickr.utils.CommonUtils;
import com.app.siliconplus.flickr.utils.DownloadImageTask;
import com.app.siliconplus.flickr.view.FlickrImageViewHolder;
import com.app.siliconplus.flickr.view.UserAccountPhotoActivity;

import java.util.List;
import java.util.Random;


public class FlickrImageAdapter extends RecyclerView.Adapter<FlickrImageViewHolder> {

    private List<FlickrImageDetails> itemList;
    private Context context;

    private Random mRandom = new Random();

    public FlickrImageAdapter(Context context, List<FlickrImageDetails> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public FlickrImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R
                .layout.flickr_image_view, null);
        FlickrImageViewHolder rcv = new FlickrImageViewHolder(layoutView);
        return rcv;
    }

    public void updateRecord(List<FlickrImageDetails> itemList){
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(FlickrImageViewHolder holder, int position) {
        if (CommonUtils.isOnline(context)) {
            new DownloadImageTask(holder.flickrImage)
                    .execute(itemList.get(position).getImagePath());
        }
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}