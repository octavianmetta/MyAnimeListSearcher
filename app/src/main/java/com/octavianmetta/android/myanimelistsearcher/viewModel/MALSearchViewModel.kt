package com.octavianmetta.android.myanimelistsearcher.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log

import com.octavianmetta.android.myanimelistsearcher.models.MALSearchResponse
import com.octavianmetta.android.myanimelistsearcher.models.MALResults
import com.octavianmetta.android.myanimelistsearcher.models.MALTopResponse
import com.octavianmetta.android.myanimelistsearcher.rest.APIService
import com.octavianmetta.android.myanimelistsearcher.rest.RetrofitClient

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MALSearchViewModel : ViewModel() {

    //Init Retrofit
    private val retrofit = RetrofitClient.getClient(APIService.BASE_URL)
    private val malApi = retrofit?.create(APIService::class.java)

    //Init RxJava untuk search
    private var malResults: MutableLiveData<List<MALResults>>? = null
    private val compositeDisposable = CompositeDisposable()

    val malData: LiveData<List<MALResults>>?
        get() {
            if (malResults == null) {
                malResults = MutableLiveData()
                initMALData()
            }
            return malResults
        }


    private fun initMALData() {
        //Dipanggil ketika program pertama berjalan. Untuk mendapatkan top airing anime
        val malObservable = malApi?.getTopAnime("anime", 1)
        malObservable!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<MALTopResponse> {
                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable.add(d)
                    }

                    override fun onNext(malTopResponse: MALTopResponse) {
                        malResults!!.postValue(malTopResponse.top)
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()

                    }

                    override fun onComplete() {

                    }
                })
    }

    fun loadMALSearch(title: String) {
        //Dijalankan setelah search. Untuk mendapatkan data hasil search
        val malObservable = malApi?.getSearch("anime", title, 1)
        malObservable!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<MALSearchResponse> {
                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable.add(d)
                    }

                    override fun onNext(malSearchResponses: MALSearchResponse) {
                        malResults!!.postValue(malSearchResponses.results)
                        Log.d("Response", malSearchResponses.results!![0].title)

                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }

                    override fun onComplete() {

                    }
                })
    }

    override fun onCleared() {
        if (compositeDisposable != null || !compositeDisposable.isDisposed)
            compositeDisposable.clear()
    }

}
