package cn.wikitang.onlinemusic.service.impl;

import cn.wikitang.onlinemusic.entity.Comment;
import cn.wikitang.onlinemusic.dao.CommentMapper;
import cn.wikitang.onlinemusic.service.ICommentService;
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
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

}
