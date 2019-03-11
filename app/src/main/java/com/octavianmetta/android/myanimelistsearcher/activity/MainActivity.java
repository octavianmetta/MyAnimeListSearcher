package com.octavianmetta.android.myanimelistsearcher.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
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

import com.octavianmetta.android.myanimelistsearcher.R;
import com.octavianmetta.android.myanimelistsearcher.adapter.MALAdapter;
import com.octavianmetta.android.myanimelistsearcher.databinding.ActivityMainBinding;
import com.octavianmetta.android.myanimelistsearcher.models.MALResults;
import com.octavianmetta.android.myanimelistsearcher.viewModel.MALSearchViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Deklarasi variabel
    private RecyclerView recyclerView;
    private MALAdapter malAdapter;
    public ActivityMainBinding binding;
    private List<MALResults> malResultsList;
    private MALSearchViewModel viewModel;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Databinding dengan layout dan recycler view
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        recyclerView = binding.recyclerview;

        //Init recyclerview
        malResultsList = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(linearLayoutManager);

        viewModel = ViewModelProviders.of(this).get(MALSearchViewModel.class);
        malAdapter = new MALAdapter(MainActivity.this, malResultsList);
        recyclerView.setAdapter(malAdapter);

        //Panggil getMALData untuk dapat top airing anime dan observe
        viewModel.getMALData().observe(MainActivity.this, new Observer<List<MALResults>>() {
            @Override
            public void onChanged(@Nullable List<MALResults> malResultsList) {

                //Jika MALResults berubah, update ke adapter
                Log.d("Diterima", malResultsList.get(0).getTitle());
                malAdapter.updateMALResults(malResultsList);
                binding.itemProgressBar.setVisibility(View.INVISIBLE);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Tampilkan search pada actionbar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem search = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                //saat enter dutekan setelah search, panggil loadMALSearch untuk dapat hasil search
                binding.itemProgressBar.setVisibility(View.VISIBLE);
                searchView.clearFocus();
                malAdapter.clearResults();
                viewModel.loadMALSearch(query);

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
//TODO : pagination