package cn.wikitang.onlinemusic.controller;

import cn.wikitang.onlinemusic.dao.AdminMapper;
import cn.wikitang.onlinemusic.entity.Admin;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author twj
 * @since 2021-12-08
 */
@RestController
@Controller
public class AdminController {
    @Autowired
    private AdminMapper adminMapper;


    @ApiOperation("登录操作")
    @ResponseBody
    @RequestMapping(value = "/admin/login/status", method = RequestMethod.POST)
    public Object loginStatus(HttpServletRequest req, HttpSession session) {
        JSONObject jsonObject = new JSONObject();

        String name = req.getParameter("name");
        String password = req.getParameter("password");

        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getName, name)
                .eq(Admin::getPassword, password);
        Integer count = adminMapper.selectCount(queryWrapper);
        boolean res = count > 0 ? true : false;
        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "登录成功");
            session.setAttribute("name", name);
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "用户名或密码错误");
        }
        return jsonObject;

    }
}

