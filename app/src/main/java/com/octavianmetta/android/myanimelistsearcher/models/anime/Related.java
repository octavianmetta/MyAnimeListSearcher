
package com.octavianmetta.android.myanimelistsearcher.models.anime;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Related {

    @SerializedName("Adaptation")
    @Expose
    private List<Adaptation> adaptation = null;
    @SerializedName("Summary")
    @Expose
    private List<Summary> summary = null;
    @SerializedName("Sequel")
    @Expose
    private List<Sequel> sequel = null;
    @SerializedName("Prequel")
    @Expose
    private List<Prequel> prequel = null;
    @SerializedName("Other")
    @Expose
    private List<Other> other = null;
    @SerializedName("Side story")
    @Expose
    private List<SideStory> sideStory = null;

    public List<Adaptation> getAdaptation() {
        return adaptation;
    }

    public void setAdaptation(List<Adaptation> adaptation) {
        this.adaptation = adaptation;
    }

    public List<Summary> getSummary() {
        return summary;
    }

    public void setSummary(List<Summary> summary) {
        this.summary = summary;
    }

    public List<Sequel> getSequel() {
        return sequel;
    }

    public void setSequel(List<Sequel> sequel) {
        this.sequel = sequel;
    }

    public List<Prequel> getPrequel() {
        return prequel;
    }

    public void setPrequel(List<Prequel> prequel) {
        this.prequel = prequel;
    }

    public List<Other> getOther() {
        return other;
    }

    public void setOther(List<Other> other) {
        this.other = other;
    }

    public List<SideStory> getSideStory() {
        return sideStory;
    }

    public void setSideStory(List<SideStory> sideStory) {
        this.sideStory = sideStory;
    }

}
