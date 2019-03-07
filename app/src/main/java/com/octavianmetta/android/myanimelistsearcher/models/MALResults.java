package com.octavianmetta.android.myanimelistsearcher.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.octavianmetta.android.myanimelistsearcher.BR;

public class MALResults extends BaseObservable {
    @SerializedName("mal_id")
    @Expose
    public Integer malId;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("image_url")
    @Expose
    public String imageUrl;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("airing")
    @Expose
    public Boolean airing;
    @SerializedName("synopsis")
    @Expose
    public String synopsis;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("episodes")
    @Expose
    public Integer episodes;
    @SerializedName("score")
    @Expose
    public Double score;
    @SerializedName("start_date")
    @Expose
    public String startDate;
    @SerializedName("end_date")
    @Expose
    public String endDate;
    @SerializedName("members")
    @Expose
    public Integer members;
    @SerializedName("rated")
    @Expose
    public String rated;

    public MALResults(Integer malId, String url, String imageUrl, String title, Boolean airing,
                      String synopsis, String type, Integer episodes, Double score,
                      String startDate, String endDate, Integer members, String rated) {
        this.malId = malId;
        this.url = url;
        this.imageUrl = imageUrl;
        this.title = title;
        this.airing = airing;
        this.synopsis = synopsis;
        this.type = type;
        this.episodes = episodes;
        this.score = score;
        this.startDate = startDate;
        this.endDate = endDate;
        this.members = members;
        this.rated = rated;
    }

    public Integer getMalId() {
        return malId;
    }

    public void setMalId(Integer malId) {
        this.malId = malId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String imageUrl){
        Glide.with(view.getContext())
                .load(imageUrl)
                .into(view);
    }

    @Bindable
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        notifyPropertyChanged(BR.imageUrl);
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;

    }

    @Bindable
    public Boolean getAiring() {
        return airing;
    }

    public void setAiring(Boolean airing) {
        this.airing = airing;
        notifyPropertyChanged(BR.airing);
    }

    @Bindable
    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
        notifyPropertyChanged(BR.synopsis);
    }

    @Bindable
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        notifyPropertyChanged(BR.type);
    }

    @Bindable
    public Integer getEpisodes() {
        return episodes;
    }

    public void setEpisodes(Integer episodes) {
        this.episodes = episodes;
        notifyPropertyChanged(BR.episodes);
    }

    @Bindable
    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
        notifyPropertyChanged(BR.score);
    }

    @Bindable
    public String getStartDate() {
        return startDate.substring(0,4);
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
        notifyPropertyChanged(BR.startDate);
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getMembers() {
        return members;
    }

    public void setMembers(Integer members) {
        this.members = members;
    }

    @Bindable
    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
        notifyPropertyChanged(BR.rated);
    }
}
