package com.marvin.controller;


import com.marvin.bean.MovieFriend;
import com.marvin.bean.vo.FriendQuery;
import com.marvin.bean.vo.FriendVo;
import com.marvin.bean.vo.UserQuery;
import com.marvin.common.Result;
import com.marvin.service.MovieFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-04-13
 */
@RestController
@RequestMapping("/movie-friend")
@CrossOrigin
public class MovieFriendController {

    @Autowired
    private MovieFriendService movieFriendService;

    // 后台获取匹配列表
    @PostMapping("getMatchListPage/{current}/{limit}")
    public Result getMatchListPage(@PathVariable long current,
                                   @PathVariable long limit,
                                   @RequestBody FriendQuery friendQuery){
        Result result = movieFriendService.getMatchListPage(current,limit,friendQuery);
        return result;
    }


    // 个人中心》寻觅影友列表显示
    @GetMapping("getMatchListById/{userId}")
    public Result getMatchListById(@PathVariable String userId){
        List<FriendVo> list = movieFriendService.getMatchListById(userId);
        return Result.ok().data("list",list);
    }

    // 接受邀请，修改寻觅影友状态方法
    @PostMapping("updateMatchStatus")
    public Result updateMatchStatus(@RequestBody MovieFriend movieFriend){
        boolean flag = movieFriendService.updateById(movieFriend);
        if (flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }


    @PostMapping("insertMatch")
    public Result insertMatch(@RequestBody MovieFriend movieFriend){
        boolean save = movieFriendService.save(movieFriend);
        if (save){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

    @PostMapping("getMatchPage/{current}/{limit}")
    public Result getMatchPage(@PathVariable long current,
                               @PathVariable long limit,
                               @RequestBody(required = false) UserQuery userQuery){
        Result result= movieFriendService.getMatchPage(current,limit,userQuery);
        return result;
    }
}

