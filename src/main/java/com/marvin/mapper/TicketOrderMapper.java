package com.marvin.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.marvin.bean.TicketOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.marvin.bean.vo.ChartQuery;
import com.marvin.bean.vo.OrderVo;
import com.marvin.bean.vo.ScheduleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2022-03-28
 */
public interface TicketOrderMapper extends BaseMapper<TicketOrder> {
    IPage<OrderVo> pageOrderVo(IPage<OrderVo> page,@Param(Constants.WRAPPER) Wrapper<OrderVo> wrapper);
    IPage<OrderVo> getOrderListPage(IPage<OrderVo> page,@Param(Constants.WRAPPER) Wrapper<OrderVo> wrapper);
    List<ChartQuery> getChartByMoviePrice(@Param(Constants.WRAPPER) Wrapper<ChartQuery> wrapper);
    List<ChartQuery> getChartTotalPrice(@Param(Constants.WRAPPER) Wrapper<ChartQuery> wrapper);
}
