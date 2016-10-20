package com.lucky.cat.updown.download;

/**
 * Created by jisx on 2016/10/14.
 */
public class Build {
    /**
     * 是否开启debug
     */
    public static boolean isDebug = true;
    /**
     * 同时下载的任务条数
     */
    public static int numbersTask = 1;
    /**
     * 任务添加时，是否立即开始
     */
    public static boolean isStartNow = false;
    /**
     * 任务下载完成，是否立即开始下载任务
     */
    public static boolean isStartNext = true;
    /**
     * 沿用上次下载记录
     */
    public static boolean followRecord = true;
    /**
     * 只能在wifi下 执行下载
     */
    public static boolean under_wifi = true;

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

    private Build() {
    }

    public static Build Build() {
        return new Build();
    }

    public boolean isDebug() {
        return isDebug;
    }

    public void setIsDebug(boolean isDebug) {
        Build.isDebug = isDebug;
    }

    public int getNumbersTask() {
        return numbersTask;
    }

    public void setNumbersTask(int numbersTask) {
        Build.numbersTask = numbersTask;
    }

    public boolean isStartNow() {
        return isStartNow;
    }

    public void setIsStartNow(boolean isStartNow) {
        Build.isStartNow = isStartNow;
    }

    public boolean isStartNext() {
        return isStartNext;
    }

    public void setIsStartNext(boolean isStartNext) {
        Build.isStartNext = isStartNext;
    }

    public boolean isFollowRecord() {
        return followRecord;
    }

    public void setFollowRecord(boolean followRecord) {
        Build.followRecord = followRecord;
    }

    public boolean isUnder_wifi() {
        return under_wifi;
    }

    public void setUnder_wifi(boolean under_wifi) {
        Build.under_wifi = under_wifi;
    }

    public boolean isResume_wifi_anto_down() {
        return resume_wifi_anto_down;
    }

    public void setResume_wifi_anto_down(boolean resume_wifi_anto_down) {
        Build.resume_wifi_anto_down = resume_wifi_anto_down;
    }
}
