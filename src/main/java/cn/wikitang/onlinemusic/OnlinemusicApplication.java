package cn.wikitang.onlinemusic;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
//@ComponentScan(value={"cn.wikitang.**"})
@MapperScan(basePackages = {"cn.wikitang.*.dao"})
public class OnlinemusicApplication {

	private final static Logger logger = LoggerFactory.getLogger(OnlinemusicApplication.class);

	public static void main(String[] args) {
		logger.info("*****************  正在启动服务，请稍后。 *****************");
		try {
			SpringApplication.run(OnlinemusicApplication.class, args);
			logger.info("*****************  OnlineMusicApplication 服务启动成功！ *****************");
		} catch (Exception e) {
			logger.error("服务启动失败:{}",e.getMessage(),e);
		}
	}

}
