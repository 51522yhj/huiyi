package com.easymeeting;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@SpringBootApplication
@MapperScan("com.easymeeting.mappers")
public class EasyMeetingJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyMeetingJavaApplication.class, args);
    }

}
