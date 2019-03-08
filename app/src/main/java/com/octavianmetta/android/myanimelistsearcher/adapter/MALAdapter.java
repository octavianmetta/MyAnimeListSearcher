package com.octavianmetta.android.myanimelistsearcher.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.octavianmetta.android.myanimelistsearcher.R;
import com.octavianmetta.android.myanimelistsearcher.databinding.RecyclerviewLayoutBinding;
import com.octavianmetta.android.myanimelistsearcher.models.MALSearchResponse;
import com.octavianmetta.android.myanimelistsearcher.models.MALResults;

import java.util.List;

import javax.xml.transform.Result;

public class MALAdapter extends RecyclerView.Adapter<MALAdapter.MALViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;

    private List<MALResults> malResultsList;
    private MALSearchResponse malSearchResponse;
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
        MALViewHolder viewHolder = null;
        if(layoutInflater == null){
            layoutInflater = layoutInflater.from(parent.getContext());
        }

        switch (viewType){
            case ITEM:
                RecyclerviewLayoutBinding binding = DataBindingUtil
                        .inflate(layoutInflater, R.layout.recyclerview_layout, parent,false);
                viewHolder = new MALViewHolder(binding);
                break;
            case LOADING:
                RecyclerviewLayoutBinding v2 = DataBindingUtil
                        .inflate(layoutInflater, R.layout.item_progress, parent,false);
                viewHolder = new MALViewHolder(v2);
                break;
        }
       return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MALViewHolder holder, int position) {
        MALResults malResults = malResultsList.get(position);
        switch (getItemViewType(position)){
            case ITEM:
                holder.bind(malResults);
                break;
            case LOADING:
                break;
        }

    }

    @Override
    public int getItemCount() {
        return malResultsList == null ? 0 : malResultsList.size();
    }

    public int getItemViewType(int position){
        return (position == malResultsList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void updateMALResults(List<MALResults> malSearchResults){
        this.malResultsList = malSearchResults;
        Log.d("Result", malResultsList.get(0).getTitle());
        notifyDataSetChanged();
    }

    public void add(MALResults r){
        malResultsList.add(r);
        notifyItemInserted(malResultsList.size() - 1);
    }

    public void addAll(List<MALResults> resultsList){
        for(MALResults malResults : resultsList){
            add(malResults);
        }
    }

    public void remove(MALResults r){
        int position = malResultsList.indexOf(r);
        if (position > -1){
            malResultsList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new MALResults());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = malResultsList.size() - 1;
        MALResults result = getItem(position);

        if (result != null) {
            malResultsList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public MALResults getItem(int position) {
        return malResultsList.get(position);
    }
}
