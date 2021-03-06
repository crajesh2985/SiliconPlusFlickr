package com.app.siliconplus.flickr.view;


import android.support.v7.widget.RecyclerView;
        import android.view.View;
        import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
        import android.widget.Toast;

import com.app.siliconplus.flickr.R;

public class FlickrImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView imageName;
    public ImageView flickrImage;

    public FlickrImageViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        flickrImage = (ImageView) itemView.findViewById(R.id.country_photo);
    }

    @Override
    public void onClick(View view) {
       // Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }
}