package com.marvin.service;

import com.marvin.bean.Movie;
import com.baomidou.mybatisplus.extension.service.IService;
import com.marvin.bean.vo.IndexMovieVo;
import com.marvin.common.Result;

import java.util.List;

/**
 * <p>
 * 豆瓣_电影_2021 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-12-16
 */
public interface MovieService extends IService<Movie> {

    List<IndexMovieVo> indexMovie();

    List<Movie> favoriteMovie();

    Result searchMovie(long current, long limit, String keyWord);
}
