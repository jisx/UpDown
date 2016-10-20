package com.lucky.cat.client;

import android.app.Application;

import com.lucky.cat.updown.download.Build;
import com.lucky.cat.updown.download.DownloadManage;

/**
 * Created by jisx on 2016/10/19.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Build build = Build.Build();
        build.setFollowRecord(false);
        build.setIsStartNext(true);
        build.setIsStartNow(true);
        build.setIsDebug(true);
        build.setNumbersTask(5);
        build.setUnder_wifi(true);
        build.setResume_wifi_anto_down(true);

        DownloadManage.INSTANCE.init(this,build);
    }
}
