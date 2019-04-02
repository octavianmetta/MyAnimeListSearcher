package com.octavianmetta.android.myanimelistsearcher.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

import com.octavianmetta.android.myanimelistsearcher.models.anime.characterStaff.Character
import com.octavianmetta.android.myanimelistsearcher.models.anime.characterStaff.CharacterStaff
import com.octavianmetta.android.myanimelistsearcher.rest.APIService
import com.octavianmetta.android.myanimelistsearcher.rest.RetrofitClient

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CharacterViewModel : ViewModel() {
    private val retrofit = RetrofitClient.getClient(APIService.BASE_URL)
    private val malApi = retrofit?.create(APIService::class.java)

    private var characterList: MutableLiveData<List<Character>>? = null
    private val compositeDisposable = CompositeDisposable()

    fun getCharacterData(malId: Int): LiveData<List<Character>>? {
        if (characterList == null) {
            characterList = MutableLiveData()
            initCharacterData(malId)
        }
        return characterList
    }

    private fun initCharacterData(malId: Int) {
        //Dipanggil ketika program pertama berjalan. Untuk mendapatkan top airing anime
        val characterObservable = malApi?.getCharacter(malId)
        characterObservable!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<CharacterStaff> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(characterStaff: CharacterStaff) {
                        characterList!!.postValue(characterStaff.characters)
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
