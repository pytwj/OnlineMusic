package cn.wikitang.onlinemusic.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.wikitang.onlinemusic.common.exception.ApiException;
import cn.wikitang.onlinemusic.common.utils.DTOBuilder;
import cn.wikitang.onlinemusic.common.utils.UserLoginToken;
import cn.wikitang.onlinemusic.common.utils.ValidatorUtils;
import cn.wikitang.onlinemusic.constant.Constants;
import cn.wikitang.onlinemusic.dao.ListSongMapper;
import cn.wikitang.onlinemusic.dto.ListSongDTO;
import cn.wikitang.onlinemusic.entity.ListSong;
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
@Api(tags = "歌单歌曲管理")
@RestController
@RequestMapping("/listSong")
public class ListSongController {

    @Autowired
    private ListSongMapper listSongMapper;

    @UserLoginToken
    @ApiOperation("添加歌曲")
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addListSong(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        ListSongDTO listSongDTO = (ListSongDTO) DTOBuilder.getDTO(request, ListSongDTO.class);
        ValidatorUtils.validateDto(listSongDTO);
        ListSong listSong = new ListSong();
        Integer songId = Integer.valueOf(listSongDTO.getSongId());
        LambdaQueryWrapper<ListSong> songIdQueryWrapper = new LambdaQueryWrapper<>();
        songIdQueryWrapper.eq(ListSong::getSongId, songId);
        ListSong listSongList = listSongMapper.selectOne(songIdQueryWrapper);
        if (listSongList != null) {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "请不要添加重复歌曲，请核查！");
            throw new ApiException("请不要添加重复歌曲，请核查！");
        }
        listSong.setSongId(songId);
        listSong.setSongListId(Integer.valueOf(listSongDTO.getSongListId()));
        boolean flag = listSongMapper.insert(listSong) > 0;
        if (flag) {
            jsonObject.put(Constants.CODE, 1);
            jsonObject.put(Constants.MSG, "添加成功");
        } else {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "添加失败");
        }
        return jsonObject;
    }

    @UserLoginToken
    @ApiOperation("根据歌单ID查询歌单")
    @ResponseBody
    @RequestMapping(value = "/getByListSong", method = RequestMethod.GET)
    public Object getByListSong(HttpServletRequest request) {
        ListSongDTO listSongDTO = (ListSongDTO) DTOBuilder.getDTO(request, ListSongDTO.class);
        ValidatorUtils.validateDto(listSongDTO);
        Integer songListId = Integer.parseInt(listSongDTO.getSongListId());
        LambdaQueryWrapper<ListSong> songListQueryWrapper = new LambdaQueryWrapper<>();
        songListQueryWrapper.eq(ListSong::getSongListId, songListId);
        List<ListSong> songList = listSongMapper.selectList(songListQueryWrapper);
        if (CollectionUtil.isNotEmpty(songList)){
            return songList;
        }
        return null;
    }

    @ApiOperation("根据歌单ID查询歌单")
    @ResponseBody
    @RequestMapping(value = "/getByListSongForCli", method = RequestMethod.GET)
    public Object getByListSongForCli(HttpServletRequest request) {
        ListSongDTO listSongDTO = (ListSongDTO) DTOBuilder.getDTO(request, ListSongDTO.class);
        ValidatorUtils.validateDto(listSongDTO);
        Integer songListId = Integer.parseInt(listSongDTO.getSongListId());
        LambdaQueryWrapper<ListSong> songListQueryWrapper = new LambdaQueryWrapper<>();
        songListQueryWrapper.eq(ListSong::getSongListId, songListId);
        List<ListSong> songList = listSongMapper.selectList(songListQueryWrapper);
        if (CollectionUtil.isNotEmpty(songList)){
            return songList;
        }
        return null;
    }

    @UserLoginToken
    @ApiOperation("修改歌单信息")
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object updateListSong(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        ListSongDTO listSongDTO = (ListSongDTO) DTOBuilder.getDTO(request, ListSongDTO.class);
        ValidatorUtils.validateDto(listSongDTO);
        ListSong listSong = new ListSong();
        listSong.setId(Integer.valueOf(listSongDTO.getId()));
        listSong.setSongId(Integer.valueOf(listSong.getSongListId()));
        listSong.setSongListId(Integer.valueOf(listSong.getSongListId()));
        boolean flag = listSongMapper.updateById(listSong) > 0;
        if (flag) {
            jsonObject.put(Constants.CODE, 1);
            jsonObject.put(Constants.MSG, "修改成功");
        } else {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "修改失败");
        }
        return jsonObject;
    }

    @UserLoginToken
    @ApiOperation("删除歌单歌曲")
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Object deleteListSong(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        ListSongDTO listSongDTO = (ListSongDTO) DTOBuilder.getDTO(request, ListSongDTO.class);
        ValidatorUtils.validateDto(listSongDTO);
        Integer songId = Integer.valueOf(listSongDTO.getSongId());
        Integer songListId = Integer.valueOf(listSongDTO.getSongListId());
        LambdaQueryWrapper<ListSong> songIdQueryWrapper = new LambdaQueryWrapper<>();
        songIdQueryWrapper.eq(ListSong::getSongId, songId)
                .eq(ListSong::getSongListId, songListId);
        boolean flag = listSongMapper.delete(songIdQueryWrapper) > 0;
        if (flag) {
            jsonObject.put(Constants.CODE, 1);
            jsonObject.put(Constants.MSG, "删除成功");
        } else {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "删除失败");
        }
        return jsonObject;
    }



}
