package com.project.questday.global.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.Duration;

@Component
public class LogCleanupScheduler {

    private static final String LOG_DIR = "logs"; // logback 설정과 일치해야 함

    @Scheduled(cron = "0 0 3 * * *") // 매일 새벽 3시
    public void deleteOldLogs() {
        File logDir = new File(LOG_DIR);
        if (!logDir.exists()) return;

        File[] files = logDir.listFiles();
        if (files == null) return;

        long now = System.currentTimeMillis();
        long expireTime = Duration.ofDays(7).toMillis();

        for (File file : files) {
            if (file.isFile() && now - file.lastModified() > expireTime) {
                if (file.delete()) {
                    System.out.println("삭제된 로그 파일: " + file.getName());
                }
            }
        }
    }
}
