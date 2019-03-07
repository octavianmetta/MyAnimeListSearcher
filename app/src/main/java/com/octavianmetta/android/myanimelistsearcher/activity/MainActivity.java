package com.octavianmetta.android.myanimelistsearcher.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        malResultsList = new ArrayList<MALResults>();
        recyclerView = binding.recyclerview;
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MALViewModel viewModel = ViewModelProviders.of(this).get(MALViewModel.class);
        malAdapter = new MALAdapter(MainActivity.this, malResultsList);
        recyclerView.setAdapter(malAdapter);

        viewModel.getSearch().observe(this, new Observer<List<MALResponse>>() {
            @Override
            public void onChanged(@Nullable List<MALResponse> malResponse) {
                malAdapter.updateMAL(malResponse);
            }
        });
    }
}
