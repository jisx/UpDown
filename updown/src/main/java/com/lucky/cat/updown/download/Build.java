package com.lucky.cat.updown.download;

/**
 * Created by jisx on 2016/10/14.
 */
public class Build {
    /**
     * 是否开启debug
     */
    public boolean isDebug;
    /**
     * 同时下载的任务条数
     */
    public int numbersTask;
    /**
     * 任务添加时，是否立即开始
     */
    public boolean isStartNow;

    public Build(boolean isDebug, int numbersTask, boolean isStartNow) {
        this.isDebug = isDebug;
        this.numbersTask = numbersTask;
        this.isStartNow = isStartNow;
    }

    public static Build defaultBuild = new Build(false,5,false);
}
