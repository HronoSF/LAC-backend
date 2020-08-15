package com.github.hronosf.aspects;

import com.amazonaws.services.s3.AmazonS3;
import com.github.hronosf.services.S3ConnectorService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class FileUploaderAspect {

    @Getter
    @Value("${s3.bucket.name}")
    private String s3BucketName;

    private final S3ConnectorService s3ConnectorService;

    @Pointcut("execution(* com.github.hronosf.services.DocumentService.*(..))")
    public void documentCreated() { // required
    }

    @AfterReturning(value = "documentCreated()", returning = "path")
    public void uploadGeneratedDocumentInS3(String path) {
        String keyName = StringUtils.substringBetween(path, "generatedDocuments" + File.separator, "-");

        log.debug("Uploading generated doc to S3 to bucket:{} with path:{}/{}"
                , s3BucketName
                , keyName
                , StringUtils.substringAfter(path, "generatedDocuments" + File.separator));

        s3ConnectorService.uploadFileToS3(keyName, path, s3BucketName);
    }
}
