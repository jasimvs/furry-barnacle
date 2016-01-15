package com.egnyte.utils.auditreporter;

/**
 * Created by jsulaiman on 1/15/2016.
 */
class File {
    String fileId;
    long size;
    String fileName;
    long ownerUserId;

    File(String fileId, long size, String fileName, long ownerUserId) {
        this.fileId = fileId;
        this.size = size;
        this.fileName = fileName;
        this.ownerUserId = ownerUserId;
    }
}
