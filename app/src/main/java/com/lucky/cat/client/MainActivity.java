package com.lucky.cat.client;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.lucky.cat.updown.download.DownloadManage;
import com.lucky.cat.updown.sql.DownloadModel;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        setValue();

        DownloadAdapter adapter = new DownloadAdapter(this, DownloadManage.INSTANCE.getList());

        listView.setAdapter(adapter);
        DownloadManage.INSTANCE.getHistoryList();

    }

    private void setValue() {
        DownloadManage.INSTANCE.addTask(new DownloadModel(
                "http://183.131.168.94/appdl.hicloud.com/dl/appdl/application/apk/af/af2315f3676348bcb9febbc93ae32f4d/com.chengshizhanzheng.game.1606241757.apk?sign=portal@portal1476784233416&source=portalsite&wsiphost=localhttp://183.131.168.94/appdl.hicloud.com/dl/appdl/application/apk/af/af2315f3676348bcb9febbc93ae32f4d/com.chengshizhanzheng.game.1606241757.apk?sign=portal@portal1476784233416&source=portalsite&wsiphost=local",
                Environment.getExternalStorageDirectory() + "/com.chengshizhanzheng.game.1606241757.apk",
                "com.chengshizhanzheng.game.1606241757.apk",
                33594155L,
                0L,
                "", new Date()));
        DownloadManage.INSTANCE.addTask(new DownloadModel(
                "http://183.131.168.65/appdl.hicloud.com/dl/appdl/application/apk/71/71397497b2e24580825df5bba6223054/com.farlenkov.vrtps.1608140955.apk?sign=portal@portal1476784233458&source=portalsite&wsiphost=local",
                Environment.getExternalStorageDirectory() + "/com.farlenkov.vrtps.1608140955.apk",
                "com.farlenkov.vrtps.1608140955.apk",
                60269630L,
                0L,
                "", new Date()));
        DownloadManage.INSTANCE.addTask(new DownloadModel(
                "http://183.131.168.61/appdl.hicloud.com/dl/appdl/application/apk/d7/d7f8d7708ab84e61816ace2e8b88d1aa/com.imangi.templerun2.1609291047.apk?sign=portal@portal1476784143313&source=portalsite&wsiphost=local",
                Environment.getExternalStorageDirectory() + "/com.imangi.templerun2.1609291047.apk",
                "com.imangi.templerun2.1609291047.apk",
                53576450L,
                0L,
                "", new Date()));
        DownloadManage.INSTANCE.addTask(new DownloadModel(
                "http://183.131.168.63/appdl.hicloud.com/dl/appdl/application/apk/ab/ab88fbc789c441a994ae63d9e04142d8/com.kiloo.subwaysurf.1609281957.apk?sign=portal@portal1476784143312&source=portalsite&wsiphost=local",
                Environment.getExternalStorageDirectory() + "/com.kiloo.subwaysurf.1609281957.apk",
                "com.kiloo.subwaysurf.1609281957.apk",
                53082818L,
                0L,
                "", new Date()));
        DownloadManage.INSTANCE.addTask(new DownloadModel(
                "http://183.131.168.63/appdl.hicloud.com/dl/appdl/application/apk/5f/5f9af0b6e46b4468ba5eee23272722d5/com.happyelements.AndroidAnimal.1609281427.apk?sign=portal@portal1476865051023&source=portalsite&wsiphost=localhttp://183.131.168.63/appdl.hicloud.com/dl/appdl/application/apk/5f/5f9af0b6e46b4468ba5eee23272722d5/com.happyelements.AndroidAnimal.1609281427.apk?sign=portal@portal1476865051023&source=portalsite&wsiphost=localhttp://183.131.168.63/appdl.hicloud.com/dl/appdl/application/apk/5f/5f9af0b6e46b4468ba5eee23272722d5/com.happyelements.AndroidAnimal.1609281427.apk?sign=portal@portal1476865051023&source=portalsite&wsiphost=local",
                Environment.getExternalStorageDirectory() + "/com.happyelements.AndroidAnimal.1609281427.apk",
                "com.happyelements.AndroidAnimal.1609281427.apk",
                95252834L,
                0L,
                "", new Date()));
        DownloadManage.INSTANCE.addTask(new DownloadModel(
                "http://183.131.168.61/appdl.hicloud.com/dl/appdl/application/apk/0f/0f71418ca9e64045aa01552c1558129c/com.tencent.mm.1610191500.apk?sign=portal@portal1476865050847&source=portalsite&wsiphost=local",
                Environment.getExternalStorageDirectory() + "/com.tencent.mm.1610191500.apk",
                "com.tencent.mm.1610191500.apk",
                38918379L,
                0L,
                "", new Date()));
        DownloadManage.INSTANCE.addTask(new DownloadModel(
                "http://183.131.168.63/appdl.hicloud.com/dl/appdl/application/apk/22/222e55ed6719405d9c985d09d7990ce8/com.qiyi.video.1610131557.apk?sign=portal@portal1476865050853&source=portalsite&wsiphost=local",
                Environment.getExternalStorageDirectory() + "/com.qiyi.video.1610131557.apk",
                "com.qiyi.video.1610131557.apk",
                25141632L,
                0L,
                "", new Date()));
        DownloadManage.INSTANCE.addTask(new DownloadModel(
                "http://appdl.hicloud.com/dl/appdl/application/apk/03/038adcbec75b439098d2179b373c0dfe/cn.wps.moffice_eng.1610161002.apk?sign=portal@portal1476865050852&source=portalsite",
                Environment.getExternalStorageDirectory() + "/cn.wps.moffice_eng.1610161002.apk",
                "cn.wps.moffice_eng.1610161002.apk",
                27251825L,
                0L,
                "", new Date()));
        DownloadManage.INSTANCE.addTask(new DownloadModel(
                "http://183.131.168.65/appdl.hicloud.com/dl/appdl/application/apk/38/38c4e7e64ed541dfa4f8c62635fb84a7/com.android.mediacenter.1607261719.apk?sign=portal@portal1476865050849&source=portalsite&wsiphost=local",
                Environment.getExternalStorageDirectory() + "/com.android.mediacenter.1607261719.apk",
                "com.android.mediacenter.1607261719.apk",
                13186032L,
                0L,
                "", new Date()));
        DownloadManage.INSTANCE.addTask(new DownloadModel(
                "http://183.131.168.65/appdl.hicloud.com/dl/appdl/application/apk/25/25078bea152d408a9b39eea06b43f037/com.sina.weibo.1610181347.apk?sign=portal@portal1476865050851&source=portalsite&wsiphost=local",
                Environment.getExternalStorageDirectory() + "/com.sina.weibo.1610181347.apk",
                "com.sina.weibo.1610181347.apk",
                56699946L,
                0L,
                "", new Date()));
    }
}
