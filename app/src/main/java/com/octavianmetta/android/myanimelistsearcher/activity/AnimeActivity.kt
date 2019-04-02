package com.octavianmetta.android.myanimelistsearcher.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log

import com.octavianmetta.android.myanimelistsearcher.R
import com.octavianmetta.android.myanimelistsearcher.adapter.CharacterAdapter
import com.octavianmetta.android.myanimelistsearcher.databinding.ActivityAnimeBinding
import com.octavianmetta.android.myanimelistsearcher.models.anime.AnimeModel
import com.octavianmetta.android.myanimelistsearcher.models.anime.characterStaff.Character
import com.octavianmetta.android.myanimelistsearcher.viewModel.AnimeDetailViewModel
import com.octavianmetta.android.myanimelistsearcher.viewModel.CharacterViewModel

import java.util.ArrayList

class AnimeActivity : AppCompatActivity() {

    private var binding: ActivityAnimeBinding? = null
    private var animeViewModel: AnimeDetailViewModel? = null
    private var characterViewModel: CharacterViewModel? = null
    private var characterRecyclerView: RecyclerView? = null
    private var characterAdapter: CharacterAdapter? = null
    private var linearLayoutManager: LinearLayoutManager? = null
    private var characterList: List<Character>? = null

    private var malId: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent.hasExtra("malId")) {
            malId = intent.getIntExtra("malId", 0)
            Log.d("Intent diterima", malId.toString())

        }

        //Databinding
        binding = DataBindingUtil.setContentView<ActivityAnimeBinding>(this, R.layout.activity_anime)
        characterRecyclerView = binding!!.animeCharacter

        characterList = ArrayList()
        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        characterRecyclerView!!.layoutManager = linearLayoutManager

        animeViewModel = ViewModelProviders.of(this).get(AnimeDetailViewModel::class.java)
        characterViewModel = ViewModelProviders.of(this).get(CharacterViewModel::class.java)
        characterAdapter = CharacterAdapter(this@AnimeActivity, characterList)
        characterRecyclerView!!.adapter = characterAdapter

        animeViewModel!!.getAnimeData(malId)!!.observe(this@AnimeActivity, Observer { animeResponse ->
            Log.d("Diterima", animeResponse!!.title)
            binding!!.setAnime(animeResponse)
        })
        characterViewModel!!.getCharacterData(malId)!!.observe(this@AnimeActivity, Observer { characters -> characterAdapter!!.updateCharacter(characters) })

    }
}

//TODO: Design this activity