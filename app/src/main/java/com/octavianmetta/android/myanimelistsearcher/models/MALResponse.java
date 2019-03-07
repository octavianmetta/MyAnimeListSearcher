package com.octavianmetta.android.myanimelistsearcher.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.octavianmetta.android.myanimelistsearcher.BR;

import java.util.List;

public class MALResponse extends BaseObservable {
    @SerializedName("request_hash")
    @Expose
    public String requestHash;
    @SerializedName("request_cached")
    @Expose
    public Boolean requestCached;
    @SerializedName("request_cache_expiry")
    @Expose
    public Integer requestCacheExpiry;
    @SerializedName("results")
    @Expose
    public List<MALResults> results = null;
    @SerializedName("last_page")
    @Expose
    public Integer lastPage;

    public MALResponse(String requestHash, Boolean requestCached, Integer requestCacheExpiry, List<MALResults> results, Integer lastPage) {
        this.requestHash = requestHash;
        this.requestCached = requestCached;
        this.requestCacheExpiry = requestCacheExpiry;
        this.results = results;
        this.lastPage = lastPage;
    }

    public String getRequestHash() {
        return requestHash;
    }

    public void setRequestHash(String requestHash) {
        this.requestHash = requestHash;
    }

    public Boolean getRequestCached() {
        return requestCached;
    }

    public void setRequestCached(Boolean requestCached) {
        this.requestCached = requestCached;
    }

    public Integer getRequestCacheExpiry() {
        return requestCacheExpiry;
    }

    public void setRequestCacheExpiry(Integer requestCacheExpiry) {
        this.requestCacheExpiry = requestCacheExpiry;
    }

    @Bindable
    public List<MALResults> getResults() {
        return results;
    }

    public void setResults(List<MALResults> results) {
        this.results = results;
        notifyPropertyChanged(BR.results);

    }

    public Integer getLastPage() {
        return lastPage;
    }

    public void setLastPage(Integer lastPage) {
        this.lastPage = lastPage;
    }
}
