package com.octavianmetta.android.myanimelistsearcher.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.octavianmetta.android.myanimelistsearcher.models.MALResponse;
import com.octavianmetta.android.myanimelistsearcher.models.MALResults;
import com.octavianmetta.android.myanimelistsearcher.rest.APIService;
import com.octavianmetta.android.myanimelistsearcher.rest.MALClient;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MALViewModel extends ViewModel {

    private MutableLiveData<List<MALResponse>> malResponse;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public LiveData<List<MALResponse>> getSearch(){
        if (malResponse == null){
            malResponse = new MutableLiveData<List<MALResponse>>();
            loadMALSearch();
        }
        return malResponse;
    }

    private void loadMALSearch(){

        Retrofit retrofit = MALClient.getClient(APIService.BASE_URL);
        APIService MALApi = retrofit.create(APIService.class);
        Observable<List<MALResponse>> MALObservable = MALApi.getSearch("Persona");

        MALObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<MALResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<MALResponse> malResponses) {
                        malResponse.setValue(malResponses);


                    }

                    @Override
                    public void onError(Throwable e) {

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
