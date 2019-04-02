package com.octavianmetta.android.myanimelistsearcher.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.provider.ContactsContract
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast

import com.octavianmetta.android.myanimelistsearcher.R
import com.octavianmetta.android.myanimelistsearcher.databinding.CharacterrvLayoutBinding
import com.octavianmetta.android.myanimelistsearcher.databinding.RecyclerviewLayoutBinding
import com.octavianmetta.android.myanimelistsearcher.models.anime.characterStaff.Character
import com.octavianmetta.android.myanimelistsearcher.models.anime.characterStaff.CharacterStaff

class CharacterAdapter(private val context: Context, private var characterList: List<Character>?)
    : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    inner class CharacterViewHolder(val binding: CharacterrvLayoutBinding) : RecyclerView.ViewHolder(binding.getRoot()) {

        fun bind(character: Character) {
            binding.setCharacters(character)
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        var viewHolder: CharacterViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)

        val binding = DataBindingUtil
                .inflate<CharacterrvLayoutBinding>(inflater, R.layout.characterrv_layout, parent, false)
        viewHolder = CharacterViewHolder(binding)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return characterList!!.size
    }

    override fun onBindViewHolder(characterViewHolder: CharacterViewHolder, position: Int) {
        val character = characterList!![position]
        characterViewHolder.bind(character)

    }

    fun updateCharacter(characters: List<Character>?) {
        this.characterList = characters
        Toast.makeText(context, this.characterList!![0].name, Toast.LENGTH_SHORT).show()
        notifyDataSetChanged()
    }
}
