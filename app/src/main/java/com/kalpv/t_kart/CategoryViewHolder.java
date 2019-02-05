package com.kalpv.t_kart;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class CategoryViewHolder extends RecyclerView.ViewHolder {
    ImageView iv_categoryBanner;
    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        iv_categoryBanner = itemView.findViewById(R.id.iv_categoryBanner);
    }
}
