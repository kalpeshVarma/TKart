package com.kalpv.t_kart;

import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kalpv.t_kart.Model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    private List<Product> productList;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_BODY = 1;
    private int width, height;

    public ProductAdapter(List<Product> productList, int width, int height) {
        this.productList = productList;
        this.width = width;
        this.height = height;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        
            Product product = this.productList.get(position);

            ViewGroup.LayoutParams layoutParams = holder.iv_product.getLayoutParams();
        layoutParams.width = width/2;
        layoutParams.height = height/3;
        holder.iv_product.setLayoutParams(layoutParams);

            Picasso.get().load("https://images-na.ssl-images-amazon.com/images/I/71mIo-rNNeL._UL1326_.jpg")
                    .into(holder.iv_product);
            holder.tv_productBrand.setText(product.getProductName());
            holder.tv_productDescription.setText(product.getProductDescription());
            holder.tv_productPrice.setText(product.getProductPrice());
            holder.tv_productPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tv_productOfferPrice.setText(product.getProductOfferPrice());
        }


    @Override
    public int getItemCount() {
        return productList.size();
    }
}
