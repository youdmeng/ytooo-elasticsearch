package com.ytooo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ServletComponentScan
@EnableScheduling
@EnableAsync
public class Application {
    //
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(AdcDaApplication.class);
//    }
//
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
        // 启动Metrics 性能监控报表
        //		CsvReporter reporter = applicationContext.getBean(CsvReporter.class);
        //reporter.start(1, TimeUnit.MINUTES);
    }
}
