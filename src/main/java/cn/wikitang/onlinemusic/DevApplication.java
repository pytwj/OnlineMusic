package cn.wikitang.onlinemusic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Package: cn.wikitang.onlinemusic
 * @ClassName: DevApplication
 * @Author: WikiTang
 * @Date: 2021/12/8 22:58
 * @Description:
 */
@EnableSwagger2
@SpringBootApplication
//@MapperScan(basePackages = {"cn.wikitang.*.dao"})
public class DevApplication {

    private final static Logger logger = LoggerFactory.getLogger(DevApplication.class);

    public static void main(String[] args) {
        logger.info("*****************  正在启动服务，请稍后。 *****************");
        SpringApplication.run(DevApplication.class, args);
        logger.info("*****************  OnlineMusicApplication 服务启动成功！ *****************");
    }
}