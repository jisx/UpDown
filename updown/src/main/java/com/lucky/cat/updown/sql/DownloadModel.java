package com.lucky.cat.updown.sql;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "DOWNLOAD_MODEL".
 */
public class DownloadModel {

    private String downLoadUrl;
    /** Not-null value. */
    private String savePath;
    private String fileName;
    private Long fileSize;
    private Long completeSize;
    private String validate;
    private java.util.Date createTime;

    public DownloadModel() {
    }

    public DownloadModel(String downLoadUrl) {
        this.downLoadUrl = downLoadUrl;
    }

    public DownloadModel(String downLoadUrl, String savePath, String fileName, Long fileSize, Long completeSize, String validate, java.util.Date createTime) {
        this.downLoadUrl = downLoadUrl;
        this.savePath = savePath;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.completeSize = completeSize;
        this.validate = validate;
        this.createTime = createTime;
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

    public Long getCompleteSize() {
        return completeSize;
    }

    public void setCompleteSize(Long completeSize) {
        this.completeSize = completeSize;
    }

    public String getValidate() {
        return validate;
    }

    public void setValidate(String validate) {
        this.validate = validate;
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
