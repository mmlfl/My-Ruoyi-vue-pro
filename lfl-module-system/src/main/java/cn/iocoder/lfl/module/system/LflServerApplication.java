package cn.iocoder.lfl.module.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("cn.iocoder.lfl")
public class LflServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(LflServerApplication.class, args);
    }
}
