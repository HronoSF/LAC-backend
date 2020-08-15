package com.github.hronosf.services;

import com.amazonaws.services.s3.model.S3Object;

public interface S3ConnectorService {

    void uploadFileToS3(String keyName, String filePath, String bucketName);

    S3Object getFileFromS3(String bucketName, String key);
}