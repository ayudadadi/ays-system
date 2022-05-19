package com.marvin.service;

import com.marvin.bean.MovieVideo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.marvin.bean.vo.MovieQuery;
import com.marvin.bean.vo.MovieVideoQuery;
import com.marvin.common.Result;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 * 电影视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-02-14
 */
public interface MovieVideoService extends IService<MovieVideo> {

    Result pageVideoCondition(long current,
                              long limit,
                              MovieVideoQuery movieVideoQuery);

    Result deleteVideo(String id);

}
