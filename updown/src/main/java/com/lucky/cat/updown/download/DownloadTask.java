package com.lucky.cat.updown.download;

import com.lucky.cat.updown.sql.DownloadModel;

/**
 * Created by jisx on 2016/10/14.
 */
public class DownloadTask {

    private DownloadModel model;

    private DownloadRequest request;

    public DownloadModel getModel() {
        return model;
    }

    public void setModel(DownloadModel model) {
        this.model = model;
    }

    public DownloadRequest getRequest() {
        return request;
    }

    public void setRequest(DownloadRequest request) {
        this.request = request;
    }
}
