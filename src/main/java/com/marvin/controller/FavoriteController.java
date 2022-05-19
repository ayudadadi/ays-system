package com.marvin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.marvin.bean.Favorite;
import com.marvin.common.Result;
import com.marvin.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-04-10
 */
@RestController
@RequestMapping("/favorite")
@CrossOrigin
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    // 查询喜爱列表
    @GetMapping("getStarListByUserId/{userId}")
    public Result getStarListByUserId(@PathVariable String userId){
        Result result = favoriteService.getStarListByUserId(userId);
        return result;
    }

    // 查询记录
    @PostMapping("selectStar")
    public Result selectStar(@RequestBody Favorite favorite){
        QueryWrapper<Favorite> wrapper = new QueryWrapper<>();
        String userId = favorite.getUserId();
        String movieId = favorite.getMovieId();
        if(!StringUtils.isEmpty(userId)){
            wrapper.eq("user_id",userId);
        }
        if(!StringUtils.isEmpty(movieId)){
            wrapper.eq("movie_id",movieId);
        }
        int num = favoriteService.count(wrapper);
        if(num >=1){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

    // 插入一条记录
    @PostMapping("starMovie")
    public Result starMovie(@RequestBody Favorite favorite){
        boolean flag = favoriteService.save(favorite);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

    @PostMapping("removeStar")
    public Result removeStar(@RequestBody Favorite favorite){
        QueryWrapper<Favorite> wrapper = new QueryWrapper<>();
        String userId = favorite.getUserId();
        String movieId = favorite.getMovieId();
        if(!StringUtils.isEmpty(userId)){
            wrapper.eq("user_id",userId);
        }
        if(!StringUtils.isEmpty(movieId)){
            wrapper.eq("movie_id",movieId);
        }
        boolean flag = favoriteService.remove(wrapper);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

}

