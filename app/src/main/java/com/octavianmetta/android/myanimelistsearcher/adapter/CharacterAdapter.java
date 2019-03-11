package com.octavianmetta.android.myanimelistsearcher.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.octavianmetta.android.myanimelistsearcher.R;
import com.octavianmetta.android.myanimelistsearcher.databinding.CharacterrvLayoutBinding;
import com.octavianmetta.android.myanimelistsearcher.databinding.RecyclerviewLayoutBinding;
import com.octavianmetta.android.myanimelistsearcher.models.anime.characterStaff.Character;
import com.octavianmetta.android.myanimelistsearcher.models.anime.characterStaff.CharacterStaff;

import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> {
    private List<Character> characterList;
    private CharacterStaff characterStaff;
    private LayoutInflater layoutInflater;
    private Context context;

    public CharacterAdapter(Context context, List<Character> characterList){
        this.characterList = characterList;
        this.context = context;
    }

    public class CharacterViewHolder extends RecyclerView.ViewHolder{
        private final CharacterrvLayoutBinding binding;

        public CharacterViewHolder(final CharacterrvLayoutBinding itemBinding){
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }

        public void bind (Character character){
            binding.setCharacters(character);
            binding.executePendingBindings();
        }
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CharacterViewHolder viewHolder = null;
        if (layoutInflater == null){
            layoutInflater = layoutInflater.from(parent.getContext());
        }
        CharacterrvLayoutBinding binding = DataBindingUtil
                .inflate(layoutInflater, R.layout.characterrv_layout, parent, false);
        viewHolder = new CharacterViewHolder(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder characterViewHolder, int position) {
        Character character = characterList.get(position);
        characterViewHolder.bind(character);

    }

    @Override
    public int getItemCount() {
        return characterList == null ? 0 : characterList.size();
    }

    public void updateCharacter(List<Character> characters){
        this.characterList = characters;
        Toast.makeText(context,this.characterList.get(0).getName(),Toast.LENGTH_SHORT).show();
        notifyDataSetChanged();
    }
}
