package com.github.hronosf.services.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.github.hronosf.services.S3ConnectorService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;

@Slf4j
@Getter
@Service
public class S3ConnectorServiceImpl implements S3ConnectorService {

    @Setter(onMethod = @__(@Autowired))
    private AmazonS3 s3Client;

    @Value("${s3.bucket.name}")
    private String s3BucketName;

    @PostConstruct
    public void createBucketIfNotExist() {
        if (!s3Client.doesBucketExistV2(s3BucketName)) {
            log.debug("Creating bucket {}", s3BucketName);
            s3Client.createBucket(new CreateBucketRequest(s3BucketName));
        }
    }

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
