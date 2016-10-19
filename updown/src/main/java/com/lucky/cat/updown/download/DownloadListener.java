package com.lucky.cat.updown.download;

import com.lucky.cat.updown.sql.DownloadModel;

/**
 * Created by jisx on 2016/10/14.
 */
public interface DownloadListener {

    public void onPrepare(DownloadModel downloadModel);

    public void onStart(DownloadModel downloadModel);

    public void onLoading(DownloadModel downloadModel);

    public void onStop(DownloadModel downloadModel);

    public void onCancel(DownloadModel downloadModel);

    public void onComplete(DownloadModel downloadModel);
}
