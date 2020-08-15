package com.github.hronosf.aspects;

import com.github.hronosf.services.S3ConnectorService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@RequiredArgsConstructor
public class FileUploaderAspect {
    private final S3ConnectorService s3ConnectorService;

}
