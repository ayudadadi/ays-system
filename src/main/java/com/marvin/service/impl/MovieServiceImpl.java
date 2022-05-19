package com.marvin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.marvin.bean.Movie;
import com.marvin.bean.vo.IndexMovieVo;
import com.marvin.common.Result;
import com.marvin.mapper.MovieMapper;
import com.marvin.service.MovieService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 豆瓣_电影_2021 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-12-16
 */
@Service
public class MovieServiceImpl extends ServiceImpl<MovieMapper, Movie> implements MovieService {

    // 热播电影
    @Override
    public List<IndexMovieVo> indexMovie() {
        QueryWrapper<IndexMovieVo> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("play_count");
        wrapper.last("limit 8");
        List<IndexMovieVo> indexMovieVoList = baseMapper.IndexMovieVo(wrapper);
        return indexMovieVoList;
    }

    // 猜你喜欢
    @Override
    public List<Movie> favoriteMovie() {
        QueryWrapper<Movie> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 4");
        List<Movie> movieList = baseMapper.selectList(wrapper);
        return movieList;
    }

    @Override
    public Result searchMovie(long current, long limit, String keyWord) {
        Page<Movie> moviePage = new Page<>(current, limit);
        // 构建条件
        QueryWrapper<Movie> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(keyWord)){
            // 参数：1.列,2.值
            wrapper.like("casts",keyWord).or().like("title",keyWord);
        }
        baseMapper.selectPage(moviePage,wrapper);
        long total = moviePage.getTotal(); // 总记录数
        List<Movie> records = moviePage.getRecords(); // 数据list集合
        return Result.ok().data("total",total).data("rows",records);
    }
}
