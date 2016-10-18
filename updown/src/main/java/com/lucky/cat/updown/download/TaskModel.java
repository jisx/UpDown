package com.lucky.cat.updown.download;

import com.lucky.cat.updown.sql.DownloadModel;

/**
 * Created by jisx on 2016/10/14.
 */
public class TaskModel {
    private DownloadType type;

    private DownloadModel model;

    private DownloadRequest request;

    public DownloadType getType() {
        return type;
    }

    public void setType(DownloadType type) {
        this.type = type;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskModel taskModel = (TaskModel) o;

        return model != null ? model.equals(taskModel.model) : taskModel.model == null;

    }

    @Override
    public int hashCode() {
        return model != null ? model.hashCode() : 0;
    }
}
