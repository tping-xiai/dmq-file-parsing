package com.jfinteck.dmq;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DmqFileParsingApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DmqFileParsingApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("执行该方法.....");
    }
}

