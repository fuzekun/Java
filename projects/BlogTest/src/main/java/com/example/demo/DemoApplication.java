package com.example.demo;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    //记录器
    Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void test(){
        logger.trace("这是trace日志");

        logger.debug("debug");

        logger.info("info");

        logger.warn("warning");

        logger.error("error");
    }
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
