package com.kalpv.t_kart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kalpv.t_kart.Model.Category;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {
    List<Category> categoryList;

    public CategoryAdapter(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = this.categoryList.get(position);
        Picasso.get().load(category.getBannerUrl()).fit().into(holder.iv_categoryBanner);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
