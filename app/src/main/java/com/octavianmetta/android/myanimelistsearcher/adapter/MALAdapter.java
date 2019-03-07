package com.octavianmetta.android.myanimelistsearcher.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.octavianmetta.android.myanimelistsearcher.R;
import com.octavianmetta.android.myanimelistsearcher.databinding.RecyclerviewLayoutBinding;
import com.octavianmetta.android.myanimelistsearcher.models.MALResponse;
import com.octavianmetta.android.myanimelistsearcher.models.MALResults;

import java.util.List;

public class MALAdapter extends RecyclerView.Adapter<MALAdapter.MALViewHolder> {

    private List<MALResults> malResultsList;
    private MALResponse malResponse;
    private LayoutInflater layoutInflater;
    private Context mCtx;

    public MALAdapter(Context mCtx, List<MALResults> MALList){
        this.mCtx = mCtx;
        this.malResultsList = MALList;
    }

    public class MALViewHolder extends RecyclerView.ViewHolder {

        private final RecyclerviewLayoutBinding binding;

        public MALViewHolder(final RecyclerviewLayoutBinding itemBinding){
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }

        public void bind (MALResults malResults){
            binding.setMAL(malResults);
            binding.executePendingBindings();
        }
    }

    @NonNull
    @Override
    public MALViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(layoutInflater == null){
            layoutInflater = layoutInflater.from(parent.getContext());
        }
        RecyclerviewLayoutBinding binding = DataBindingUtil
                .inflate(layoutInflater, R.layout.recyclerview_layout, parent,false);
        return new MALViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MALViewHolder holder, int position) {
        MALResults malResults = malResultsList.get(position);
        holder.bind(malResults);
    }

    @Override
    public int getItemCount() {
        return malResultsList.size();
    }

    public void updateMAL(MALResponse malResponses){
        this.malResponse = malResponses;

        if (malResponse != null){
            List<MALResults> malResults = malResponses.getResults();
            this.malResultsList = malResults;
        }

        notifyDataSetChanged();
    }
}
