package cn.wikitang.onlinemusic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Package: cn.wikitang.onlinemusic.config
 * @ClassName: FileConfiguration
 * @Author: WikiTang
 * @Date: 2022/3/24 23:39
 * @Description:
 */
@Configuration
public class FileConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        歌手头像
        registry.addResourceHandler("/img/singerPic/**").addResourceLocations("file:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "img" + System.getProperty("file.separator") + "singerPic" + System.getProperty("file.separator"));
//        歌曲图片
        registry.addResourceHandler("/img/songPic/**").addResourceLocations("file:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "img" + System.getProperty("file.separator") + "songPic" + System.getProperty("file.separator"));
//        歌单图片
        registry.addResourceHandler("/img/songListPic/**").addResourceLocations("file:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "img" + System.getProperty("file.separator") + "songListPic" + System.getProperty("file.separator"));
//        歌曲
        registry.addResourceHandler("/song/**").addResourceLocations("file:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "song" + System.getProperty("file.separator"));
//        用户头像
        registry.addResourceHandler("/img/avatorImages/**").addResourceLocations("file:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "img" + System.getProperty("file.separator") + "avatorImages" + System.getProperty("file.separator"));
//        默认头像
        registry.addResourceHandler("/img/**").addResourceLocations("file:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "img" + System.getProperty("file.separator"));
    }
}
