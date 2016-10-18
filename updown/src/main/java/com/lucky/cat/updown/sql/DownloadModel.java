package com.lucky.cat.updown.sql;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "DOWNLOAD_MODEL".
 */
public class DownloadModel {

    private Long id;
    private String downLoadUrl;
    /** Not-null value. */
    private String savePath;
    private String fileName;
    private Long fileSize;
    private String MD5;
    private java.util.Date createTime;

    public DownloadModel() {
    }

    public DownloadModel(Long id, String downLoadUrl) {
        this.id = id;
        this.downLoadUrl = downLoadUrl;
    }

    public DownloadModel(Long id, String downLoadUrl, String savePath, String fileName, Long fileSize, String MD5, java.util.Date createTime) {
        this.id = id;
        this.downLoadUrl = downLoadUrl;
        this.savePath = savePath;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.MD5 = MD5;
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDownLoadUrl() {
        return downLoadUrl;
    }

    public void setDownLoadUrl(String downLoadUrl) {
        this.downLoadUrl = downLoadUrl;
    }

    /** Not-null value. */
    public String getSavePath() {
        return savePath;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getMD5() {
        return MD5;
    }

    public void setMD5(String MD5) {
        this.MD5 = MD5;
    }

    public java.util.Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DownloadModel that = (DownloadModel) o;

        return downLoadUrl != null ? downLoadUrl.equals(that.downLoadUrl) : that.downLoadUrl == null;

    }

    @Override
    public int hashCode() {
        return downLoadUrl != null ? downLoadUrl.hashCode() : 0;
    }
}
