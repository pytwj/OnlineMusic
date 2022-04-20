package cn.wikitang.onlinemusic.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.wikitang.onlinemusic.common.utils.DTOBuilder;
import cn.wikitang.onlinemusic.common.utils.UserLoginToken;
import cn.wikitang.onlinemusic.common.utils.ValidatorUtils;
import cn.wikitang.onlinemusic.constant.Constants;
import cn.wikitang.onlinemusic.dao.SongListMapper;
import cn.wikitang.onlinemusic.dto.SongListDTO;
import cn.wikitang.onlinemusic.entity.SongList;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author twj
 * @since 2021-12-08
 */
@Api(tags = "歌单管理")
@RestController
@RequestMapping("/songList")
public class SongListController {

    @Autowired
    private SongListMapper songListMapper;

    @UserLoginToken
    @ApiOperation("添加歌单")
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addSongList(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        SongListDTO songListDTO = (SongListDTO) DTOBuilder.getDTO(request, SongListDTO.class);
        ValidatorUtils.validateDto(songListDTO);
        SongList songList = new SongList();
        BeanUtils.copyProperties(songListDTO, songList);
        boolean flag = songListMapper.insert(songList) > 0;
        if (flag) {
            jsonObject.put(Constants.CODE, 1);
            jsonObject.put(Constants.MSG, "保存成功");
        } else {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "保存失败");
        }
        return jsonObject;

    }

    @UserLoginToken
    @ApiOperation("修改歌单")
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object updateSongList(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        SongListDTO songListDTO = (SongListDTO) DTOBuilder.getDTO(request, SongListDTO.class);
        ValidatorUtils.validateDto(songListDTO);
        SongList songList = new SongList();
        BeanUtils.copyProperties(songListDTO, songList);
        songList.setId(Integer.valueOf(songListDTO.getId()));
        boolean flag = songListMapper.updateById(songList) > 0;
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
    @ApiOperation("删除歌单")
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object deleteSongList(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        SongListDTO songListDTO = (SongListDTO) DTOBuilder.getDTO(request, SongListDTO.class);
        ValidatorUtils.validateDto(songListDTO);
        boolean flag = songListMapper.deleteById(Integer.valueOf(songListDTO.getId())) > 0;
        if (flag) {
            jsonObject.put(Constants.CODE, 1);
            jsonObject.put(Constants.MSG, "删除成功");
        } else {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "删除失败");
        }
        return jsonObject;
    }

    @UserLoginToken
    @ApiOperation("查询所有歌单")
    @ResponseBody
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Object allSongList(HttpServletRequest request) {
        return songListMapper.selectList(null);
    }

    @ApiOperation("客户端查询所有歌单")
    @ResponseBody
    @RequestMapping(value = "/allForClient", method = RequestMethod.GET)
    public Object allForClient(HttpServletRequest request) {
        return songListMapper.selectList(null);
    }

    @UserLoginToken
    @ApiOperation("根据标题查询歌单")
    @ResponseBody
    @RequestMapping(value = "/songListOfTitle", method = RequestMethod.POST)
    public Object songListOfTitle(HttpServletRequest request) {
        SongListDTO songListDTO = (SongListDTO) DTOBuilder.getDTO(request, SongListDTO.class);
        ValidatorUtils.validateDto(songListDTO);
        LambdaQueryWrapper<SongList> titleQueryWrapper = new LambdaQueryWrapper<>();
        titleQueryWrapper.eq(SongList::getTitle, songListDTO.getTitle());
        return songListMapper.selectList(titleQueryWrapper);
    }


//    @UserLoginToken
    @ApiOperation("根据标题模糊查询")
    @ResponseBody
    @RequestMapping(value = "/likeTitle", method = RequestMethod.GET)
    public Object likeTitle(HttpServletRequest request) {
        SongListDTO songListDTO = (SongListDTO) DTOBuilder.getDTO(request, SongListDTO.class);
        ValidatorUtils.validateDto(songListDTO);
        LambdaQueryWrapper<SongList> titleQueryWrapper = new LambdaQueryWrapper<>();
        titleQueryWrapper.like(SongList::getTitle, songListDTO.getTitle());
        List<SongList> songList = songListMapper.selectList(titleQueryWrapper);
        if (CollectionUtil.isNotEmpty(songList)){
            return songList;
        }
        return null;
    }

//    @UserLoginToken
    @ApiOperation("根据风格模糊查询")
    @ResponseBody
    @RequestMapping(value = "/likeType", method = RequestMethod.GET)
    public Object likeType(HttpServletRequest request) {
        SongListDTO songListDTO = (SongListDTO) DTOBuilder.getDTO(request, SongListDTO.class);
        ValidatorUtils.validateDto(songListDTO);
        LambdaQueryWrapper<SongList> titleQueryWrapper = new LambdaQueryWrapper<>();
        titleQueryWrapper.like(SongList::getStyle, songListDTO.getStyle());
        List<SongList> songList = songListMapper.selectList(titleQueryWrapper);
        if (CollectionUtil.isNotEmpty(songList)) {
            return songList;
        }
        return null;
    }


//    @UserLoginToken
    @ApiOperation("更新歌单头像")
    @ResponseBody
    @RequestMapping(value = "/updateSongListPic", method = RequestMethod.POST)
    public Object updateSongListPic(@RequestParam("file") MultipartFile avatarPic, @RequestParam("id") Integer id) {
        JSONObject jsonObject = new JSONObject();
        if (avatarPic.isEmpty()) {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "更新失败");
        }
//        时间戳 + 文件名 防止上传相同名字的文件被覆盖
        String fileName = System.currentTimeMillis() + "_" + avatarPic.getOriginalFilename();
        String filePath = Constants.SONG_LIST_PIC_PATH;
        File file = new File(filePath);
//        判断存放目录是否存在
        if (!file.exists()) {
//            创建目录
            file.mkdir();
        }
        File saveFile = new File(filePath + fileName);
//        存储的路径
        String storePath = "/img/songListPic/" + fileName;
        try {
            avatarPic.transferTo(saveFile);
            SongList songList = new SongList();
            songList.setId(id);
            songList.setPic(storePath);
            boolean flag = songListMapper.updateById(songList) > 0;
            if (flag) {
                jsonObject.put(Constants.CODE, 1);
                jsonObject.put(Constants.MSG, "更新成功");
                jsonObject.put("pic", storePath);
            } else {
                jsonObject.put(Constants.CODE, 0);
                jsonObject.put(Constants.MSG, "更新失败");
            }
        } catch (IOException e) {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "更新失败" + e.getMessage());
            e.printStackTrace();
        }
        return jsonObject;
    }

    @ApiOperation("根据ID查询歌单信息")
    @ResponseBody
    @RequestMapping(value = "/songListOfId", method = RequestMethod.GET)
    public Object songListOfId(HttpServletRequest request) {
        SongListDTO songListDTO = (SongListDTO) DTOBuilder.getDTO(request, SongListDTO.class);
        ValidatorUtils.validateDto(songListDTO);
        LambdaQueryWrapper<SongList> idQueryWrapper = new LambdaQueryWrapper<>();
        idQueryWrapper.eq(SongList::getId, songListDTO.getId());
        return songListMapper.selectList(idQueryWrapper);
    }


}
