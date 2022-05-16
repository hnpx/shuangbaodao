package cn.px.main.core.schedue.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;

@Component
@Slf4j
public class ExcelDeleteTask {

    @Value("${export.baseDir}")
    private String exportDir;

    @Scheduled(cron = "${export.cron}")
    public void dodelete(){
        File file = new File(exportDir);
        if(!file.exists()){
            log.error("yml配置中export.baseDir目录不存在");
            return;
        }
        Arrays.asList(file.listFiles()).forEach((child)->{
            if(!child.isDirectory() && child.renameTo(child)){
                if(!child.delete()){
                    log.error("删除"+child.getPath()+"失败");
                };
            }
        });

    }
}
