package com.octavianmetta.android.myanimelistsearcher.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.octavianmetta.android.myanimelistsearcher.R;
import com.octavianmetta.android.myanimelistsearcher.adapter.CharacterAdapter;
import com.octavianmetta.android.myanimelistsearcher.databinding.ActivityAnimeBinding;
import com.octavianmetta.android.myanimelistsearcher.models.anime.AnimeModel;
import com.octavianmetta.android.myanimelistsearcher.models.anime.characterStaff.Character;
import com.octavianmetta.android.myanimelistsearcher.viewModel.AnimeDetailViewModel;
import com.octavianmetta.android.myanimelistsearcher.viewModel.CharacterViewModel;

import java.util.ArrayList;
import java.util.List;

public class AnimeActivity extends AppCompatActivity {

    private ActivityAnimeBinding binding;
    private AnimeDetailViewModel animeViewModel;
    private CharacterViewModel characterViewModel;
    private RecyclerView characterRecyclerView;
    private CharacterAdapter characterAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<Character> characterList;

    private int malId;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getIntent().hasExtra("malId")){
            malId = getIntent().getIntExtra("malId",0);
        }

        AnimeModel animeModel = new AnimeModel();
        characterList = new ArrayList<>();

        binding = DataBindingUtil.setContentView(this,R.layout.activity_anime);
        characterRecyclerView = binding.animeCharacter;

        characterRecyclerView.setLayoutManager(linearLayoutManager);
        animeViewModel = ViewModelProviders.of(this).get(AnimeDetailViewModel.class);
        characterViewModel = ViewModelProviders.of(this).get(CharacterViewModel.class);

        characterAdapter = new CharacterAdapter(AnimeActivity.this, characterList);
        characterRecyclerView.setAdapter(characterAdapter);

        animeViewModel.getAnimeData(malId).observe(AnimeActivity.this, new Observer<AnimeModel>() {
            @Override
            public void onChanged(@Nullable AnimeModel animeResponse) {
                Log.d("Diterima",animeResponse.getTitle());
                binding.setAnime(animeResponse);
            }
        });
        characterViewModel.getCharacterData(malId).observe(AnimeActivity.this, new Observer<List<Character>>() {
            @Override
            public void onChanged(@Nullable List<Character> characters) {
                characterAdapter.updateCharacter(characters);

            }
        });

    }
}

//TODO: Design this activity