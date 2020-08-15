package com.github.hronosf.services.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.github.hronosf.services.S3ConnectorService;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.File;

@Slf4j
public class S3ConnectorServiceImpl implements S3ConnectorService {

    @Setter(onMethod = @__(@Autowired))
    private AmazonS3 s3Client;

    @PostConstruct
    public void init() {
        createBucket("DocumentStorage");
    }

    public Bucket createBucket(String bucketName) {
        log.debug("Creating bucket {}", bucketName);
        return s3Client.createBucket(new CreateBucketRequest(bucketName));
    }

    @SneakyThrows
    public void uploadFileToS3(String keyName, String filePath, String bucketName) {
        log.debug("Uploading {} file to {} bucket", keyName, bucketName);
        TransferManager transferManager = TransferManagerBuilder.standard().withS3Client(s3Client).build();
        transferManager.upload(bucketName, keyName, new File(filePath));
    }

    public S3Object getFileFromS3(String bucketName, String key) {
        log.debug("Downloading an object");
        return s3Client.getObject(new GetObjectRequest(bucketName, key));
    }
}
