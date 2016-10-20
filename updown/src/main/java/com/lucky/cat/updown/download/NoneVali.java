package com.lucky.cat.updown.download;

import com.lucky.cat.updown.sql.DownloadModel;

/**
 * Created by jisx on 2016/10/20.
 */

public class NoneVali extends ValiDateFactory {
    @Override
    public boolean Verify(DownloadModel model) {
        return true;
    }
}
