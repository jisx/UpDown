package com.lucky.cat.updown.download;

import com.lucky.cat.updown.sql.DownloadModel;

/**
 * Created by jisx on 2016/10/14.
 */
public interface DownloadListener {

    public void onPrepare(DownloadRequest request);

    public void onStart(DownloadRequest request);

    public void onLoading(DownloadRequest request);

    public void onStop(DownloadRequest request);

    public void onCancel(DownloadRequest request);

    public void onComplete(DownloadRequest request);
}
