package com.lucky.cat.updown.download;

/**
 * Created by jisx on 2016/10/14.
 */
public abstract class DownloadRequest extends Request {

    public abstract void init();

    public abstract void start();

    public abstract void cancel();

}
