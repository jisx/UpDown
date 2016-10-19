package com.lucky.cat.updown.download;

import android.content.Context;
import android.util.Log;

import com.lucky.cat.updown.sql.DaoMaster;
import com.lucky.cat.updown.sql.DaoSession;
import com.lucky.cat.updown.sql.DownloadModel;
import com.lucky.cat.updown.sql.DownloadModelDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jisx on 2016/10/14.
 */
public enum DownloadManage implements DownloadListener {

    INSTANCE {

        private String DBName = "download-db";

        DaoSession daoSession;

        DownloadModelDao dao;

        public Build build;
        //保存所有的下载任务列表
        public List<DownloadRequest> taskList;
        //正在下载的任务列表
        public List<DownloadRequest> loadingList;
        // 存放model 和 request 键值对，便于检索
        public HashMap<DownloadModel, DownloadRequest> relationMap;
        //存放监听的
        public HashMap<DownloadModel, DownloadListener> listenerMap;

        @Override
        public void init(Context context) {
            init(context, Build.defaultBuild);
        }

        @Override
        public void init(Context context, Build build) {
            if (build != null) {
                new Throwable("DownloadManage has init");
            }

            this.build = build;
            //连接数据库
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DBName, null);
            this.daoSession = new DaoMaster(helper.getWritableDatabase()).newSession();
            this.dao = daoSession.getDownloadModelDao();

            taskList = new ArrayList<>();
            loadingList = new ArrayList<>();
            relationMap = new HashMap<>();
            listenerMap = new HashMap<>();

        }

        @Override
        public void addTask(DownloadModel model) {
            if (build == null) {
                new Throwable("DownloadManage not init");
            }

            if (!exist(model)) {
                relationMap.put(model, new OkHttpRequest(model, this));
                taskList.add(relationMap.get(model));
            }

            //添加一条任务就开始执行下载
            if (build.isStartNow) {
                startTask();
            }

        }

        private boolean exist(DownloadModel model) {
            return relationMap.containsKey(model);
        }

        @Override
        public void startTask() {

            if (loadingList.isEmpty()) {
                if (!taskList.isEmpty()) {
                    for (DownloadRequest request : taskList) {
                        if (request.downloadType == DownloadType.PREPARE) {
                            startTask(request.getModel());
                        }
                    }
                }
            }

        }

        @Override
        public void startTask(DownloadModel model) {
            DownloadRequest request = relationMap.get(model);
            if (request != null) {
                if (loadingList.contains(request)) {
                    Log.d(TAG, "该任务已经开始了");
                    return;
                } else {
                    if (loadingList.size() < build.numbersTask) {
                        loadingList.add(request);
                        request.start();
                    } else {
                        Log.d(TAG, "最多" + build.numbersTask + "条下载任务");
                    }

                }

            } else {
                Log.d(TAG, "任务不存在");
            }

            request = null;
        }

        @Override
        public void pauseTask(DownloadModel model) {
            cancelTask(model);
        }

        @Override
        public void removeTask(DownloadModel model) {
            cancelTask(model);

            DownloadRequest request = relationMap.get(model);

            if (request != null && taskList.contains(request)) {
                taskList.remove(request);
                relationMap.remove(model);
            }

            request = null;
        }

        @Override
        public void cancelTask(DownloadModel model) {
            DownloadRequest request = relationMap.get(model);
            if (loadingList.contains(request)) {
                request.cancel();
                loadingList.remove(request);
            }

            request = null;
        }

        @Override
        public List<DownloadRequest> getList() {

            return taskList;
        }

        @Override
        public List<DownloadModel> getLoadingList() {
            List<DownloadModel> list = new ArrayList<>();
            for (DownloadRequest request : loadingList) {
                list.add(request.getModel());
            }
            return list;
        }

        @Override
        public void onPrepare(DownloadModel downloadModel) {
            if (relationMap.containsKey(downloadModel)) {
                relationMap.get(downloadModel).downloadType = DownloadType.PREPARE;
            }
            dao.insertOrReplace(downloadModel);


            if (listenerMap.containsKey(downloadModel)) {
                listenerMap.get(downloadModel).onPrepare(downloadModel);
            }
        }

        @Override
        public void onStart(DownloadModel downloadModel) {
            if (relationMap.containsKey(downloadModel)) {
                relationMap.get(downloadModel).downloadType = DownloadType.START;
            }
            dao.insertOrReplace(downloadModel);

            if (listenerMap.containsKey(downloadModel)) {
                listenerMap.get(downloadModel).onStart(downloadModel);
            }
        }

        @Override
        public void onLoading(DownloadModel downloadModel) {
            if (relationMap.containsKey(downloadModel)) {
                relationMap.get(downloadModel).downloadType = DownloadType.LOADING;
            }
            //
            dao.insertOrReplace(downloadModel);


            if (listenerMap.containsKey(downloadModel)) {
                listenerMap.get(downloadModel).onLoading(downloadModel);
            }
        }

        @Override
        public void onStop(DownloadModel downloadModel) {
            if (relationMap.containsKey(downloadModel)) {
                relationMap.get(downloadModel).downloadType = DownloadType.STOP;
                DownloadRequest request = relationMap.get(downloadModel);
                if (loadingList.contains(request)) {
                    loadingList.remove(request);
                }
            }
            dao.insertOrReplace(downloadModel);

            if (listenerMap.containsKey(downloadModel)) {
                listenerMap.get(downloadModel).onStop(downloadModel);
            }

            //开始下个任务
            startTask();
        }

        @Override
        public void onCancel(DownloadModel downloadModel) {
            if (relationMap.containsKey(downloadModel)) {
                relationMap.get(downloadModel).downloadType = DownloadType.CANCEL;
            }
            dao.insertOrReplace(downloadModel);

            if (listenerMap.containsKey(downloadModel)) {
                listenerMap.get(downloadModel).onCancel(downloadModel);
            }
            //开始下个任务
            startTask();
        }

        @Override
        public void onComplete(DownloadModel downloadModel) {
            if (relationMap.containsKey(downloadModel)) {
                relationMap.get(downloadModel).downloadType = DownloadType.COMPLETE;
            }
            dao.insertOrReplace(downloadModel);
            //删除任务
            removeTask(downloadModel);

            if (listenerMap.containsKey(downloadModel)) {
                listenerMap.get(downloadModel).onComplete(downloadModel);
            }
            //开始下个任务
            startTask();
        }

        @Override
        public void addListener(DownloadRequest request, DownloadListener listener) {
            listenerMap.put(request.getModel(), listener);
        }

        @Override
        public void removeListener(DownloadRequest request, DownloadListener listener) {
            if (listenerMap.containsKey(request.getModel()))
                listenerMap.remove(request.getModel());
        }

    };

    public abstract void init(Context context);

    public abstract void init(Context context, Build build);

    public abstract void addTask(DownloadModel model);

    public abstract void startTask();

    public abstract void startTask(DownloadModel downloadModel);

    public abstract void pauseTask(DownloadModel downloadModel);

    public abstract void removeTask(DownloadModel downloadModel);

    public abstract void cancelTask(DownloadModel downloadModel);

    public abstract List<DownloadRequest> getList();

    public abstract List<DownloadModel> getLoadingList();

    public abstract void addListener(DownloadRequest request, DownloadListener listener);

    public abstract void removeListener(DownloadRequest request, DownloadListener listener);

    public static final String TAG = "DownloadManage";

}
