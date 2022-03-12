package cn.wikitang.onlinemusic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Package: cn.wikitang.onlinemusic.config
 * @ClassName: Knife4jConfiguration
 * @Author: WikiTang
 * @Date: 2022/3/5 16:43
 * @Description:
 */
@Configuration
@EnableSwagger2
public class Knife4jConfiguration {

    @Bean
    public Docket defaultApi2() {
        ApiSelectorBuilder builder = new Docket(DocumentationType.SWAGGER_2)
                .enableUrlTemplating(false)
                .apiInfo(apiInfo())
                //选择哪些路径和api会生成document
                .select()
                //对所有api进行监控
                .apis(RequestHandlerSelectors.any())
                //这里可以自定义过滤
                .paths(this::filterPath);

        return builder.build();
    }

    private boolean filterPath(String path) {
        boolean ret = path.endsWith("/error");
        if (ret) {
            return false;
        }
        //这块可以写其他的过滤逻辑
        return true;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Online-Music系统api文档")
                .description("开发阶段api文档")
                .termsOfServiceUrl("http://localhost:8077/")
                .version("1.0")
                .build();
    }
}
