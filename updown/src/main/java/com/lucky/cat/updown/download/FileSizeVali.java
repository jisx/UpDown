package com.lucky.cat.updown.download;

import com.lucky.cat.updown.sql.DownloadModel;

import java.io.File;

/**
 * Created by jisx on 2016/10/20.
 */

public class FileSizeVali extends ValiDateFactory {

    @Override
    public boolean Verify(DownloadModel model) {
        File file = new File(model.getSavePath());
        if(file.exists()){
            return file.length() == model.getFileSize().longValue();
        }
        return false;
    }
}
