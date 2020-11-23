package spingboot.express;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@MapperScan(basePackages = "spingboot.*.mapper")
public class ExpressApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpressApplication.class, args);
    }

}
