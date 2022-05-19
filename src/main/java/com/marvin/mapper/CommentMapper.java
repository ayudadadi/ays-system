package com.marvin.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.marvin.bean.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.marvin.bean.vo.CommentVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2022-03-19
 */
public interface CommentMapper extends BaseMapper<Comment> {
   IPage<CommentVo> getCommentListById(IPage<CommentVo> page,@Param(Constants.WRAPPER) Wrapper<CommentVo> wrapper);
}
