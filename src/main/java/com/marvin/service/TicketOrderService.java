package com.marvin.service;

import com.marvin.bean.TicketOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.marvin.bean.vo.ChartQuery;
import com.marvin.bean.vo.OrderVo;
import com.marvin.common.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2022-03-28
 */
public interface TicketOrderService extends IService<TicketOrder> {

    Result pageOrderList(long current, long limit, String userId);

    Result getOrderListPage(long current, long limit, OrderVo orderVo);

    Result getChartByMoviePrice(ChartQuery chartQuery);

    Result getChartTotalPrice(ChartQuery chartQuery);
}
