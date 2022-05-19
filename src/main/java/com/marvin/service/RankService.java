package com.marvin.service;

import com.marvin.bean.Rank;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-12-21
 */
public interface RankService extends IService<Rank> {

    List<Rank> indexRankList();
}
