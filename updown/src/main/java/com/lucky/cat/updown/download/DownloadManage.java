package com.lucky.cat.updown.download;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.lucky.cat.updown.sql.DaoMaster;
import com.lucky.cat.updown.sql.DaoSession;
import com.lucky.cat.updown.sql.DownloadModel;
import com.lucky.cat.updown.sql.DownloadModelDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jisx on 2016/10/14.
 */
public enum DownloadManage {

    INSTANCE {

        private String DBName = "download-db";

        DaoSession daoSession;

        DownloadModelDao dao;

        public Build build;
        //保存所有的下载任务列表
        public List<TaskModel> taskList;
        //正在下载的任务列表
        public List<TaskModel> loadingList;

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

            taskList = new ArrayList<TaskModel>();
            loadingList = new ArrayList<TaskModel>();

            //从以前数据库中拿出数据，存入下载队列
            if (!dao.loadAll().isEmpty()) {
                for (DownloadModel model : dao.loadAll()) {
                    addTask(model);
                }
            }

            //填充LoadingList
            if(taskList.size() > this.build.numbersTask){
                loadingList.addAll(taskList.subList(0,this.build.numbersTask - 1));
            }else{
                loadingList.addAll(taskList);
            }

        }

        @Override
        public void addTask(DownloadModel model) {
            if (build == null) {
                new Throwable("DownloadManage not init");
            }

            if (!exist(model)) {
                TaskModel taskModel = new TaskModel();
                taskList.add(taskModel);
            }

            //添加一条任务就开始执行下载
            if (build.isStartNow) {
                startTask();
            }

        }

        private boolean exist(DownloadModel model) {
            for (TaskModel taskModel : taskList) {
                if (taskModel.getModel().equals(model)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public void startTask() {

            if(loadingList.isEmpty()){
                if(!taskList.isEmpty()){
                    loadingList.add(taskList.get(0));
                }
            }

            loadingList.get(0).getRequest().start();
        }
        @Override
        public void startTask(TaskModel  taskModel) {
           if(loadingList.contains(taskModel)){
               Log.d(TAG,"The download tasks started");
               return;
           }
            if(taskList.contains(taskModel)){
                //判断是否达到最大下载条数
                if(loadingList.size() < build.numbersTask){
                    loadingList.add(taskModel);
                    taskModel.getRequest().start();
                }else{
                    Log.d(TAG,"最多" + build.numbersTask + "条下载任务");
                }
            }else{
                Log.d(TAG,"There is no download task");
            }

        }

        @Override
        public void pauseTask(TaskModel  taskModel) {
            if(loadingList.contains(taskModel)){
                taskModel.getRequest().cancel();
                loadingList.remove(taskModel);
            }else{
                Log.d(TAG,"The download task is over");
            }
        }

        @Override
        public void removeTask(TaskModel  taskModel) {
            //先取消任务
            cancelTask(taskModel);

            if(taskList.contains(taskModel)){
                taskList.remove(taskModel);
            }else{
                Log.d(TAG," The download task has been removed");
            }
        }

        @Override
        public void cancelTask(TaskModel  taskModel) {
            if(loadingList.contains(taskModel)){
                taskModel.getRequest().cancel();
                loadingList.remove(taskModel);
            }else{
                Log.d(TAG," The download task has been canceled");
            }
        }

        @Override
        public List<TaskModel> getList() {
            return taskList;
        }

        @Override
        public List<TaskModel> getLoadingList() {
            return loadingList;
        }
    };

    public abstract void init(Context context);

    public abstract void init(Context context, Build build);

    public abstract void addTask(DownloadModel model);

    public abstract void startTask();

    public abstract void startTask(TaskModel taskModel);

    public abstract void pauseTask(TaskModel  taskModel);

    public abstract void removeTask(TaskModel  taskModel);

    public abstract void cancelTask(TaskModel  taskModel);

    public abstract List<TaskModel> getList();

    public abstract List<TaskModel> getLoadingList();

    public static final String TAG = "DownloadManage";

}
