package com.lucky.cat.client;

import android.app.Application;

import com.lucky.cat.updown.download.DownloadManage;

/**
 * Created by jisx on 2016/10/19.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DownloadManage.INSTANCE.init(this);
    }
}
