package com.kalpv.t_kart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.kalpv.t_kart.Model.Category;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout fragment_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.home);
        }


//        CategoryAdapter categoryAdapter = new CategoryAdapter(categories);
        bottomNavigationView = findViewById(R.id.bnv);
        fragment_container = findViewById(R.id.frame_container);
        loadFragment(new HomeFragment());
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search products");
        searchView.setIconified(true);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bottom_menu_home:
                if (getSupportActionBar() != null)
                    getSupportActionBar().setTitle(R.string.home);
                loadFragment(new HomeFragment());
                return true;
            case R.id.bottom_menu_categories:
                if (getSupportActionBar() != null)
                    getSupportActionBar().setTitle(R.string.categories);
                loadFragment(new CategoriesFragment());
                return true;
            case R.id.bottom_menu_cart:
                if (getSupportActionBar() != null)
                    getSupportActionBar().setTitle(R.string.cart);
                loadFragment(new CartFragment());
                return true;
            case R.id.bottom_menu_account:
                if (getSupportActionBar() != null)
                    getSupportActionBar().setTitle(R.string.my_account);
                loadFragment(new AccountFragment());
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if(bottomNavigationView.getSelectedItemId() == R.id.bottom_menu_home)
            super.onBackPressed();
        else
            bottomNavigationView.setSelectedItemId(R.id.bottom_menu_home);

    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();


    }
}
