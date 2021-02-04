package com.github.hronosf.services.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.github.hronosf.services.S3Service;
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
public class S3ServiceImpl implements S3Service {

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

    @Override
    public void uploadFileToS3(String keyName, String filePath, String bucketName) {
        log.debug("Uploading generated doc to S3 to bucket:{} with path:{}"
                , bucketName
                , keyName);

        TransferManager transferManager = TransferManagerBuilder.standard().withS3Client(s3Client).build();
        transferManager.upload(bucketName, keyName, new File(filePath));
    }

    @Override
    public S3Object getFileFromS3(String key) {
        log.debug("Downloading an object {}/{}", s3BucketName, key);
        return s3Client.getObject(new GetObjectRequest(s3BucketName, key));
    }

    @Override
    public String getS3Url(String key) {
        return s3Client.getUrl(s3BucketName, key).toString();
    }
}
