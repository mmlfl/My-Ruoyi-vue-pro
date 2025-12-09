package cn.iocoder.lfl.server;

import cn.iocoder.lfl.framework.security.config.LflSecurityAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = {"cn.iocoder.lfl.server","cn.iocoder.lfl.module"})
@MapperScan("cn.iocoder.lfl.module.system.mapper")
@Import(LflSecurityAutoConfiguration.class)
public class LflServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(LflServerApplication.class, args);
    }
}
