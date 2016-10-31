package com.lucky.cat.updown.download;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import com.lucky.cat.updown.sql.DaoMaster;
import com.lucky.cat.updown.sql.DaoSession;
import com.lucky.cat.updown.sql.DownloadHistoryModel;
import com.lucky.cat.updown.sql.DownloadHistoryModelDao;
import com.lucky.cat.updown.sql.DownloadModel;
import com.lucky.cat.updown.sql.DownloadModelDao;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jisx on 2016/10/14.
 */
public enum DownloadManage implements DownloadListener {

    INSTANCE {

        private String DBName = "download-db";

        private NetWorkChangeListener netListener;

        Context context;

        DaoSession daoSession;

        DownloadModelDao dao;
        DownloadHistoryModelDao historyDao;

        public Build build;
        //保存所有的下载任务列表
        public List<DownloadRequest> taskList;
        //正在下载的任务列表
        public List<DownloadRequest> loadingList;
        // 存放model 和 request 键值对，便于检索
        public HashMap<DownloadModel, DownloadRequest> relationMap;
        //存放监听的，提供给UI的
        public HashMap<DownloadRequest, DownloadListener> listenerMap;
        //保存wifi被断了时，正在下载的任务，做为恢复任务的依据
        public List<DownloadRequest> stopDownList;

        @Override
        public void init(Context context) {
            init(context, new Build());
        }

        @Override
        public void init(Context context, Build build) {
            if (this.build != null) {
                writeLog("初始化，已经初始化了，不需重复实例化");
                return;
            }

            this.context = context;
            this.build = build;

            if (build.isResume_wifi_anto_down()) {
                netListener = new NetWorkChangeListener();
                IntentFilter filter = new IntentFilter();
                filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
                context.registerReceiver(netListener, filter);
            }

            //连接数据库
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DBName, null);
            this.daoSession = new DaoMaster(helper.getWritableDatabase()).newSession();
            this.dao = daoSession.getDownloadModelDao();
            this.historyDao = daoSession.getDownloadHistoryModelDao();

            taskList = new ArrayList<>();
            loadingList = new ArrayList<>();
            stopDownList = new ArrayList<>();

            relationMap = new HashMap<>();
            listenerMap = new HashMap<>();

            //数据库中取出上次下载的进度
            if (build.isFollowRecord()) {
                writeLog("初始化，即将添加了" + dao.loadAll().size() + "条任务");

                for (DownloadModel model : dao.queryBuilder().orderAsc(DownloadModelDao.Properties.CreateTime).list()) {
                    addTask(model);
                }
            } else {
                writeLog("初始化，删除了所有数据库记录");
                dao.deleteAll();
            }
        }

        @Override
        public void addTask(DownloadModel model) {

            if (build == null) {
                writeLog("还未实例化，请在application中调用init()方法");
                return;
            }
            //完善数据
            perfectModel(model);


            if (!exist(model)) {
                relationMap.put(model, new OkHttpRequest(model, this));
                taskList.add(relationMap.get(model));
                dao.insertOrReplace(model);
            } else {
                showToast(model.getFileName() + "已经在下载列表中");
                writeLog("该任务：" + model.getFileName() + "已经添加过了，不需要重复添加");
            }

            //添加一条任务就开始执行下载
            if (build.isStartNow() && loadingList.isEmpty()) {
                startTask();
            }

        }

        private void perfectModel(DownloadModel model) {
            if (model.getCreateTime() == null) {
                model.setCreateTime(new Date());
            }
            String path = model.getSavePath();
            if (model.getFileName() == null) {
                model.setFileName(path.substring(path.lastIndexOf("/"), path.length() - 1));
            }
        }

        @Override
        public void addTask(List<DownloadModel> modelList) {
            writeLog("批量导入下载任务共计" + modelList.size() + "条");
            for (DownloadModel model : modelList) {
                addTask(model);
            }
        }

        private boolean exist(DownloadModel model) {
            return relationMap.containsKey(model);
        }

        @Override
        public void startTask() {
            //不为空说明 之前是因为wifi网络关闭导致的任务暂停,则需要重启任务
            if (!stopDownList.isEmpty()) {
                for (DownloadRequest request : stopDownList) {
                    startTask(request.getModel());
                }
                showToast("正在恢复下载");
                stopDownList.clear();
            }

            if (loadingList.isEmpty()) {
                if (!taskList.isEmpty()) {
                    for (DownloadRequest request : taskList) {
                        if (request.downloadType == DownloadType.PREPARE) {
                            startTask(request.getModel());
                            break;
                        }
                    }
                } else {
                    writeLog("状态为 PREPARE  的任务已经全部执行结束");
                }
            }

        }

        @Override
        public void startTask(DownloadModel model) {
            //wifi判断
            if (build.isUnder_wifi()) {
                if (!isWifi(context)) {
                    showToast("当前不是wifi环境");
                    writeLog("不是wifi下，不能下载");
                    return;
                }
            }


            DownloadRequest request = relationMap.get(model);
            if (request != null) {
                if (loadingList.contains(request)) {
                    writeLog("任务:" + model.getFileName() + "已经在下载了");
//                    showToast(model.getFileName() + "正在下载中");
                    return;
                } else {
                    if (loadingList.size() < build.getNumbersTask()) {
                        loadingList.add(request);
                        request.start();
                    } else {
                        showToast("最多同时进行" + build.getNumbersTask() + "条下载任务");
                        writeLog("无法启动任务：" + model.getFileName() + ",最多同时进行" + build.getNumbersTask() + "条下载任务");
                    }

                }

            } else {
                writeLog("任务:" + model.getFileName() + "不存在");
            }

            request = null;
        }

        @Override
        public void pauseTask(DownloadModel model) {
            DownloadRequest request = relationMap.get(model);
            if (loadingList.contains(request)) {
                loadingList.remove(request);
                request.cancel();
                writeLog("正在取消任务：" + model.getFileName());
            } else {

                writeLog("任务：" + request.getModel().getFileName() + "已经被取消过了");
            }

            request = null;
        }

        @Override
        public void pauseTask(List<DownloadModel> modelList) {
            writeLog("批量暂停任务，共计" + modelList.size() + "条任务");
            for (DownloadModel model : modelList) {
                pauseTask(model);
            }
        }

        @Override
        public void removeTask(DownloadModel model) {

            pauseTask(model);

            DownloadRequest request = relationMap.get(model);

            if (request != null && taskList.contains(request)) {
                taskList.remove(request);
                relationMap.remove(model);
                dao.delete(model);
                writeLog("删除任务：" + model.getFileName());
            }

            request = null;
        }

        @Override
        public void removeTask(List<DownloadModel> modelList) {

            writeLog("批量删除任务，共计" + modelList.size() + "条任务");
            for (DownloadModel model : modelList) {
                removeTask(model);
            }
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
        public List<DownloadHistoryModel> getHistoryList() {
            return historyDao.loadAll();
        }

        @Override
        public void setBuild(Build build) {
            this.build = build;
        }

        @Override
        public Build getBuild() {
            return this.build;
        }

        @Override
        public void onPrepare(DownloadRequest request) {
            if (taskList.contains(request)) {
                request.downloadType = DownloadType.PREPARE;
            }
            dao.insertOrReplace(request.getModel());

            writeLog("任务：" + request.getModel().getFileName() + "已经准备好下载了");

            if (listenerMap.containsKey(request)) {
                listenerMap.get(request).onPrepare(request);
            }
        }

        @Override
        public void onStart(DownloadRequest request) {
            if (taskList.contains(request)) {
                request.downloadType = DownloadType.START;
            }
            dao.insertOrReplace(request.getModel());

            writeLog("任务：" + request.getModel().getFileName() + "已经开始执行");

            if (listenerMap.containsKey(request)) {
                listenerMap.get(request).onStart(request);
            }
        }

        @Override
        public void onLoading(DownloadRequest request) {
            if (taskList.contains(request)) {
                request.downloadType = DownloadType.LOADING;
            }
            //
            dao.insertOrReplace(request.getModel());

            if (listenerMap.containsKey(request)) {
                listenerMap.get(request).onLoading(request);
            }

            //wifi判断
            if (build.isUnder_wifi()) {
                if (!isWifi(context)) {
                    pauseTask(request.getModel());
                    writeLog("下载中断，不是wifi环境下，不能下载");
                }
            }
        }

        @Override
        public void onStop(DownloadRequest request) {

            if (taskList.contains(request)) {
                request.downloadType = DownloadType.STOP;
                if (loadingList.contains(request)) {
                    loadingList.remove(request);
                }
            }

            dao.insertOrReplace(request.getModel());

            writeLog("任务：" + request.getModel().getFileName() + "已经停止了");

            if (listenerMap.containsKey(request)) {
                listenerMap.get(request).onStop(request);
            }

            //wifi判断
            if (build.isUnder_wifi()) {
                if (!isWifi(context)) {
                    stopDownList.add(request);
                    writeLog("下载中断，保存被中断的任务：" + request.getModel().getFileName());
                    return;
                }
            }

            //开始下个任务
            if (build.isStartNext())
                startTask();
        }

        @Override
        public void onCancel(DownloadRequest request) {
            if (taskList.contains(request)) {
                request.downloadType = DownloadType.CANCEL;
            }

            dao.insertOrReplace(request.getModel());

            writeLog("任务：" + request.getModel().getFileName() + "正在执行取消");

            if (listenerMap.containsKey(request)) {
                listenerMap.get(request).onCancel(request);
            }
        }

        @Override
        public void onComplete(DownloadRequest request) {
            if (taskList.contains(request)) {
                request.downloadType = DownloadType.COMPLETE;
            }
            dao.insertOrReplace(request.getModel());

            if (!isVerify(request.getModel())) {
                //验真不通过就重新下载
                if (build.isStartNext()) {
                    build.setStartNext(false);
                    onStop(request);//走停止逻辑
                    startTask(request.getModel());//再重启任务
                    build.setStartNext(true);
                }
                return;
            }

            //删除任务
            removeTask(request.getModel());
            //保存到历史记录中
            DownloadModel model = request.getModel();
            historyDao.insert(new DownloadHistoryModel(null, model.getDownLoadUrl(), model.getSavePath(), model.getFileName(), model.getCompleteSize(), new Date()));
            model = null;

            showToast(request.getModel().getFileName() + "下载完成");
            writeLog("任务：" + request.getModel().getFileName() + "下载完成");

            if (listenerMap.containsKey(request)) {
                listenerMap.get(request).onComplete(request);
            }
            //开始下个任务
            if (build.isStartNext())
                startTask();
        }

        private boolean isVerify(DownloadModel model) {

            ValiDateFactory vali = new NoneVali();

            switch (build.getVali()) {
                case NONE:
                    vali = new NoneVali();
                    return true;
                case FILESIZE:
                    vali = new FileSizeVali();
                    break;

                case MD5:

                    break;

            }

            if (!vali.Verify(model)) {
                model.setCompleteSize(0L);
                File file = new File(model.getSavePath());
                if (file.exists()) {
                    file.delete();
                }
                file = null;
                return false;
            }

            return true;
        }

        @Override
        public void addListener(DownloadRequest request, DownloadListener listener) {
            listenerMap.put(request, listener);
        }

        @Override
        public void removeListener(DownloadRequest request, DownloadListener listener) {
            if (listenerMap.containsKey(request))
                listenerMap.remove(request);
        }


        private void writeLog(String msg) {
            if (build.isDebug())
                Log.d(TAG, msg);
        }

        private void showToast(String msg){
            Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
        }

    };

    /**
     * 在APPlication中实例化
     *
     * @param context
     */
    public abstract void init(Context context);

    /**
     * 在APPlication中实例化
     *
     * @param context
     */
    public abstract void init(Context context, Build build);

    public abstract void addTask(DownloadModel model);

    public abstract void addTask(List<DownloadModel> modelList);

    public abstract void startTask();

    public abstract void startTask(DownloadModel downloadModel);

    public abstract void pauseTask(DownloadModel downloadModel);

    public abstract void pauseTask(List<DownloadModel> modelList);

    public abstract void removeTask(DownloadModel downloadModel);

    public abstract void removeTask(List<DownloadModel> modelList);

    public abstract List<DownloadRequest> getList();

    public abstract List<DownloadModel> getLoadingList();

    public abstract List<DownloadHistoryModel> getHistoryList();

    public abstract void setBuild(Build build);

    public abstract Build getBuild();

    public abstract void addListener(DownloadRequest request, DownloadListener listener);

    public abstract void removeListener(DownloadRequest request, DownloadListener listener);

    public static final String TAG = "DownloadManage";


    public boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

}
