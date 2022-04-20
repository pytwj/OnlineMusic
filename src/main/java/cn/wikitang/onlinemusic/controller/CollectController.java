package cn.wikitang.onlinemusic.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.wikitang.onlinemusic.common.utils.DTOBuilder;
import cn.wikitang.onlinemusic.common.utils.UserLoginToken;
import cn.wikitang.onlinemusic.common.utils.ValidatorUtils;
import cn.wikitang.onlinemusic.constant.Constants;
import cn.wikitang.onlinemusic.dao.CollectMapper;
import cn.wikitang.onlinemusic.dto.CollectDTO;
import cn.wikitang.onlinemusic.entity.Collect;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author twj
 * @since 2021-12-08
 */
@Api(tags = "收藏模块")
@RestController
@RequestMapping("/collect")
public class CollectController {

    @Autowired
    private CollectMapper collectMapper;

    @ApiOperation("添加收藏")
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addCollect(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        CollectDTO collectDTO = (CollectDTO) DTOBuilder.getDTO(request, CollectDTO.class);
        ValidatorUtils.validateDto(collectDTO);
        if (StringUtils.isBlank(collectDTO.getSongId())) {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "收藏的歌曲为空");
            return jsonObject;
        }
        LambdaQueryWrapper<Collect> collectQueryWrapper = new LambdaQueryWrapper<>();
        collectQueryWrapper.eq(Collect::getUserId, Integer.parseInt(collectDTO.getUserId()))
                .eq(Collect::getSongId, Integer.parseInt(collectDTO.getSongId()));
        Collect existCollect = collectMapper.selectOne(collectQueryWrapper);
        if (existCollect != null) {
            boolean isDel = collectMapper.delete(collectQueryWrapper) > 0;
            if (isDel) {
                jsonObject.put(Constants.CODE, 2);
                jsonObject.put(Constants.MSG, "取消收藏");
                return jsonObject;
            }
        }
        Collect collect = new Collect();
        collect.setUserId(Integer.parseInt(collectDTO.getUserId()));
//        0-歌曲 1-歌单
        collect.setType(Integer.parseInt(collectDTO.getType()));
        collect.setSongId(Integer.parseInt(collectDTO.getSongId()));
        if (StringUtils.isNotEmpty(collectDTO.getSongListId())) {
            collect.setSongListId(Integer.parseInt(collectDTO.getSongListId()));
        }
        collect.setCreateTime(new Date());
        boolean flag = collectMapper.insert(collect) > 0;
        if (flag) {
            jsonObject.put(Constants.CODE, 1);
            jsonObject.put(Constants.MSG, "收藏成功");
        } else {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "收藏失败");
        }
        return jsonObject;
    }

    @UserLoginToken
    @ApiOperation("删除收藏")
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object deleteCollect(HttpServletRequest request) {
        CollectDTO collectDTO = (CollectDTO) DTOBuilder.getDTO(request, CollectDTO.class);
        ValidatorUtils.validateDto(collectDTO);
        Integer userId = Integer.parseInt(collectDTO.getUserId());
        Integer songId = Integer.parseInt(collectDTO.getSongId());
        LambdaQueryWrapper<Collect> deleteQueryWrapper = new LambdaQueryWrapper<>();
        deleteQueryWrapper.eq(Collect::getUserId, userId)
                .eq(Collect::getSongId, songId);
        boolean flag = collectMapper.delete(deleteQueryWrapper) > 0;
        if (flag) {
            return flag;
        }
        return null;
    }

    @ApiOperation("查询所有收藏")
    @ResponseBody
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Object allCollect(HttpServletRequest request) {
        return collectMapper.selectList(null);
    }

    @ApiOperation("查询某个用户收藏")
    @ResponseBody
    @RequestMapping(value = "/getByUserId", method = RequestMethod.GET)
    public Object getByUserId(HttpServletRequest request) {
        CollectDTO collectDTO = (CollectDTO) DTOBuilder.getDTO(request, CollectDTO.class);
        ValidatorUtils.validateDto(collectDTO);
        LambdaQueryWrapper<Collect> collectQueryWrapper = new LambdaQueryWrapper<>();
        collectQueryWrapper.eq(Collect::getUserId, Integer.parseInt(collectDTO.getUserId()));
        List<Collect> collectList = collectMapper.selectList(collectQueryWrapper);
        if (CollectionUtil.isNotEmpty(collectList)) {
            return collectList;
        }
        return null;
    }

}
