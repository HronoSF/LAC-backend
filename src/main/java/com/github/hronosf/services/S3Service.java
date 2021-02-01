package com.github.hronosf.services;

import com.amazonaws.services.s3.model.S3Object;
import com.github.hronosf.dto.DocumentDataResponseDTO;

import java.util.List;

public interface S3Service {

    void uploadFileToS3(String keyName, String filePath, String bucketName);

    S3Object getFileFromS3(String key);

    String getS3BucketName();

    List<DocumentDataResponseDTO> listS3bucket(String prefix);
}
