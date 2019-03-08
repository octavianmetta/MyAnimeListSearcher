package com.octavianmetta.android.myanimelistsearcher.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.octavianmetta.android.myanimelistsearcher.DataBinderMapperImpl;
import com.octavianmetta.android.myanimelistsearcher.R;
import com.octavianmetta.android.myanimelistsearcher.adapter.MALAdapter;
import com.octavianmetta.android.myanimelistsearcher.databinding.ActivityMainBinding;
import com.octavianmetta.android.myanimelistsearcher.models.MALResponse;
import com.octavianmetta.android.myanimelistsearcher.models.MALResults;
import com.octavianmetta.android.myanimelistsearcher.viewModel.MALViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MALAdapter malAdapter;
    private ActivityMainBinding binding;
    private List<MALResults> malResultsList;
    private MALViewModel viewModel;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        malResultsList = new ArrayList<MALResults>();

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView = binding.recyclerview;
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(linearLayoutManager);

        viewModel = ViewModelProviders.of(this).get(MALViewModel.class);
        malAdapter = new MALAdapter(MainActivity.this, malResultsList);
        recyclerView.setAdapter(malAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem search = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                binding.itemProgressBar.setVisibility(View.VISIBLE);
                searchView.clearFocus();

                viewModel.getSearch(query).observe(MainActivity.this, new Observer<MALResponse>() {
                    @Override
                    public void onChanged(@Nullable MALResponse malResponses) {
                        Log.d("Diterima",malResponses.results.get(0).getTitle());
                        malAdapter.updateMAL(malResponses);
                        binding.itemProgressBar.setVisibility(View.INVISIBLE);
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
//TODO : Add search bar and clickable adapter