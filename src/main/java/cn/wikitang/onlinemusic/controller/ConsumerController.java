package cn.wikitang.onlinemusic.controller;

import cn.wikitang.onlinemusic.common.exception.ApiException;
import cn.wikitang.onlinemusic.common.utils.DTOBuilder;
import cn.wikitang.onlinemusic.common.utils.DateUtil;
import cn.wikitang.onlinemusic.common.utils.UserLoginToken;
import cn.wikitang.onlinemusic.common.utils.ValidatorUtils;
import cn.wikitang.onlinemusic.constant.Constants;
import cn.wikitang.onlinemusic.dao.ConsumerMapper;
import cn.wikitang.onlinemusic.dto.ConsumerDTO;
import cn.wikitang.onlinemusic.entity.Consumer;
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
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author twj
 * @since 2021-12-08
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Autowired
    private ConsumerMapper consumerMapper;

    @UserLoginToken
    @ApiOperation("添加用户")
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addConsumer(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        ConsumerDTO consumerDTO = (ConsumerDTO) DTOBuilder.getDTO(request, ConsumerDTO.class);
        ValidatorUtils.validateDto(consumerDTO);
        if (StringUtils.isEmpty(consumerDTO.getUsername())) {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "用户名不能为空！");
            throw new ApiException("用户名不能为空！");
        }
        if (StringUtils.isEmpty(consumerDTO.getPassword())) {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "密码不能为空！");
            throw new ApiException("密码不能为空！");
        }
        List<Consumer> consumerList = consumerMapper.selectList(null);
        List<String> emailList = consumerList.stream().map(Consumer::getEmail).collect(Collectors.toList());
        List<String> userNameList = consumerList.stream().map(Consumer::getUsername).collect(Collectors.toList());
        boolean isUniqueEmail = emailList.contains(consumerDTO.getEmail());
        boolean isUniqueUserName = userNameList.contains(consumerDTO.getUsername());
        if (isUniqueEmail) {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "邮箱已被注册过，请核查！");
            throw new ApiException("邮箱已被注册过，请核查！");
        }
        if (isUniqueUserName) {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "用户名已被注册过，请核查！");
            throw new ApiException("用户名已被注册过，请核查！");
        }
        Date birth = DateUtil.dateFormate(consumerDTO.getBirth(), "yyyy-MM-dd");
        Consumer consumer = new Consumer();
        BeanUtils.copyProperties(consumerDTO, consumer);
        consumer.setBirth(birth);
        consumer.setSex(Integer.parseInt(consumerDTO.getSex()));
        consumer.setCreateTime(new Date());
        consumer.setUpdateTime(new Date());
        boolean flag = consumerMapper.insert(consumer) > 0;
        if (flag) {
            jsonObject.put(Constants.CODE, 1);
            jsonObject.put(Constants.MSG, "添加成功");
        } else {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "添加失败");
        }
        return jsonObject;
    }

    @ApiOperation("添加用户去校验")
    @ResponseBody
    @RequestMapping(value = "/addForCli", method = RequestMethod.POST)
    public Object addForCli(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        ConsumerDTO consumerDTO = (ConsumerDTO) DTOBuilder.getDTO(request, ConsumerDTO.class);
        ValidatorUtils.validateDto(consumerDTO);
        if (StringUtils.isEmpty(consumerDTO.getUsername())) {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "用户名不能为空！");
            throw new ApiException("用户名不能为空！");
        }
        if (StringUtils.isEmpty(consumerDTO.getPassword())) {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "密码不能为空！");
            throw new ApiException("密码不能为空！");
        }
        List<Consumer> consumerList = consumerMapper.selectList(null);
        List<String> emailList = consumerList.stream().map(Consumer::getEmail).collect(Collectors.toList());
        List<String> userNameList = consumerList.stream().map(Consumer::getUsername).collect(Collectors.toList());
        boolean isUniqueEmail = emailList.contains(consumerDTO.getEmail());
        boolean isUniqueUserName = userNameList.contains(consumerDTO.getUsername());
        if (isUniqueEmail) {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "邮箱已被注册过，请核查！");
            throw new ApiException("邮箱已被注册过，请核查！");
        }
        if (isUniqueUserName) {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "用户名已被注册过，请核查！");
            throw new ApiException("用户名已被注册过，请核查！");
        }
        Date birth = DateUtil.dateFormate(consumerDTO.getBirth(), "yyyy-MM-dd");
        Consumer consumer = new Consumer();
        BeanUtils.copyProperties(consumerDTO, consumer);
        consumer.setBirth(birth);
        consumer.setSex(Integer.parseInt(consumerDTO.getSex()));
        consumer.setCreateTime(new Date());
        consumer.setUpdateTime(new Date());
        boolean flag = consumerMapper.insert(consumer) > 0;
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
    @ApiOperation("修改用户信息")
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object updateConsumer(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        ConsumerDTO consumerDTO = (ConsumerDTO) DTOBuilder.getDTO(request, ConsumerDTO.class);
        ValidatorUtils.validateDto(consumerDTO);
        LambdaQueryWrapper<Consumer> emailQueryWrapper = new LambdaQueryWrapper<>();
        emailQueryWrapper.eq(Consumer::getEmail, consumerDTO.getEmail())
                .ne(Consumer::getId, Integer.parseInt(consumerDTO.getId()));
        boolean isOnlyEmail = consumerMapper.selectCount(emailQueryWrapper) > 0;
        if (isOnlyEmail) {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "邮箱已被注册过，请核查！");
            throw new ApiException("邮箱已被注册过，请核查！");
        }
        Date birth = DateUtil.dateFormate(consumerDTO.getBirth(), "yyyy-MM-dd");
        Consumer consumer = new Consumer();
        BeanUtils.copyProperties(consumerDTO, consumer);
        consumer.setId(Integer.parseInt(consumerDTO.getId()));
        consumer.setBirth(birth);
        consumer.setSex(Integer.parseInt(consumerDTO.getSex()));
        consumer.setUpdateTime(new Date());
        boolean flag = consumerMapper.updateById(consumer) > 0;
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
    @ApiOperation("删除用户信息")
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Object deleteConsumer(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        ConsumerDTO consumerDTO = (ConsumerDTO) DTOBuilder.getDTO(request, ConsumerDTO.class);
        ValidatorUtils.validateDto(consumerDTO);
        boolean flag = consumerMapper.deleteById(Integer.parseInt(consumerDTO.getId())) > 0;
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
    @ApiOperation("根据主键查询用户信息")
    @ResponseBody
    @RequestMapping(value = "/selectByPrimaryKey", method = RequestMethod.GET)
    public Object selectByPrimaryKey(HttpServletRequest request) {
        ConsumerDTO consumerDTO = (ConsumerDTO) DTOBuilder.getDTO(request, ConsumerDTO.class);
        ValidatorUtils.validateDto(consumerDTO);
        Consumer consumerInfo = consumerMapper.selectById(Integer.parseInt(consumerDTO.getId()));
        if (consumerInfo != null) {
            return consumerInfo;
        }
        return null;
    }

    @UserLoginToken
    @ApiOperation("查询所有用户信息")
    @ResponseBody
    @RequestMapping(value = "/allConsumer", method = RequestMethod.GET)
    public Object allConsumer(HttpServletRequest request) {
        return consumerMapper.selectList(null);
    }

    @ApiOperation("更新用户头像")
    @ResponseBody
    @RequestMapping(value = "/updateConsumerPic", method = RequestMethod.POST)
    public Object updateSingerPic(@RequestParam("file") MultipartFile avatarPic, @RequestParam("id") Integer id) {
        JSONObject jsonObject = new JSONObject();
        if (avatarPic.isEmpty()) {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "更新失败");
        }
//        时间戳 + 文件名 防止上传相同名字的文件被覆盖
        String fileName = System.currentTimeMillis() + "_" + avatarPic.getOriginalFilename();
        String filePath = Constants.CONSUMER_PIC_PATH;
        File file = new File(filePath);
//        判断存放目录是否存在
        if (!file.exists()) {
//            创建目录
            file.mkdir();
        }
        File saveFile = new File(filePath + fileName);
//        存储的路径
        String storePath = "/img/avatorImages/" + fileName;
        try {
            avatarPic.transferTo(saveFile);
            Consumer consumer = new Consumer();
            consumer.setId(id);
            consumer.setAvator(storePath);
            boolean flag = consumerMapper.updateById(consumer) > 0;
            if (flag) {
                jsonObject.put(Constants.CODE, 1);
                jsonObject.put(Constants.MSG, "更新成功");
                jsonObject.put("avator", storePath);
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

    @ApiOperation("登录")
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        ConsumerDTO consumerDTO = (ConsumerDTO) DTOBuilder.getDTO(request, ConsumerDTO.class);
        ValidatorUtils.validateDto(consumerDTO);
        LambdaQueryWrapper<Consumer> consumerQueryWrapper = new LambdaQueryWrapper<>();
        consumerQueryWrapper.eq(Consumer::getPassword,consumerDTO.getPassword())
                .eq(Consumer::getUsername,consumerDTO.getUsername());
        Consumer consumer = consumerMapper.selectOne(consumerQueryWrapper);
        if (consumer != null){
            jsonObject.put(Constants.CODE, 1);
            jsonObject.put(Constants.MSG, "登录成功");
            jsonObject.put("userMsg", consumerMapper.selectById(consumer.getId()));
        } else {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "登录失败");
        }
        return jsonObject;
    }









}
