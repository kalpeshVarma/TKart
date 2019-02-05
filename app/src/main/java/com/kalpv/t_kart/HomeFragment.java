package com.kalpv.t_kart;


import android.graphics.Point;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kalpv.t_kart.Model.Category;
import com.kalpv.t_kart.Model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    int scrollPosition;
    boolean flag = false;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //Header Recycler view setup
        final List<Category> categoryList = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            Category category = new Category(UUID.randomUUID().toString(), "Title " + i, "https://storiesflistgv2.blob.core.windows.net/stories/2016/09/daily_offers_banner_Final.jpg");
            categoryList.add(category);
        }
        final CategoryAdapter categoryAdapter = new CategoryAdapter(categoryList);
        final ScrollingPagerIndicator scrollingPagerIndicator = view.findViewById(R.id.scrollIndicator);
        final RecyclerView rv_slider = view.findViewById(R.id.rv_slider);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rv_slider);
        rv_slider.setLayoutManager(layoutManager);
        rv_slider.setAdapter(categoryAdapter);
        scrollingPagerIndicator.attachToRecyclerView(rv_slider);
        autoScroll(categoryAdapter, rv_slider);


        //Product Recyclerview setup
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int windowWidth = size.x;
        int windowHeight = size.y;
        List<Product> productList = new ArrayList<>();
        for (int i = 0; i <= 15; i++) {
            Product product = new Product(UUID.randomUUID().toString(), "LEVIS",
                    "Round neck tshirt", "259", "399");
            productList.add(product);
        }

        RecyclerView rv_tsirts = view.findViewById(R.id.rv_tshirts);

        ProductAdapter productAdapter = new ProductAdapter(productList, windowWidth, windowHeight);

        RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(getActivity(), 2);
//        rv_tsirts.addItemDecoration(new GridSpacingItemDecoration(2,20,true));
        rv_tsirts.setLayoutManager(layoutManager1);
        rv_tsirts.setAdapter(productAdapter);

//        rv_slider.setNestedScrollingEnabled(false);
//        rv_tsirts.setNestedScrollingEnabled(false);
//
//        rv_slider.setHasFixedSize(false);
//        rv_tsirts.setHasFixedSize(false);

        return view;
    }

    public void autoScroll(final RecyclerView.Adapter adapter, final RecyclerView recyclerView) {
        final int speedScroll = 5000;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int count = 0;

            @Override
            public void run() {
                if (count == adapter.getItemCount())
                    count = 0;
                if (count < adapter.getItemCount()) {
                    recyclerView.smoothScrollToPosition(++count);
                    handler.postDelayed(this, speedScroll);
                }
            }
        };
        handler.postDelayed(runnable, speedScroll);
    }

}
