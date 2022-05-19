package com.marvin.service.impl;

import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.marvin.bean.MovieVideo;
import com.marvin.bean.vo.MovieVideoQuery;
import com.marvin.common.Result;
import com.marvin.handler.AysException;
import com.marvin.mapper.MovieVideoMapper;
import com.marvin.service.MovieVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.marvin.service.VodService;
import com.marvin.utils.GetVodInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 电影视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-02-14
 */
@Service
public class MovieVideoServiceImpl extends ServiceImpl<MovieVideoMapper, MovieVideo> implements MovieVideoService {

    @Autowired
    private MovieVideoService movieVideoService;

    @Autowired
    private VodService vodService;

    @Override
    public Result pageVideoCondition(long current, long limit, MovieVideoQuery movieVideoQuery) {
        Page<MovieVideo> movieVideoPage = new Page<>(current,limit);
        QueryWrapper<MovieVideo> wrapper = new QueryWrapper<>();
        // 多条件组合查询，动态sql
        String title = movieVideoQuery.getTitle();
        String begin = movieVideoQuery.getBegin();
        String end = movieVideoQuery.getEnd();

        // 判断条件值是否为空，如果不为空拼接条件
        if(!StringUtils.isEmpty(title)){
            // 参数：1.列,2.值
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("created",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("created",end);
        }
        // 降序排序
        wrapper.orderByDesc("created");

        movieVideoService.page(movieVideoPage,wrapper);
        long total = movieVideoPage.getTotal(); // 总记录数
        List<MovieVideo> records = movieVideoPage.getRecords(); // 数据list集合
        for (MovieVideo record : records) {
            if(!"Normal".equals(record.getStatus())){
                try {
                    // 调用获取视频信息的方法
                    GetVodInfo.getVodInfo(record);
                    // 把更新后的视频信息 存入数据库
                    movieVideoService.updateById(record);
                } catch (ClientException e) {
                    e.printStackTrace();
                }
            }
        }
        return Result.ok().data("total",total).data("rows",records);
    }

    @Override
    public Result deleteVideo(String id) {
        MovieVideo movieVideo = movieVideoService.getById(id);
        String videoSourceId = movieVideo.getVideoSourceId();
        //判断是否有视频资源id
        if(!StringUtils.isEmpty(videoSourceId)) {
            //根据视频id，远程调用实现视频删除
            Result result = vodService.deleteVideo(videoSourceId);
            if(result.getCode() == 20001) {
                throw new AysException(20001,"删除阿里云视频失败");
            }
        }
        boolean flag = movieVideoService.removeById(id);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }
}
