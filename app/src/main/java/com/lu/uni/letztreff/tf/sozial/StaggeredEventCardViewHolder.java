package com.lu.uni.letztreff.tf.sozial;

import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.lu.uni.letztreff.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StaggeredEventCardViewHolder extends RecyclerView.ViewHolder {

    public NetworkImageView productImage;
    public TextView productTitle;
    public TextView productPrice;

    StaggeredEventCardViewHolder(@NonNull View itemView) {
        super(itemView);
        productImage = itemView.findViewById(R.id.event_image);
        productTitle = itemView.findViewById(R.id.event_title);
        productPrice = itemView.findViewById(R.id.event_price);
    }
}
