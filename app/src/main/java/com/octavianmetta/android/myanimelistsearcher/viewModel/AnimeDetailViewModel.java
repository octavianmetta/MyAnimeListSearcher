package com.octavianmetta.android.myanimelistsearcher.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.octavianmetta.android.myanimelistsearcher.models.anime.AnimeModel;
import com.octavianmetta.android.myanimelistsearcher.rest.APIService;
import com.octavianmetta.android.myanimelistsearcher.rest.RetrofitClient;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class AnimeDetailViewModel extends ViewModel {

    private Retrofit retrofit = RetrofitClient.getClient(APIService.BASE_URL);
    private APIService MALApi = retrofit.create(APIService.class);

    private MutableLiveData<AnimeModel> animeResults;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public LiveData<AnimeModel> getAnimeData(int malId){
        if(animeResults == null){
            animeResults = new MutableLiveData<>();
            loadMALAnime(malId);
        }

        return  animeResults;
    }
    public void loadMALAnime(Integer malId){
        //Dijalankan setelah search. Untuk mendapatkan data hasil search
        Observable<AnimeModel> animeModelObservable = MALApi.getAnime(malId);
        animeModelObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AnimeModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(AnimeModel animeModelResponses) {
                        animeResults.postValue(animeModelResponses);
                        Log.d("Response", animeModelResponses.getTitle());

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
