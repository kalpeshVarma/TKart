package com.kalpv.t_kart;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class ProductViewHolder extends RecyclerView.ViewHolder {
    ImageView iv_product;
    TextView tv_productBrand, tv_productDescription, tv_productOfferPrice, tv_productPrice;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        iv_product = itemView.findViewById(R.id.iv_product);
        iv_product = itemView.findViewById(R.id.iv_product);
        tv_productBrand = itemView.findViewById(R.id.tv_prouctBrand);
        tv_productDescription = itemView.findViewById(R.id.tv_productDescription);
        tv_productOfferPrice = itemView.findViewById(R.id.tv_productOfferPrice);
        tv_productPrice = itemView.findViewById(R.id.tv_productPrice);
    }
}
