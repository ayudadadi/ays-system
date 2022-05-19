package com.marvin.service;

import com.marvin.bean.Favorite;
import com.baomidou.mybatisplus.extension.service.IService;
import com.marvin.common.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2022-04-10
 */
public interface FavoriteService extends IService<Favorite> {

    Result getStarListByUserId(String userId);
}
