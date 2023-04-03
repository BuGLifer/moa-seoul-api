package kr.buglifer.moaseoul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MoaseoulApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoaseoulApplication.class, args);
    }

}
