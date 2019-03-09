package com.octavianmetta.android.myanimelistsearcher.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.octavianmetta.android.myanimelistsearcher.R;
import com.octavianmetta.android.myanimelistsearcher.databinding.ActivityAnimeBinding;
import com.octavianmetta.android.myanimelistsearcher.models.anime.AnimeModel;
import com.octavianmetta.android.myanimelistsearcher.viewModel.AnimeDetailViewModel;

public class AnimeActivity extends AppCompatActivity {

    private ActivityAnimeBinding binding;
    private AnimeDetailViewModel viewModel;

    private int malId;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getIntent().hasExtra("malId")){
            malId = getIntent().getIntExtra("malId",0);
        }

        AnimeModel animeModel = new AnimeModel();

        binding = DataBindingUtil.setContentView(this,R.layout.activity_anime);

        viewModel = ViewModelProviders.of(this).get(AnimeDetailViewModel.class);

        viewModel.getAnimeData(malId).observe(AnimeActivity.this, new Observer<AnimeModel>() {
            @Override
            public void onChanged(@Nullable AnimeModel animeResponse) {
                Log.d("Diterima",animeResponse.getTitle());
                binding.setAnime(animeResponse);
            }
        });
    }
}

//TODO: Design this activity