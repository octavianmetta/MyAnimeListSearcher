package com.octavianmetta.android.myanimelistsearcher.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.octavianmetta.android.myanimelistsearcher.R;
import com.octavianmetta.android.myanimelistsearcher.adapter.EndlessRecyclerViewScrollListener;
import com.octavianmetta.android.myanimelistsearcher.adapter.MALAdapter;
import com.octavianmetta.android.myanimelistsearcher.databinding.ActivityMainBinding;
import com.octavianmetta.android.myanimelistsearcher.models.MALSearchResponse;
import com.octavianmetta.android.myanimelistsearcher.models.MALResults;
import com.octavianmetta.android.myanimelistsearcher.viewModel.MALViewModel;
import com.paginate.Paginate;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    //Deklarasi variabel
    private RecyclerView recyclerView;
    private MALAdapter malAdapter;
    public ActivityMainBinding binding;
    private List<MALResults> malResultsList;
    private MALViewModel viewModel;
    private LinearLayoutManager linearLayoutManager;
    private ProgressBar progressBar;

    private boolean searchReset = true;
    private static final int PAGE_START = 1;
    private int TOTAL_PAGES = 5;
    private int currentPage = PAGE_START;

    private int alterList = 3;
    private static final int ADD = 0;
    private static final int ADD_ALL = 1;
    private static final int REMOVE = 2;
    private static final int UPDATE_ALL = 3;




    private boolean isLoading = false;
    private boolean isLastPage = false;

    private String searchQuery;
    private EndlessRecyclerViewScrollListener scrollListener;


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
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        viewModel = ViewModelProviders.of(this).get(MALViewModel.class);
        malAdapter = new MALAdapter(MainActivity.this, malResultsList);
        recyclerView.setAdapter(malAdapter);

        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                loadNextPage();
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });



        //Panggil getMALData untuk dapat top airing anime dan observe
        viewModel.getMALData(currentPage).observe(MainActivity.this, new Observer<List<MALResults>>() {
            @Override
            public void onChanged(@Nullable List<MALResults> malResultsList) {

                //Jika MALResults berubah, update ke adapter
                Log.d("Diterima", malResultsList.get(0).getTitle());
                malAdapter.updateMALResults(malResultsList);
                switch (alterList){
                    case UPDATE_ALL:
                        malAdapter.updateMALResults(malResultsList);
                        break;
                    case ADD_ALL:
                        malAdapter.addAll(malResultsList);
                        if (currentPage <= TOTAL_PAGES) malAdapter.addLoadingFooter();
                        else isLastPage = true;

                }

            }
        });
    }

    private void loadNextPage(){
        Log.d("Main Activity", "loadNextPage: " + currentPage);
        malAdapter.removeLoadingFooter();
        isLoading = false;
        alterList = ADD_ALL;
        viewModel.loadMALSearch(searchQuery,currentPage);


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
                viewModel.loadMALSearch(query, currentPage);
                binding.itemProgressBar.setVisibility(View.GONE);

                searchView.clearFocus();
                malAdapter.notifyDataSetChanged();


                searchReset = false;
                searchQuery = query;


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
//TODO : clickable adapter