package com.marvin.service;

import com.marvin.bean.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.marvin.common.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2022-03-19
 */
public interface CommentService extends IService<Comment> {

    Result addComment(Comment comment);

    Result getCommentListById(long current,long limit,String movieId);
}
