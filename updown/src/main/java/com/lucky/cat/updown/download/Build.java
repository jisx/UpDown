package com.lucky.cat.updown.download;

/**
 * Created by jisx on 2016/10/14.
 */
public class Build {
    /**
     * 是否开启debug
     */
    private boolean isDebug = true;
    /**
     * 同时下载的任务条数
     */
    private int numbersTask = 1;
    /**
     * 任务添加时，是否立即开始
     */
    private boolean isStartNow = false;
    /**
     * 任务下载完成，是否立即开始下载任务
     */
    private boolean isStartNext = true;
    /**
     * 沿用上次下载记录
     */
    private boolean followRecord = true;
    /**
     * 只能在wifi下 执行下载
     */
    private boolean under_wifi = true;
    /**
     * 是否进行校验
     */
    private Vali vali = Vali.NONE;

    private boolean isShowMsg = true;

    /**
     * 恢复wifi 自动下载
     * 记得在配置清单中注册权限可类
     * <p>
     * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
     * <p>
     * <receiver android:name="com.lucky.cat.updown.download.NetWorkChangeListener">
     * <intent-filter>
     * <action android:name="android.net.conn.CONNECTIVITY_CHANGE" />
     * </intent-filter>
     * </receiver>
     */
    public static boolean resume_wifi_anto_down = false;

    public Build() {
    }

    public boolean isDebug() {
        return isDebug;
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
    }

    public int getNumbersTask() {
        return numbersTask;
    }

    public void setNumbersTask(int numbersTask) {
        this.numbersTask = numbersTask;
    }

    public boolean isStartNow() {
        return isStartNow;
    }

    public void setStartNow(boolean startNow) {
        isStartNow = startNow;
    }

    public boolean isStartNext() {
        return isStartNext;
    }

    public void setStartNext(boolean startNext) {
        isStartNext = startNext;
    }

    public boolean isFollowRecord() {
        return followRecord;
    }

    public void setFollowRecord(boolean followRecord) {
        this.followRecord = followRecord;
    }

    public boolean isUnder_wifi() {
        return under_wifi;
    }

    public void setUnder_wifi(boolean under_wifi) {
        this.under_wifi = under_wifi;
    }

    public Vali getVali() {
        return vali;
    }

    public void setVali(Vali vali) {
        this.vali = vali;
    }

    public boolean isShowMsg() {
        return isShowMsg;
    }

    public void setShowMsg(boolean showMsg) {
        isShowMsg = showMsg;
    }

    public static boolean isResume_wifi_anto_down() {
        return resume_wifi_anto_down;
    }

    public static void setResume_wifi_anto_down(boolean resume_wifi_anto_down) {
        Build.resume_wifi_anto_down = resume_wifi_anto_down;
    }
}
