package com.lucky.cat.updown.download;

import android.os.Handler;
import android.os.Message;

import com.lucky.cat.updown.sql.DownloadModel;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jisx on 2016/10/18.
 */

public class OkHttpRequest extends DownloadRequest {

    DownloadModel model;

    DownloadListener listener;

    OkHttpClient client;

    Request request;

    Call call;

    public OkHttpRequest(DownloadModel model, DownloadListener listener) {
        this.model = model;
        this.listener = listener;

        init();
    }

    @Override
    public void init() {
        //构建http请求
        client = new OkHttpClient();

        request = new Request.Builder()
                .url(model.getDownLoadUrl())
                .build();

        call = client.newCall(request);
        listener.onPrepare(model);
    }

    @Override
    public void start() {
        if (call.isExecuted() || call.isCanceled()) {
            call = client.newCall(request);
        }

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onStop(model);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    saveToFile(response.body().byteStream());
                } catch (Throwable e) {
                    e.printStackTrace();
                    listener.onStop(model);
                }
            }
        });

        listener.onStart(model);
    }

    private void saveToFile(InputStream inputStream) throws Throwable {
        BufferedInputStream bis = null;
        RandomAccessFile file = null;
        File flie1 = new File(model.getSavePath());
        if (!flie1.exists()) {
            flie1.getParentFile().mkdirs();
            flie1.createNewFile();
        }
        file = new RandomAccessFile(flie1, "rwd");

        file.seek(model.getCompleteSize());
        bis = new BufferedInputStream(inputStream);
        byte[] buffer = new byte[1024 * 1024 * 5];
        int len;
        long length = model.getCompleteSize();
        while ((len = bis.read(buffer)) != -1) {
            length = length + len;
            file.write(buffer, 0, len);
            if (length < model.getFileSize()) {
                model.setCompleteSize(length);
                listener.onLoading(model);
            }

        }
        listener.onComplete(model);
        bis.close();
        inputStream.close();
        file.close();
    }

    @Override
    public void cancel() {
        if (call.isExecuted()) {
            call.cancel();
            listener.onCancel(model);
        }
    }

    @Override
    public DownloadModel getModel() {
        return model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OkHttpRequest that = (OkHttpRequest) o;

        if (model != null ? !model.equals(that.model) : that.model != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return model != null ? model.hashCode() : 0;
    }
}
