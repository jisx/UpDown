package com.lucky.cat.updown.download;

import com.lucky.cat.updown.sql.DownloadModel;

/**
 * Created by jisx on 2016/10/14.
 */
public abstract class DownloadRequest extends Request{

    public DownloadType downloadType = DownloadType.PREPARE;

    public abstract void init();

    public abstract void start();

    public abstract void cancel();

    public abstract DownloadModel getModel();



}
