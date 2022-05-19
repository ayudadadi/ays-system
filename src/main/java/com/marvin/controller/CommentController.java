package com.marvin.controller;


import com.marvin.bean.Comment;
import com.marvin.common.Result;
import com.marvin.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-03-19
 */
@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentService commentService;

    // 添加评论
    @PostMapping("addComment")
    public Result addComment(@RequestBody Comment comment){
        Result result = commentService.addComment(comment);
        return result;
    }

    // 根据影片id查评论列表，带分页
    @GetMapping("getCommentListById/{current}/{limit}/{movieId}")
    public Result getCommentListById(@PathVariable long current,
                                     @PathVariable long limit,
                                     @PathVariable String movieId){
        Result result = commentService.getCommentListById(current,limit,movieId);
        return result;
    }
}

