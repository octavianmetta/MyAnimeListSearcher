package com.octavianmetta.android.myanimelistsearcher.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log

import com.octavianmetta.android.myanimelistsearcher.models.anime.AnimeModel
import com.octavianmetta.android.myanimelistsearcher.rest.APIService
import com.octavianmetta.android.myanimelistsearcher.rest.RetrofitClient

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class AnimeDetailViewModel : ViewModel() {

    private val retrofit = RetrofitClient.getClient(APIService.BASE_URL)
    private val MALApi = retrofit?.create(APIService::class.java)

    private var animeResults: MutableLiveData<AnimeModel>? = null
    private val compositeDisposable = CompositeDisposable()

    fun getAnimeData(malId: Int): LiveData<AnimeModel>? {
        if (animeResults == null) {
            animeResults = MutableLiveData()
            loadMALAnime(malId)
        }

        return animeResults
    }

    fun loadMALAnime(malId: Int?) {
        //Dijalankan setelah search. Untuk mendapatkan data hasil search
        val animeModelObservable = MALApi?.getAnime(malId)
        animeModelObservable!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<AnimeModel> {
                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable.add(d)
                    }

                    override fun onNext(animeModelResponses: AnimeModel) {
                        animeResults!!.postValue(animeModelResponses)
                        Log.d("Response", animeModelResponses.title)

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
