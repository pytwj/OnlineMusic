package cn.wikitang.onlinemusic.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.wikitang.onlinemusic.common.utils.DTOBuilder;
import cn.wikitang.onlinemusic.common.utils.DateUtil;
import cn.wikitang.onlinemusic.common.utils.UserLoginToken;
import cn.wikitang.onlinemusic.common.utils.ValidatorUtils;
import cn.wikitang.onlinemusic.constant.Constants;
import cn.wikitang.onlinemusic.dao.SingerMapper;
import cn.wikitang.onlinemusic.dao.SongMapper;
import cn.wikitang.onlinemusic.dto.SingerDTO;
import cn.wikitang.onlinemusic.entity.Singer;
import cn.wikitang.onlinemusic.entity.Song;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
@Api(tags = "歌手管理")
@RestController
@Controller
@RequestMapping("/singer")
public class SingerController {

    @Autowired
    private SingerMapper singerMapper;
    @Autowired
    private SongMapper songMapper;

    public static final JSONObject JSON_OBJECT = new JSONObject();

    @UserLoginToken
    @ApiOperation("添加歌手")
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addSinger(HttpServletRequest request, HttpSession session) {
        JSONObject jsonObject = new JSONObject();
        SingerDTO singerDTO = (SingerDTO) DTOBuilder.getDTO(request, SingerDTO.class);
        ValidatorUtils.validateDto(singerDTO);
        Singer singer = new Singer();
        Date birth = DateUtil.dateFormate(singerDTO.getBirth(),"yyyy-MM-dd");
        singer.setBirth(birth);
        singer.setSex(Integer.valueOf(singerDTO.getSex()));
        BeanUtils.copyProperties(singerDTO, singer);
        boolean flag = singerMapper.insert(singer) > 0;
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
    @ApiOperation("修改歌手")
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object updateSinger(HttpServletRequest request) {
        SingerDTO singerDTO = (SingerDTO) DTOBuilder.getDTO(request, SingerDTO.class);
        ValidatorUtils.validateDto(singerDTO);
        Singer singer = new Singer();
        singer.setId(Integer.valueOf(singerDTO.getId()));
        Date birth = DateUtil.dateFormate(singerDTO.getBirth(), Constants.YYYYMMDD);
        singer.setBirth(birth);
        singer.setSex(Integer.valueOf(singerDTO.getSex()));
        BeanUtils.copyProperties(singerDTO, singer);
        boolean flag = singerMapper.updateById(singer) > 0;
        if (flag) {
            JSON_OBJECT.put(Constants.CODE, 1);
            JSON_OBJECT.put(Constants.MSG, "修改成功");
        } else {
            JSON_OBJECT.put(Constants.CODE, 0);
            JSON_OBJECT.put(Constants.MSG, "修改失败");
        }
        return JSON_OBJECT;
    }

    @UserLoginToken
    @ApiOperation("删除歌手")
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object deleteSinger(HttpServletRequest request) {
        SingerDTO singerDTO = (SingerDTO) DTOBuilder.getDTO(request, SingerDTO.class);
        ValidatorUtils.validateDto(singerDTO);
        boolean flag = singerMapper.deleteById(Integer.valueOf(singerDTO.getId())) > 0;
        if (flag) {
            JSON_OBJECT.put(Constants.CODE, 1);
            JSON_OBJECT.put(Constants.MSG, "删除成功");
        } else {
            JSON_OBJECT.put(Constants.CODE, 0);
            JSON_OBJECT.put(Constants.MSG, "删除失败");
        }
        return JSON_OBJECT;
    }

    @UserLoginToken
    @ApiOperation("根据ID查询歌手")
    @ResponseBody
    @RequestMapping(value = "/selectByPrimaryKey", method = RequestMethod.GET)
    public Object selectByPrimaryKey(HttpServletRequest request) {
        SingerDTO singerDTO = (SingerDTO) DTOBuilder.getDTO(request, SingerDTO.class);
        ValidatorUtils.validateDto(singerDTO);
        return singerMapper.selectById(Integer.valueOf(singerDTO.getId()));
    }

    @ApiOperation("客户端根据ID查询歌手")
    @ResponseBody
    @RequestMapping(value = "/selectByPrimaryKeyForCli", method = RequestMethod.GET)
    public Object selectByPrimaryKeyForCli(HttpServletRequest request) {
        SingerDTO singerDTO = (SingerDTO) DTOBuilder.getDTO(request, SingerDTO.class);
        ValidatorUtils.validateDto(singerDTO);
        return singerMapper.selectById(Integer.valueOf(singerDTO.getId()));
    }

    @UserLoginToken
    @ApiOperation("查询所有歌手")
    @ResponseBody
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Object allSinger() {
        return singerMapper.selectList(null);
    }

    @ApiOperation("客户端查询所有歌手")
    @ResponseBody
    @RequestMapping(value = "/allForClient", method = RequestMethod.GET)
    public Object allForClient() {
//        总记录数
        Integer count = singerMapper.selectCount(null);
//        随机开始位置
        Integer randomCount = (int) (Math.random() * count);
//        保证能展示10条数据
        if (randomCount > count-10) {
            randomCount = count -10;
        }
        LambdaQueryWrapper<Singer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Singer::getId);
        queryWrapper.last("limit " + String.valueOf(randomCount) + ",10");
        return singerMapper.selectList(queryWrapper);
    }

    @UserLoginToken
    @ApiOperation("根据歌手名模糊查询")
    @ResponseBody
    @RequestMapping(value = "/singerOfName", method = RequestMethod.GET)
    public Object singerOfName(HttpServletRequest request) {
        SingerDTO singerDTO = (SingerDTO) DTOBuilder.getDTO(request, SingerDTO.class);
        ValidatorUtils.validateDto(singerDTO);
        LambdaQueryWrapper<Singer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Singer::getName, singerDTO.getName());
        return singerMapper.selectOne(queryWrapper);
    }

//    @UserLoginToken
    @ApiOperation("根据性别查询")
    @ResponseBody
    @RequestMapping(value = "/singerOfSex", method = RequestMethod.GET)
    public Object singerOfSex(HttpServletRequest request) {
        SingerDTO singerDTO = (SingerDTO) DTOBuilder.getDTO(request, SingerDTO.class);
        ValidatorUtils.validateDto(singerDTO);
        LambdaQueryWrapper<Singer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Singer::getSex, Integer.valueOf(singerDTO.getSex()));
        List<Singer> singerList = singerMapper.selectList(queryWrapper);
        if (CollectionUtil.isNotEmpty(singerList)) {
            return singerList;
        }
        return null;
    }

//    @UserLoginToken
    @ApiOperation("更新歌手头像")
    @ResponseBody
    @RequestMapping(value = "/updateSingerPic", method = RequestMethod.POST)
    public Object updateSingerPic(@RequestParam("file") MultipartFile avatarPic, @RequestParam("id") Integer id) {
        if (avatarPic.isEmpty()) {
            JSON_OBJECT.put(Constants.CODE, 0);
            JSON_OBJECT.put(Constants.MSG, "更新失败");
        }
//        时间戳 + 文件名 防止上传相同名字的文件被覆盖
        String fileName = System.currentTimeMillis() + "_" + avatarPic.getOriginalFilename();
        String filePath = Constants.SINGER_PIC_PATH;
        File file = new File(filePath);
//        判断存放目录是否存在
        if (!file.exists()) {
//            创建目录
            file.mkdir();
        }
        File saveFile = new File(filePath + fileName);
//        存储的路径
        String storePath = "/img/singerPic/" + fileName;
        try {
            avatarPic.transferTo(saveFile);
            Singer singer = new Singer();
            singer.setId(id);
            singer.setPic(storePath);
            boolean flag = singerMapper.updateById(singer) > 0;
            if (flag) {
                JSON_OBJECT.put(Constants.CODE, 1);
                JSON_OBJECT.put(Constants.MSG, "更新成功");
                JSON_OBJECT.put("pic", storePath);
            } else {
                JSON_OBJECT.put(Constants.CODE, 0);
                JSON_OBJECT.put(Constants.MSG, "更新失败");
            }
        } catch (IOException e) {
            JSON_OBJECT.put(Constants.CODE, 0);
            JSON_OBJECT.put(Constants.MSG, "更新失败" + e.getMessage());
            e.printStackTrace();
        }
        return JSON_OBJECT;
    }


    @UserLoginToken
    @ApiOperation("根据歌手名获取歌手ID再获取歌曲")
    @ResponseBody
    @RequestMapping(value = "/singerIdGetSong", method = RequestMethod.GET)
    public Object singerIdGetSong(HttpServletRequest request) {
        SingerDTO singerDTO = (SingerDTO) DTOBuilder.getDTO(request, SingerDTO.class);
        ValidatorUtils.validateDto(singerDTO);
        LambdaQueryWrapper<Singer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Singer::getName, singerDTO.getName());
        Singer singer = singerMapper.selectOne(queryWrapper);
        if (singer != null) {
            LambdaQueryWrapper<Song> songQueryWrapper = new LambdaQueryWrapper<>();
            songQueryWrapper.eq(Song::getSingerId,singer.getId());
            List<Song> songList = songMapper.selectList(songQueryWrapper);
            if (CollectionUtil.isNotEmpty(songList)){
                return songList;
            }
        }
        return null;
    }
}
