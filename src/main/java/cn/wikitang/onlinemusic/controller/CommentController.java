package cn.wikitang.onlinemusic.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.wikitang.onlinemusic.common.utils.DTOBuilder;
import cn.wikitang.onlinemusic.common.utils.ValidatorUtils;
import cn.wikitang.onlinemusic.constant.Constants;
import cn.wikitang.onlinemusic.dao.CommentMapper;
import cn.wikitang.onlinemusic.dto.CommentDTO;
import cn.wikitang.onlinemusic.dto.CommentWithCustomerDTO;
import cn.wikitang.onlinemusic.entity.Comment;
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
@Api(tags = "评论模块")
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentMapper commentMapper;

    @ApiOperation("添加评论")
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addComment(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        CommentDTO commentDTO = (CommentDTO) DTOBuilder.getDTO(request, CommentDTO.class);
        ValidatorUtils.validateDto(commentDTO);
        Comment comment = new Comment();
        comment.setUserId(Integer.parseInt(commentDTO.getUserId()));
        if (StringUtils.isNotEmpty(commentDTO.getSongId())) {
            comment.setSongId(Integer.parseInt(commentDTO.getSongId()));
        }
        if (StringUtils.isNotEmpty(commentDTO.getSongListId())) {
            comment.setSongListId(Integer.parseInt(commentDTO.getSongListId()));
        }
        comment.setContent(commentDTO.getContent());
        comment.setCreateTime(new Date());
//        0-歌曲 1-歌单
        comment.setType(Integer.parseInt(commentDTO.getType()));
        if (StringUtils.isNotEmpty(commentDTO.getUp())) {
            comment.setUp(Integer.parseInt(commentDTO.getUp()));
        }
        boolean flag = commentMapper.insert(comment) > 0;
        if (flag) {
            jsonObject.put(Constants.CODE, 1);
            jsonObject.put(Constants.MSG, "评论成功");
        } else {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "评论失败");
        }
        return jsonObject;
    }

    @ApiOperation("修改评论")
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object updateComment(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        CommentDTO commentDTO = (CommentDTO) DTOBuilder.getDTO(request, CommentDTO.class);
        ValidatorUtils.validateDto(commentDTO);
        Comment comment = new Comment();
        comment.setId(Integer.parseInt(commentDTO.getId()));
        comment.setUserId(Integer.parseInt(commentDTO.getUserId()));
        if (StringUtils.isEmpty(commentDTO.getSongId())) {
            comment.setSongId(null);
        } else {
            comment.setSongId(Integer.parseInt(commentDTO.getSongId()));
        }
        if (StringUtils.isEmpty(commentDTO.getSongListId())) {
            comment.setSongListId(null);
        } else {
            comment.setSongListId(Integer.parseInt(commentDTO.getSongListId()));
        }
        comment.setContent(commentDTO.getContent());
        comment.setCreateTime(new Date());
//        0-歌曲 1-歌单
        comment.setType(Integer.parseInt(commentDTO.getType()));
        comment.setUp(Integer.parseInt(commentDTO.getUp()));
        boolean flag = commentMapper.updateById(comment) > 0;
        if (flag) {
            jsonObject.put(Constants.CODE, 1);
            jsonObject.put(Constants.MSG, "修改成功");
        } else {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "修改失败");
        }
        return jsonObject;
    }

    @ApiOperation("删除评论")
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object deleteComment(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        CommentDTO commentDTO = (CommentDTO) DTOBuilder.getDTO(request, CommentDTO.class);
        ValidatorUtils.validateDto(commentDTO);
        Integer id = Integer.parseInt(commentDTO.getId());
        boolean flag = commentMapper.deleteById(id) > 0;
        if (flag) {
            jsonObject.put(Constants.CODE, 1);
            jsonObject.put(Constants.MSG, "删除成功");
        } else {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "删除失败");
        }
        return jsonObject;
    }

    @ApiOperation("根据主键查询评论")
    @ResponseBody
    @RequestMapping(value = "/selectByPrimaryKey", method = RequestMethod.GET)
    public Object selectByPrimaryKey(HttpServletRequest request) {
        CommentDTO commentDTO = (CommentDTO) DTOBuilder.getDTO(request, CommentDTO.class);
        ValidatorUtils.validateDto(commentDTO);
        Integer id = Integer.parseInt(commentDTO.getId());
        Comment commentInfo = commentMapper.selectById(id);
        if (commentInfo != null) {
            return commentInfo;
        }
        return null;
    }

    @ApiOperation("查询某个歌曲下的所有评论")
    @ResponseBody
    @RequestMapping(value = "/commentOfSongId", method = RequestMethod.GET)
    public Object commentOfSongId(HttpServletRequest request) {
        CommentDTO commentDTO = (CommentDTO) DTOBuilder.getDTO(request, CommentDTO.class);
        ValidatorUtils.validateDto(commentDTO);
        Integer songId = Integer.parseInt(commentDTO.getSongId());
        List<CommentWithCustomerDTO> commentList = commentMapper.getCommentWithUserInfo(songId);
        if (CollectionUtil.isNotEmpty(commentList)){
            return commentList;
        }
        return null;
    }

    @ApiOperation("查询某个歌单下的所有评论")
    @ResponseBody
    @RequestMapping(value = "/commentOfSongListId", method = RequestMethod.GET)
    public Object commentOfSongListId(HttpServletRequest request) {
        CommentDTO commentDTO = (CommentDTO) DTOBuilder.getDTO(request, CommentDTO.class);
        ValidatorUtils.validateDto(commentDTO);
        Integer songListId = Integer.parseInt(commentDTO.getSongListId());
        LambdaQueryWrapper<Comment> commentQueryWrapper = new LambdaQueryWrapper<>();
        commentQueryWrapper.eq(Comment::getSongListId, songListId);
        List<Comment> commentList = commentMapper.selectList(commentQueryWrapper);
        if (CollectionUtil.isNotEmpty(commentList)){
            return commentList;
        }
        return null;
    }

    @ApiOperation("点赞功能")
    @ResponseBody
    @RequestMapping(value = "/like", method = RequestMethod.POST)
    public Object like(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        CommentDTO commentDTO = (CommentDTO) DTOBuilder.getDTO(request, CommentDTO.class);
        ValidatorUtils.validateDto(commentDTO);
        Integer id = Integer.parseInt(commentDTO.getId());
        Integer up = Integer.parseInt(commentDTO.getUp());
        Comment comment = new Comment();
        comment.setId(id);
        comment.setUp(up);
        boolean flag = commentMapper.updateById(comment) > 0;
        if (flag) {
            jsonObject.put(Constants.CODE, 1);
            jsonObject.put(Constants.MSG, "点赞成功");
        } else {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "点赞失败");
        }
        return jsonObject;
    }

}
