package com.marvin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.marvin.bean.TicketOrder;
import com.marvin.bean.vo.ChartQuery;
import com.marvin.bean.vo.OrderVo;
import com.marvin.common.Result;
import com.marvin.mapper.TicketOrderMapper;
import com.marvin.service.TicketOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-03-28
 */
@Service
public class TicketOrderServiceImpl extends ServiceImpl<TicketOrderMapper, TicketOrder> implements TicketOrderService {

    @Override
    public Result pageOrderList(long current, long limit, String userId) {
        Page<OrderVo> orderVoPage = new Page<>(current, limit);
        QueryWrapper<OrderVo> wrapper = new QueryWrapper<>();

        if(!StringUtils.isEmpty(userId)){
            wrapper.eq("o.user_id",userId);
        }
        wrapper.orderByDesc("o.created");

        IPage<OrderVo> page = baseMapper.pageOrderVo(orderVoPage, wrapper);
        long total = page.getTotal();
        List<OrderVo> records = page.getRecords();
        return Result.ok().data("total",total).data("rows",records);
    }

    // 后台 获取订单列表
    @Override
    public Result getOrderListPage(long current, long limit, OrderVo orderVo) {
        Page<OrderVo> orderVoPage = new Page<>(current, limit);
        QueryWrapper<OrderVo> wrapper = new QueryWrapper<>();
        String nickName = orderVo.getNickName();
        String id = orderVo.getId();
        Integer status = orderVo.getStatus();
        String begin = orderVo.getBegin();
        String end = orderVo.getEnd();
        if(!StringUtils.isEmpty(nickName)){
            // 参数：1.列,2.值
            wrapper.like("u.nick_name",nickName);
        }
        if(!StringUtils.isEmpty(id)){
            // 参数：1.列,2.值
            wrapper.eq("o.id",id);
        }
        if(!StringUtils.isEmpty(String.valueOf(status))){
            wrapper.eq("o.status",status);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("o.created",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("o.created",end);
        }
        wrapper.orderByDesc("o.created");

        baseMapper.getOrderListPage(orderVoPage, wrapper);
        long total = orderVoPage.getTotal();
        List<OrderVo> records = orderVoPage.getRecords();
        return Result.ok().data("total",total).data("rows",records);
    }

    // 前五电影票房统计
    @Override
    public Result getChartByMoviePrice(ChartQuery chartQuery) {
        QueryWrapper<ChartQuery> wrapper = new QueryWrapper<>();
        String begin = chartQuery.getBegin();
        String end = chartQuery.getEnd();
        wrapper.between("s.schedule_start_time",begin,end);
        wrapper.groupBy("m.title");
        wrapper.orderByDesc("price");
        // 状态已支付或已完成
        wrapper.eq("o.status",1).or().eq("o.status",2);
        wrapper.last("limit 5");

        List<ChartQuery> list = baseMapper.getChartByMoviePrice(wrapper);

        ArrayList<String> movieTitleList = new ArrayList<>();
        ArrayList<Integer> priceList = new ArrayList<>();


        // 前端Echarts 数组[] 对应 java list集合，遍历集合，重新添加
        for (int i = 0; i < list.size(); i++) {
            ChartQuery chartQuery1 = list.get(i);

            movieTitleList.add(chartQuery1.getTitle());
            priceList.add(chartQuery1.getPrice());
        }

        //  返回map
        HashMap<String, Object> map = new HashMap<>();
        map.put("movieTitleList",movieTitleList);
        map.put("priceList",priceList);

        return Result.ok().data(map);
    }

    @Override
    public Result getChartTotalPrice(ChartQuery chartQuery) {
        QueryWrapper<ChartQuery> wrapper = new QueryWrapper<>();
        String begin = chartQuery.getBegin();
        String end = chartQuery.getEnd();
        wrapper.between("s.schedule_start_time",begin,end);
        // 状态已支付或已完成
        wrapper.eq("o.status",1).or().eq("o.status",2);
        wrapper.last("group by DATE(s.schedule_start_time)");

        List<ChartQuery> list = baseMapper.getChartTotalPrice(wrapper);

        ArrayList<String> timeList = new ArrayList<>();
        ArrayList<Integer> priceList = new ArrayList<>();


        // 前端Echarts 数组[] 对应 java list集合，遍历集合，重新添加
        for (int i = 0; i < list.size(); i++) {
            ChartQuery chartQuery1 = list.get(i);

            timeList.add(chartQuery1.getTime());
            priceList.add(chartQuery1.getPrice());
        }

        //  返回map
        HashMap<String, Object> map = new HashMap<>();
        map.put("timeList",timeList);
        map.put("priceList",priceList);

        return Result.ok().data(map);
    }
}
