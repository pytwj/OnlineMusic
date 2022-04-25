package cn.wikitang.onlinemusic.dao;

import cn.wikitang.onlinemusic.dto.CommentWithCustomerDTO;
import cn.wikitang.onlinemusic.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author twj
 * @since 2021-12-08
 */
@Repository
public interface CommentMapper extends BaseMapper<Comment> {

    @Select("select * from comment a left join consumer b on a.user_id = b.id where a.song_id = #{songId}")
    List<CommentWithCustomerDTO> getCommentWithUserInfo(Integer songId);

    @Select("select * from comment a left join consumer b on a.user_id = b.id where a.song_list_id = #{songListId}")
    List<CommentWithCustomerDTO> getCommentWithUserInfoBySongListId(Integer songListId);
}
