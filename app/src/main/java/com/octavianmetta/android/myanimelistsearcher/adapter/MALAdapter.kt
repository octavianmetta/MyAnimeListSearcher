package com.octavianmetta.android.myanimelistsearcher.adapter

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.octavianmetta.android.myanimelistsearcher.R
import com.octavianmetta.android.myanimelistsearcher.activity.AnimeActivity
import com.octavianmetta.android.myanimelistsearcher.databinding.RecyclerviewLayoutBinding
import com.octavianmetta.android.myanimelistsearcher.models.MALSearchResponse
import com.octavianmetta.android.myanimelistsearcher.models.MALResults

import java.util.ArrayList






class MALAdapter(private val mCtx: Context, var malResultsList: ArrayList<MALResults>)
    : RecyclerView.Adapter<MALAdapter.MALViewHolder>() {

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1


    inner class MALViewHolder(val binding: RecyclerviewLayoutBinding) : RecyclerView.ViewHolder(binding.getRoot()) {

        fun bind(malResults: MALResults) {
            binding.mal = malResults
            binding.executePendingBindings()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MALViewHolder {
        var viewHolder: MALViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)

        val binding = DataBindingUtil
                .inflate<RecyclerviewLayoutBinding>(inflater, R.layout.recyclerview_layout, parent, false)
        viewHolder = MALViewHolder(binding)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return malResultsList.size
    }

    override fun onBindViewHolder(holder: MALViewHolder, position: Int) {
        val malResults = malResultsList[position]
        holder.bind(malResults)

        holder.binding.cvCardViewParent.setOnClickListener(View.OnClickListener {
            Log.d("RecyclerView", malResultsList[position].toString())

            Toast.makeText(mCtx, malResultsList!![position].malId!!.toString(), Toast.LENGTH_SHORT).show()

            val intent = Intent(mCtx, AnimeActivity::class.java)
            intent.putExtra("malId", malResultsList!![position].malId)
            mCtx.startActivity(intent)
        })

    }

    fun updateMALResults(malSearchResults: ArrayList<MALResults>) {
        this.malResultsList = malSearchResults
        Log.d("Result", malResultsList!![0].title)
        notifyDataSetChanged()
    }

    fun clearResults() {
        this.malResultsList = ArrayList()
        notifyDataSetChanged()
    }

    fun addData(listItems: ArrayList<MALResults>) {
        var size = this.malResultsList.size
        this.malResultsList.addAll(listItems)
        var sizeNew = this.malResultsList.size
        notifyItemRangeChanged(size, sizeNew)
    }

}
