package com.lucky.cat.updown.download;

/**
 * Created by jisx on 2016/10/14.
 */
public interface DownloadListener {

    public void onPrepare();

    public void onStart();

    public void onStop();

    public void onCancel();

    public void onComplete();
}
