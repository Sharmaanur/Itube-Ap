package com.suman.itube.DB;

import androidx.annotation.NonNull;

public class VideoModel {
    String id;
    String link;
    String time;

    public String getVideoLink() {
        return link;
    }

    public void setVideoLink(String clientId) {
        this.link = clientId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @NonNull
    @Override
    public String toString() {
        return link;
    }
}
