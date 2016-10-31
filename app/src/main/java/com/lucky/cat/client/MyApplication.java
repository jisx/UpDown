package com.lucky.cat.client;

import android.app.Application;

import com.lucky.cat.updown.download.Build;
import com.lucky.cat.updown.download.DownloadManage;
import com.lucky.cat.updown.download.Vali;

/**
 * Created by jisx on 2016/10/19.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Build build = new Build();
        build.setFollowRecord(true);
        build.setStartNext(false);
        build.setStartNow(false);
        build.setDebug(true);
        build.setNumbersTask(5);
        build.setUnder_wifi(true);
        build.setResume_wifi_anto_down(true);
        build.setVali(Vali.FILESIZE);
        build.setShowMsg(true);

        DownloadManage.INSTANCE.init(this,build);
    }
}
