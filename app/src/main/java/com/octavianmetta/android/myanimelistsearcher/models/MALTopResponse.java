package com.octavianmetta.android.myanimelistsearcher.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MALTopResponse {
    @SerializedName("request_hash")
    @Expose
    public String requestHash;
    @SerializedName("request_cached")
    @Expose
    public Boolean requestCached;
    @SerializedName("request_cache_expiry")
    @Expose
    public Integer requestCacheExpiry;
    @SerializedName("top")
    @Expose
    public List<MALResults> top = null;

    public MALTopResponse(String requestHash, Boolean requestCached, Integer requestCacheExpiry, List<MALResults> top) {
        this.requestHash = requestHash;
        this.requestCached = requestCached;
        this.requestCacheExpiry = requestCacheExpiry;
        this.top = top;
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

    public List<MALResults> getTop() {
        return top;
    }

    public void setTop(List<MALResults> top) {
        this.top = top;
    }

}
