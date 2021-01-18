package com.github.hronosf.tasks;

import com.github.hronosf.enums.Constants;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ScheduledTasks {

    @Async
    @SneakyThrows
    @Scheduled(cron = "0 0/3 * * * ?")
    public void cleanGeneratedFilesFolder() {
        log.debug("Scheduled job works - clearing folder {} with files", Constants.PATH.getValue());
        FileUtils.cleanDirectory(Constants.PATH.getValue().toFile());
    }
}
