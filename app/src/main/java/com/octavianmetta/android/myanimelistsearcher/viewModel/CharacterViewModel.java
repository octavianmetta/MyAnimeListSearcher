package com.octavianmetta.android.myanimelistsearcher.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.octavianmetta.android.myanimelistsearcher.models.anime.characterStaff.Character;
import com.octavianmetta.android.myanimelistsearcher.models.anime.characterStaff.CharacterStaff;
import com.octavianmetta.android.myanimelistsearcher.rest.APIService;
import com.octavianmetta.android.myanimelistsearcher.rest.RetrofitClient;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class CharacterViewModel extends ViewModel {
    private Retrofit retrofit = RetrofitClient.getClient(APIService.BASE_URL);
    private APIService MALApi = retrofit.create(APIService.class);

    private MutableLiveData<List<Character>> characterList;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public LiveData<List<Character>> getCharacterData(int malId){
        if(characterList == null){
            characterList = new MutableLiveData<>();
            initCharacterData(malId);
        }
        return characterList;
    }

    private void initCharacterData(int malId) {
        //Dipanggil ketika program pertama berjalan. Untuk mendapatkan top airing anime
        Observable<CharacterStaff> characterObservable = MALApi.getCharacter(malId);
        characterObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CharacterStaff>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CharacterStaff characterStaff) {
                        characterList.postValue(characterStaff.getCharacters());
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onCleared() {
        if(compositeDisposable != null || !compositeDisposable.isDisposed())
            compositeDisposable.clear();
    }
}
