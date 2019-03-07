package com.octavianmetta.android.myanimelistsearcher.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.octavianmetta.android.myanimelistsearcher.models.MALResponse;
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

    private MutableLiveData<MALResponse> malResponse;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public LiveData<MALResponse> getSearch(){
        if (malResponse == null){
            malResponse = new MutableLiveData<MALResponse>();
            loadMALSearch();
        }
        return malResponse;
    }

    private void loadMALSearch(){

        Retrofit retrofit = RetrofitClient.getClient(APIService.BASE_URL);
        APIService MALApi = retrofit.create(APIService.class);
        Observable<MALResponse> MALObservable = MALApi.getSearch("anime","Bakemonogatari",1);

        MALObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MALResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(MALResponse malResponses) {
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
