package com.yhw.common.utils.oss;

/**
 * @author zhang.jiali4
 * @title UploadResultData
 * @description
 * @date 2019/10/23
 * @copyright Copyright ? 2010-2020 BYD Corporation. All rights reserved.
 */
public class UploadResultData {
    private String filename;

    private String ETag;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getETag() {
        return ETag;
    }

    public void setETag(String ETag) {
        this.ETag = ETag;
    }
}

