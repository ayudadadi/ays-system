package com.marvin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.marvin.bean.Favorite;
import com.marvin.bean.vo.FavoriteVo;
import com.marvin.common.Result;
import com.marvin.mapper.FavoriteMapper;
import com.marvin.service.FavoriteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-04-10
 */
@Service
public class FavoriteServiceImpl extends ServiceImpl<FavoriteMapper, Favorite> implements FavoriteService {

    @Override
    public Result getStarListByUserId(String userId) {
        QueryWrapper<FavoriteVo> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(userId)){
            wrapper.eq("user_id",userId);
        }
        wrapper.orderByDesc("f.created");
        wrapper.eq("f.deleted",0);
        List<FavoriteVo> list = baseMapper.getStarListByUserId(wrapper);
        return Result.ok().data("list",list);
    }
}
