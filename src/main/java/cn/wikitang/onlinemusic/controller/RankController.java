package cn.wikitang.onlinemusic.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.wikitang.onlinemusic.common.exception.ApiException;
import cn.wikitang.onlinemusic.common.utils.DTOBuilder;
import cn.wikitang.onlinemusic.common.utils.ValidatorUtils;
import cn.wikitang.onlinemusic.constant.Constants;
import cn.wikitang.onlinemusic.dao.RankMapper;
import cn.wikitang.onlinemusic.dto.RankDTO;
import cn.wikitang.onlinemusic.entity.Rank;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author twj
 * @since 2021-12-08
 */
@Api(tags = "评分模块")
@RestController
@RequestMapping("/rank")
public class RankController {

    @Autowired
    private RankMapper rankMapper;


    @ApiOperation("评分")
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addRank(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        RankDTO rankDTO = (RankDTO) DTOBuilder.getDTO(request, RankDTO.class);
        ValidatorUtils.validateDto(rankDTO);
        Rank rank = new Rank();
        rank.setSongListId(Long.valueOf(rankDTO.getSongListId()));
        rank.setConsumerId(Long.valueOf(rankDTO.getConsumerId()));
        rank.setScore(Integer.valueOf(rankDTO.getScore()));
        try {
            boolean flag = rankMapper.insert(rank) > 0;
            if (flag) {
                jsonObject.put(Constants.CODE, 1);
                jsonObject.put(Constants.MSG, "评分成功");
            } else {
                jsonObject.put(Constants.CODE, 0);
                jsonObject.put(Constants.MSG, "评分失败");
            }
        }catch (Exception e){
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "您已经评分过了");
            throw new ApiException("您已经评分过了");
        }
        return jsonObject;
    }

    @ApiOperation("计算评分平均分")
    @ResponseBody
    @RequestMapping(value = "/score", method = RequestMethod.GET)
    public Object getScore(HttpServletRequest request) {
        RankDTO rankDTO = (RankDTO) DTOBuilder.getDTO(request, RankDTO.class);
        ValidatorUtils.validateDto(rankDTO);
        Long songListId = Long.valueOf(rankDTO.getSongListId());
        LambdaQueryWrapper<Rank> scoreQueryWrapper = new LambdaQueryWrapper<>();
        scoreQueryWrapper.eq(Rank::getSongListId,songListId);
        List<Rank> rankList = rankMapper.selectList(scoreQueryWrapper);
        if (CollectionUtil.isNotEmpty(rankList)) {
            Long scoreSum = rankList.stream().mapToLong(Rank::getScore).sum();
            return scoreSum / rankList.size() / 2;
        }
//        Long scoreSum = rankMapper.getScoreRanks(songListId);
        return null;
    }


}
