package com.octavianmetta.android.myanimelistsearcher.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.octavianmetta.android.myanimelistsearcher.R;
import com.octavianmetta.android.myanimelistsearcher.activity.AnimeActivity;
import com.octavianmetta.android.myanimelistsearcher.databinding.RecyclerviewLayoutBinding;
import com.octavianmetta.android.myanimelistsearcher.models.MALSearchResponse;
import com.octavianmetta.android.myanimelistsearcher.models.MALResults;

import java.util.ArrayList;
import java.util.List;

public class MALAdapter extends RecyclerView.Adapter<MALAdapter.MALViewHolder> {

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
        RecyclerviewLayoutBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.recyclerview_layout, parent,false);
        viewHolder = new MALViewHolder(binding);
       return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MALViewHolder holder, final int position) {
        MALResults malResults = malResultsList.get(position);
        holder.bind(malResults);

        holder.binding.cvCardViewParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("RecyclerView", malResultsList.get(position).toString());

                Toast.makeText(mCtx,malResultsList.get(position).malId.toString(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mCtx, AnimeActivity.class);
                intent.putExtra("malId", malResultsList.get(position).malId);
                mCtx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return malResultsList == null ? 0 : malResultsList.size();
    }

    public void updateMALResults(List<MALResults> malSearchResults){
        this.malResultsList = malSearchResults;
        Log.d("Result", malResultsList.get(0).getTitle());
        notifyDataSetChanged();
    }

    public void clearResults(){
        this.malResultsList = new ArrayList<>();
        notifyDataSetChanged();
    }
}
