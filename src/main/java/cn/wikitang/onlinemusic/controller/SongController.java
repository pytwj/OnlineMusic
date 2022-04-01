package cn.wikitang.onlinemusic.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.wikitang.onlinemusic.common.exception.ApiException;
import cn.wikitang.onlinemusic.common.utils.DTOBuilder;
import cn.wikitang.onlinemusic.common.utils.UserLoginToken;
import cn.wikitang.onlinemusic.common.utils.ValidatorUtils;
import cn.wikitang.onlinemusic.constant.Constants;
import cn.wikitang.onlinemusic.dao.SongMapper;
import cn.wikitang.onlinemusic.dto.SongDTO;
import cn.wikitang.onlinemusic.entity.Song;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
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
@Api(tags = "歌曲管理")
@RestController
@RequestMapping("/song")
public class SongController {

    @Autowired
    private SongMapper songMapper;

//    @UserLoginToken
    @ApiOperation("添加歌曲")
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addSong(HttpServletRequest request, @RequestParam("file") MultipartFile multipartFile) {
        JSONObject jsonObject = new JSONObject();
        SongDTO songDTO = (SongDTO) DTOBuilder.getDTO(request, SongDTO.class);
        ValidatorUtils.validateDto(songDTO);
        if (multipartFile.isEmpty()){
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "歌曲上传失败");
            throw new ApiException("上传的文件为空，请核查！");
        }
//        文件名 时间戳 + 文件名
        String fileName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
        String filePath = Constants.SONG_PATH;
        File file = new File(filePath);
        if (!file.exists()){
            file.mkdir();
        }
//        存储路径
        File saveFile = new File(filePath + fileName);
        String savePath = "/song/" + fileName;
        try {
            multipartFile.transferTo(saveFile);
            Song song = new Song();
            BeanUtils.copyProperties(songDTO, song);
            song.setSingerId(Integer.valueOf(songDTO.getSingerId()));
            song.setCreateTime(new Date());
            song.setUpdateTime(new Date());
            song.setUrl(savePath);
            song.setPic(Constants.DEFAULT_SONG_PIC_PATH);
            boolean flag = songMapper.insert(song) > 0;
            if (flag) {
                jsonObject.put(Constants.CODE, 1);
                jsonObject.put(Constants.MSG, "添加成功");
            } else {
                jsonObject.put(Constants.CODE, 0);
                jsonObject.put(Constants.MSG, "添加失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @ApiOperation("根据歌手ID查询歌曲")
    @ResponseBody
    @RequestMapping(value = "/getBySingerId", method = RequestMethod.GET)
    public Object getBySingerId(HttpServletRequest request) {
        SongDTO songDTO = (SongDTO) DTOBuilder.getDTO(request, SongDTO.class);
        ValidatorUtils.validateDto(songDTO);
        Integer singerId = Integer.valueOf(songDTO.getSingerId());
        LambdaQueryWrapper<Song> songQueryWrapper = new LambdaQueryWrapper<>();
        songQueryWrapper.eq(Song::getSingerId,singerId);
        List<Song> songList = songMapper.selectList(songQueryWrapper);
        if (CollectionUtil.isNotEmpty(songList)){
            return songList;
        }
        return null;
    }

    @UserLoginToken
    @ApiOperation("修改歌曲信息")
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object updateSong(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        SongDTO songDTO = (SongDTO) DTOBuilder.getDTO(request, SongDTO.class);
        ValidatorUtils.validateDto(songDTO);
        Song song = new Song();
        BeanUtils.copyProperties(songDTO, song);
        song.setId(Integer.valueOf(songDTO.getId()));
        boolean flag = songMapper.updateById(song) > 0;
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
    @ApiOperation("删除歌曲")
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object deleteSinger(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        SongDTO songDTO = (SongDTO) DTOBuilder.getDTO(request, SongDTO.class);
        ValidatorUtils.validateDto(songDTO);
        Integer id = Integer.valueOf(songDTO.getId());
        Song songInfo = songMapper.selectById(id);
        if (songInfo == null){throw new ApiException("歌曲信息为空，请核查！");}
        String delPicPath = StringUtils.equals(Constants.ROOT_PATH + songInfo.getPic(), Constants.WHOLE_DEFAULT_SONG_PIC_PATH) ? "" : Constants.ROOT_PATH + songInfo.getPic();
        String delSongPath = Constants.ROOT_PATH + songInfo.getUrl();
        File delPic = new File(delPicPath);
        File delSong = new File(delSongPath);
        if (delPic.exists()){
            delPic.delete();
        }
        if (delSong.exists()){
            delSong.delete();
        }
        boolean flag = songMapper.deleteById(id) > 0;
        if (flag) {
            jsonObject.put(Constants.CODE, 1);
            jsonObject.put(Constants.MSG, "删除成功");
        } else {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "删除失败");
        }
        return jsonObject;
    }


//    @UserLoginToken
    @ApiOperation("更新歌手头像")
    @ResponseBody
    @RequestMapping(value = "/updateSongPic", method = RequestMethod.POST)
    public Object updateSongPic(@RequestParam("file") MultipartFile avatarPic, @RequestParam("id") Integer id) {
        JSONObject jsonObject = new JSONObject();
        if (avatarPic.isEmpty()) {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "更新失败");
        }
//        时间戳 + 文件名 防止上传相同名字的文件被覆盖
        String fileName = System.currentTimeMillis() + "_" + avatarPic.getOriginalFilename();
        String filePath = Constants.SONG_PIC_PATH;
        File file = new File(filePath);
//        判断存放目录是否存在
        if (!file.exists()) {
//            创建目录
            file.mkdir();
        }
        File saveFile = new File(filePath + fileName);
//        存储的路径
        String storePath = "/img/songPic/" + fileName;
        try {
            avatarPic.transferTo(saveFile);
            Song song = new Song();
            song.setId(id);
            song.setPic(storePath);
            boolean flag = songMapper.updateById(song) > 0;
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

//    @UserLoginToken
    @ApiOperation("更新歌手歌曲")
    @ResponseBody
    @RequestMapping(value = "/updateSongUrl", method = RequestMethod.POST)
    public Object updateSongUrl(@RequestParam("file") MultipartFile avatarPic, @RequestParam("id") Integer id) {
        JSONObject jsonObject = new JSONObject();
        if (avatarPic.isEmpty()) {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "更新失败");
        }
        Song songInfo = songMapper.selectById(id);
        if (songInfo == null) {
            throw new ApiException("歌曲信息不存在，请核查！");
        }
        String songUrl = Constants.ROOT_PATH + songInfo.getUrl();
        File delSong = new File(songUrl);
        if (delSong.exists()) {
            delSong.delete();
        }
//        时间戳 + 文件名 防止上传相同名字的文件被覆盖
        String fileName = System.currentTimeMillis() + "_" + avatarPic.getOriginalFilename();
        String filePath = Constants.SONG_PATH;
        File file = new File(filePath);
//        判断存放目录是否存在
        if (!file.exists()) {
//            创建目录
            file.mkdir();
        }
        File saveFile = new File(filePath + fileName);
//        存储的路径
        String storePath = "/song/" + fileName;
        try {
            avatarPic.transferTo(saveFile);
            Song song = new Song();
            song.setId(id);
            song.setUrl(storePath);
            boolean flag = songMapper.updateById(song) > 0;
            if (flag) {
                jsonObject.put(Constants.CODE, 1);
                jsonObject.put(Constants.MSG, "更新成功");
                jsonObject.put("url", storePath);
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

    @UserLoginToken
    @ApiOperation("根据歌曲ID查询歌曲")
    @ResponseBody
    @RequestMapping(value = "/getBySongId", method = RequestMethod.GET)
    public Object getBySongId(HttpServletRequest request) {
        SongDTO songDTO = (SongDTO) DTOBuilder.getDTO(request, SongDTO.class);
        ValidatorUtils.validateDto(songDTO);
        Integer id = Integer.valueOf(songDTO.getId());
        Song songInfo = songMapper.selectById(id);
        if (songInfo != null){
            return songInfo;
        }
        return null;
    }


    @UserLoginToken
    @ApiOperation("根据歌曲名查询歌曲")
    @ResponseBody
    @RequestMapping(value = "/getBySongName", method = RequestMethod.GET)
    public Object getBySongName(HttpServletRequest request) {
        SongDTO songDTO = (SongDTO) DTOBuilder.getDTO(request, SongDTO.class);
        ValidatorUtils.validateDto(songDTO);
        LambdaQueryWrapper<Song> songQueryWrapper = new LambdaQueryWrapper<>();
        songQueryWrapper.like(Song::getName,songDTO.getName());
        List<Song> songInfoList = songMapper.selectList(songQueryWrapper);
        if (CollectionUtil.isNotEmpty(songInfoList)){
            return songInfoList;
        }
        return null;
    }



}
