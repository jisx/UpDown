package com.lucky.cat.client;

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
        DownloadManage.INSTANCE.addTask(new DownloadModel(
                "http://183.131.168.94/appdl.hicloud.com/dl/appdl/application/apk/af/af2315f3676348bcb9febbc93ae32f4d/com.chengshizhanzheng.game.1606241757.apk?sign=portal@portal1476784233416&source=portalsite&wsiphost=local",
                Environment.getExternalStorageDirectory() + "/com.chengshizhanzheng.game.1606241757.apk",
                "com.chengshizhanzheng.game.1606241757.apk",
                33594368L,
                0L,
                "", new Date()));
        DownloadManage.INSTANCE.addTask(new DownloadModel(
                "http://183.131.168.65/appdl.hicloud.com/dl/appdl/application/apk/71/71397497b2e24580825df5bba6223054/com.farlenkov.vrtps.1608140955.apk?sign=portal@portal1476784233458&source=portalsite&wsiphost=local",
                Environment.getExternalStorageDirectory() + "/com.farlenkov.vrtps.1608140955.apk",
                "com.chengshizhanzheng.game.1606241757.apk",
                60270592L,
                0L,
                "", new Date()));

        DownloadAdapter adapter = new DownloadAdapter(this, DownloadManage.INSTANCE.getList());

        listView.setAdapter(adapter);


    }
}
