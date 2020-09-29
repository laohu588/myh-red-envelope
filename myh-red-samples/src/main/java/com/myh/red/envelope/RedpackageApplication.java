package com.myh.red.envelope;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 抢红包示例;
 */
@SpringBootApplication
public class RedpackageApplication {

    public static void main(String[] args) {

        SpringApplication.run(RedpackageApplication.class, args);
        System.out.println("## redpackage server is running now ...........");

    }

}
