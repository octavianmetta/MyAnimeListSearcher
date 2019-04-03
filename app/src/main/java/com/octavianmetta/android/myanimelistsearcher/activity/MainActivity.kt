package com.octavianmetta.android.myanimelistsearcher.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View

import com.octavianmetta.android.myanimelistsearcher.R
import com.octavianmetta.android.myanimelistsearcher.adapter.MALAdapter
import com.octavianmetta.android.myanimelistsearcher.adapter.PaginationScrollListener
import com.octavianmetta.android.myanimelistsearcher.databinding.ActivityMainBinding
import com.octavianmetta.android.myanimelistsearcher.models.MALResults
import com.octavianmetta.android.myanimelistsearcher.viewModel.MALSearchViewModel

import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    //Deklarasi variabel
    private var recyclerView: RecyclerView? = null
    private var malAdapter: MALAdapter? = null
    lateinit var binding: ActivityMainBinding
    private var malResultsList: ArrayList<MALResults>? = ArrayList()
    private var viewModel: MALSearchViewModel? = null
    private var linearLayoutManager: LinearLayoutManager? = null
    var isLastPage: Boolean = false
    var isLoading: Boolean = false
    var page: Int = 1
    var searchQuery: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Databinding dengan layout dan recycler view
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        recyclerView = binding.recyclerview

        //Init recyclerview
        malResultsList = ArrayList()
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView!!.setHasFixedSize(false)
        recyclerView!!.layoutManager = linearLayoutManager

        viewModel = ViewModelProviders.of(this).get(MALSearchViewModel::class.java!!)
        malAdapter = MALAdapter(this@MainActivity, malResultsList!!)
        recyclerView!!.adapter = malAdapter

        binding.itemProgressBar.setVisibility(View.VISIBLE)

        recyclerView?.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager!!){
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                //you have to call loadmore items to get more data
                getMoreItems()
            }
        })


        //Panggil getMALData untuk dapat top airing anime dan observe
        viewModel!!.malData!!.observe(this@MainActivity, Observer { malResultsList ->
            //Jika MALResults berubah, update ke adapter
            Log.d("Diterima", malResultsList!![0].title)
            malAdapter!!.addData(malResultsList)
            //malAdapter!!.updateMALResults(malResultsList)

            binding.itemProgressBar.setVisibility(View.INVISIBLE)
        })
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        //Tampilkan search pada actionbar
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)
        val search = menu.findItem(R.id.search)
        val searchView = search.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                //saat enter dutekan setelah search, panggil loadMALSearch untuk dapat hasil search
                binding.itemProgressBar.setVisibility(View.VISIBLE)
                searchView.clearFocus()
                malAdapter!!.clearResults()
                searchQuery = query
                page = 1
                viewModel!!.loadMALSearch(query, page)

                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    fun getMoreItems() {
        //after fetching your data assuming you have fetched list in your
        // recyclerview adapter assuming your recyclerview adapter is
        //rvAdapter
        //after getting your data you have to assign false to isLoading
        isLoading = false
        if (searchQuery.length < 3){
            viewModel!!.initMALData(++page)
        }else {
            viewModel!!.loadMALSearch(searchQuery, ++page)
        }
    }

}
//TODO : pagination