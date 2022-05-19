package com.marvin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.marvin.bean.Rank;
import com.marvin.mapper.RankMapper;
import com.marvin.service.RankService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-12-21
 */
@Service
public class RankServiceImpl extends ServiceImpl<RankMapper, Rank> implements RankService {

    @Override
    public List<Rank> indexRankList() {
        QueryWrapper<Rank> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("rate");
        wrapper.last("limit 10");
        List<Rank> rankList = baseMapper.selectList(wrapper);
        return rankList;
    }
}
