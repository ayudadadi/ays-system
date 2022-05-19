package com.marvin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.marvin.bean.Comment;
import com.marvin.bean.vo.CommentVo;
import com.marvin.common.Result;
import com.marvin.mapper.CommentMapper;
import com.marvin.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-03-19
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    // 添加评论
    @Override
    public Result addComment(Comment comment) {
        String userId = comment.getUserId();
        String movieId = comment.getMovieId();
        String content = comment.getContent();
        // 判空
        // TODO 后期可加状态码，前端拦截未登录。
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(movieId) || StringUtils.isEmpty(content)) {
            return Result.error();
        }
        int flag = baseMapper.insert(comment);
        if (flag == 1) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    // 根据影片id查评论列表，带分页
    @Override
    public Result getCommentListById(long current, long limit, String movieId) {
        Page<CommentVo> commentPage = new Page<>(current, limit);
        QueryWrapper<CommentVo> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(movieId)){
            wrapper.eq("c.movie_id",movieId);
        }else{
            return Result.error();
        }
        wrapper.orderByDesc("c.created");
        baseMapper.getCommentListById(commentPage,wrapper);
        long total = commentPage.getTotal(); // 总记录数
        List<CommentVo> records = commentPage.getRecords(); // 数据list集合
        return Result.ok().data("total",total).data("rows",records);
    }



}
