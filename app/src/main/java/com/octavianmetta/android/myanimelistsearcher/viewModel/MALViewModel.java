package com.octavianmetta.android.myanimelistsearcher.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import android.view.View;

import com.octavianmetta.android.myanimelistsearcher.activity.MainActivity;
import com.octavianmetta.android.myanimelistsearcher.models.MALSearchResponse;
import com.octavianmetta.android.myanimelistsearcher.models.MALResults;
import com.octavianmetta.android.myanimelistsearcher.models.MALTopResponse;
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

public class MALViewModel extends ViewModel {

    //Init Retrofit
    private Retrofit retrofit = RetrofitClient.getClient(APIService.BASE_URL);
    private APIService MALApi = retrofit.create(APIService.class);

    //Init RxJava
    private MutableLiveData<List<MALResults>> malResults;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public LiveData<List<MALResults>> getMALData(){
        if(malResults == null){
            malResults = new MutableLiveData<>();
            initMALData();
        }
        return malResults;
    }

    private void initMALData() {
        //Dipanggil ketika program pertama berjalan. Untuk mendapatkan top airing anime
        Observable<MALTopResponse> MALObservable = MALApi.getTopAnime("anime",1);
        MALObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MALTopResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(MALTopResponse malTopResponse) {
                        malResults.postValue(malTopResponse.top);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void loadMALSearch(String title){
        //Dijalankan setelah search. Untuk mendapatkan data hasil search
        Observable<MALSearchResponse> MALObservable = MALApi.getSearch("anime",title ,1);
        MALObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MALSearchResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(MALSearchResponse malSearchResponses) {
                        malResults.postValue(malSearchResponses.results);
                        Log.d("Response", malSearchResponses.results.get(0).getTitle());

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
