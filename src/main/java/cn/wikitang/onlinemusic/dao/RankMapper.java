package cn.wikitang.onlinemusic.dao;

import cn.wikitang.onlinemusic.entity.Rank;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author twj
 * @since 2021-12-08
 */
@Repository
public interface RankMapper extends BaseMapper<Rank> {

    @Select("SELECT COALESCE(SUM(score),0) AS score FROM rank_info WHERE songListId = #{songListId}")
    Long getScoreRanks(@Param("songListId") Long songListId);

}
