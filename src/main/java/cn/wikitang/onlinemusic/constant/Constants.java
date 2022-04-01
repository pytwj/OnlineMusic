package cn.wikitang.onlinemusic.constant;

/**
 * @Package: cn.wikitang.onlinemusic.constant
 * @ClassName: Enum
 * @Author: WikiTang
 * @Date: 2022/3/25 21:23
 * @Description:
 */
public class Constants {
    public static final String CODE = "code";
    public static final String MSG = "msg";
    /**根路径*/
    public static final String ROOT_PATH = System.getProperty("user.dir") + System.getProperty("file.separator");
    /**歌手图片存放路径*/
    public static final String SINGER_PIC_PATH = System.getProperty("user.dir") + System.getProperty("file.separator") + "img" + System.getProperty("file.separator") + "singerPic" + System.getProperty("file.separator");
    /**歌曲图片存放路径*/
    public static final String SONG_PIC_PATH = System.getProperty("user.dir") + System.getProperty("file.separator") + "img" + System.getProperty("file.separator") + "songPic" + System.getProperty("file.separator");
    /**歌单图片存放路径*/
    public static final String SONG_LIST_PIC_PATH = System.getProperty("user.dir") + System.getProperty("file.separator") + "img" + System.getProperty("file.separator") + "songListPic" + System.getProperty("file.separator");
    /**默认歌曲图片存放路径*/
    public static final String DEFAULT_SONG_PIC_PATH = "/img/songPic/tubiao.jpg";
    /**默认歌曲图片完整存放路径*/
    public static final String WHOLE_DEFAULT_SONG_PIC_PATH = ROOT_PATH + DEFAULT_SONG_PIC_PATH;
    /**歌曲存放路径*/
    public static final String SONG_PATH = System.getProperty("user.dir") + System.getProperty("file.separator") + "song" + System.getProperty("file.separator");
    /**用户头像存放路径*/
    public static final String CONSUMER_PIC_PATH = System.getProperty("user.dir") + System.getProperty("file.separator") + "img" + System.getProperty("file.separator") + "avatorImages" + System.getProperty("file.separator");
    public static final String YYYYMMDD = "yyyy-MM-dd";
}