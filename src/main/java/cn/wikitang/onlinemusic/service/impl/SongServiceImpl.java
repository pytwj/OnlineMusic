package cn.wikitang.onlinemusic.service.impl;

import cn.wikitang.onlinemusic.entity.Song;
import cn.wikitang.onlinemusic.dao.SongMapper;
import cn.wikitang.onlinemusic.service.ISongService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author twj
 * @since 2021-12-08
 */
@Service
public class SongServiceImpl extends ServiceImpl<SongMapper, Song> implements ISongService {

}
