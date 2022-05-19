package com.marvin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.marvin.bean.Movie;
import com.marvin.bean.MovieVideo;
import com.marvin.bean.vo.IndexMovieVo;
import com.marvin.bean.vo.MovieQuery;
import com.marvin.bean.vo.MovieVideoQuery;
import com.marvin.common.Result;
import com.marvin.handler.AysException;
import com.marvin.service.MovieService;
import com.marvin.service.MovieVideoService;
import com.marvin.service.VodService;
import io.swagger.annotations.Api;
import org.apache.velocity.runtime.directive.Foreach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 豆瓣_电影 前端控制器
 * </p>
 *
 * @author marvin
 * @since 2021-12-16
 */
@Api("电影管理")
@RestController
@RequestMapping("/movie")
@CrossOrigin
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieVideoService movieVideoService;

    // 首页搜索功能，影名或影人条件查询分页
    @GetMapping("searchMovie/{current}/{limit}/{keyWord}")
    public Result searchMovie(@PathVariable long current,
                              @PathVariable long limit,
                              @PathVariable String keyWord){
        Result result = movieService.searchMovie(current,limit,keyWord);
        return result;
    }

    // 选电影分类页面，多条件查询分页，自定义排序字段
    @PostMapping("getMoviesPage/{current}/{limit}")
    public Result getMoviesPage(@PathVariable long current,
                                @PathVariable long limit,
                                @RequestBody(required = false) MovieQuery movieQuery){
        Page<Movie> moviePage = new Page<>(current, limit);
        // 构建条件
        QueryWrapper<Movie> wrapper = new QueryWrapper<>();
        // 多条件组合查询，动态sql

        String type = movieQuery.getType();
        String country = movieQuery.getCountry();
        String releaseDate = movieQuery.getReleaseDate();
        String orderRule = movieQuery.getOrderRule();

        // 判断条件值是否为空，如果不为空拼接条件
        if(!StringUtils.isEmpty(country)){
            // 参数：1.列,2.值
            wrapper.like("country",country);
        }
        if(!StringUtils.isEmpty(type)){
            wrapper.like("type",type);
        }
        if(!StringUtils.isEmpty(releaseDate)){
            if("2020".equals(releaseDate)){
                // ge >=  le <= 表示查询2010~2020区间的日期
                wrapper.ge("release_date","2010-01-01");
                wrapper.le("release_date","2020-12-31");
            }
            if("2010".equals(releaseDate)){
                wrapper.ge("release_date","2000-01-01");
                wrapper.le("release_date","2009-12-31");
            }
            if("2021".equals(releaseDate)){
                wrapper.like("release_date",releaseDate);
            }
        }
        if(!StringUtils.isEmpty(orderRule)){
            wrapper.orderByDesc(orderRule);
        }
        movieService.page(moviePage,wrapper);
        long total = moviePage.getTotal(); // 总记录数
        List<Movie> records = moviePage.getRecords(); // 数据list集合
        return Result.ok().data("total",total).data("rows",records);
    }

    // 猜你喜欢
    @GetMapping("favoriteMovie")
    public Result favoriteMovie(){
        List<Movie> favoriteMovieList = movieService.favoriteMovie();
        return Result.ok().data("favoriteItems",favoriteMovieList);
    }

    // 播放次数前8的电影信息
    @GetMapping("indexMovie")
    public Result indexMovie(){
        List<IndexMovieVo> indexMovieVoList = movieService.indexMovie();
        return Result.ok().data("playItems",indexMovieVoList);
    }


    // 查询电影信息所有记录
    @GetMapping("findAll")
    public Result findAll(){
        List<Movie> list = movieService.list(null);
        return Result.ok().data("items",list);
    }

    // 根据id删除电影信息
    @DeleteMapping("{id}")
    public Result removeMovie(@PathVariable String id){
        // 删除视频表的记录，同时删除阿里云视频
        // 根据电影id查询视频表的视频id
        QueryWrapper<MovieVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("movie_id",id);

        if(null != movieVideoService.getOne(wrapper)){
            MovieVideo movieVideo = movieVideoService.getOne(wrapper);
            movieVideoService.deleteVideo(movieVideo.getId());
        }


        boolean flag = movieService.removeById(id);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

    /**
     *  分页查询电影
     * @param current 当前页
     * @param limit 每页显示的记录数
     * @return
     */
    @GetMapping("pageMovie/{current}/{limit}")
    public Result pageListMovie(@PathVariable long current,
                                @PathVariable long limit){
        // 创建page分页对象(当前页，每页显示的记录数)
        Page<Movie> moviePage = new Page<>(current,limit);
        // 调用方法实现分页，把分页所有封装到moviePage对象里面
        movieService.page(moviePage,null);

        long total = moviePage.getTotal(); // 总记录数
        List<Movie> records = moviePage.getRecords(); // 数据list集合

        // 另一种返回数据的方式
//        Map map = new HashMap();
//        map.put("total",total);
//        map.put("records",records);
//        return Result.ok().data(map);

        return Result.ok().data("total",total).data("rows",records);
    }

    /**
     * 条件查询电影信息带分页的方法
     * @param current 当前页
     * @param limit 每页显示的记录数
     * @param movieQuery 条件查询封装对象
     * @return
     */
    // 使用@RequestBody(required = false) 使用json传输数据，false：允许为空，要用post方法
    @PostMapping("pageMovieCondition/{current}/{limit}")
    public Result pageMovieCondition(@PathVariable long current,
                                     @PathVariable long limit,
                                     @RequestBody(required = false) MovieQuery movieQuery){
        Page<Movie> moviePage = new Page<>(current, limit);
        // 构建条件
        QueryWrapper<Movie> wrapper = new QueryWrapper<>();
        // 多条件组合查询，动态sql
        String title = movieQuery.getTitle();
        String type = movieQuery.getType();
        String casts = movieQuery.getCasts();
        String begin = movieQuery.getBegin();
        String end = movieQuery.getEnd();

        // 判断条件值是否为空，如果不为空拼接条件
        if(!StringUtils.isEmpty(title)){
            // 参数：1.列,2.值
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(type)){
            wrapper.like("type",type);
        }
        if(!StringUtils.isEmpty(casts)){
            wrapper.like("casts",casts);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("created",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("created",end);
        }

        // 降序排序
        wrapper.orderByDesc("created");

        movieService.page(moviePage,wrapper);
        long total = moviePage.getTotal(); // 总记录数
        List<Movie> records = moviePage.getRecords(); // 数据list集合
        return Result.ok().data("total",total).data("rows",records);
    }

    /**
     * 添加电影信息
     * @param movie
     * @return
     */
    @PostMapping("addMovie")
    public Result addMovie(@RequestBody Movie movie){
        boolean save = movieService.save(movie);
        if(save){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

    // 根据影片id进行查询
    @GetMapping("getMovie/{id}")
    public Result getMovie(@PathVariable String id){
        Movie movie = movieService.getById(id);
        return Result.ok().data("movie",movie);
    }

    // 影片修改方法
    @PostMapping("updateMovie")
    public Result updateMovie(@RequestBody Movie movie){
        boolean flag = movieService.updateById(movie);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }
}

