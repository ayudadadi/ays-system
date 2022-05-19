package com.marvin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.marvin.bean.TicketOrder;
import com.marvin.bean.vo.ChartQuery;
import com.marvin.bean.vo.OrderVo;
import com.marvin.common.Result;
import com.marvin.common.ResultCode;
import com.marvin.service.TicketOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-03-28
 */
@RestController
@RequestMapping("/ticket-order")
@CrossOrigin
public class TicketOrderController {

    @Autowired
    private TicketOrderService ticketOrderService;

    // 后台接口
    // 每日票房统计
    @PostMapping("getChartTotalPrice")
    public Result getChartTotalPrice(@RequestBody(required = false) ChartQuery chartQuery){
        Result result = ticketOrderService.getChartTotalPrice(chartQuery);
        return result;
    }
    // 前五票房统计分析
    @PostMapping("getChartByMoviePrice")
    public Result getChartByMoviePrice(@RequestBody(required = false) ChartQuery chartQuery){
        Result result = ticketOrderService.getChartByMoviePrice(chartQuery);
        return result;
    }

    // 核销订单，更新状态
    @PostMapping("finishOrderById")
    public Result finishOrderById(@RequestBody(required = false) TicketOrder ticketOrder){
        boolean flag = ticketOrderService.updateById(ticketOrder);
        if (flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }


    // 个人中心中的支付表单，更改信息
    @PostMapping("payOrderByUser")
    public Result payOrderByUser(@RequestBody(required = false) TicketOrder ticketOrder){
        boolean flag = ticketOrderService.updateById(ticketOrder);
        if (flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

    // 支付表单，更改信息
    @PostMapping("updateStatusByPay")
    public Result updateStatusByPay(@RequestBody(required = false) TicketOrder ticketOrder){
        UpdateWrapper<TicketOrder> wrapper = new UpdateWrapper<>();
        Integer status = ticketOrder.getStatus();
        String scheduleId = ticketOrder.getScheduleId();
        String seat = ticketOrder.getSeat();
        String userId = ticketOrder.getUserId();
        Date payTime = ticketOrder.getPayTime();
        Date updated = ticketOrder.getUpdated();
        wrapper.eq("schedule_id",scheduleId);
        wrapper.eq("user_id",userId);
        wrapper.eq("seat",seat);
        wrapper.set("status",status);
        wrapper.set("pay_time",payTime);
        wrapper.set("updated",updated);
        boolean flag = ticketOrderService.update(wrapper);
        if (flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

    // 获取订单列表
    @PostMapping("getOrderListPage/{current}/{limit}")
    public Result getOrderListPage(@PathVariable long current,
                                   @PathVariable long limit,
                                   @RequestBody OrderVo orderVo){
        Result result = ticketOrderService.getOrderListPage(current,limit,orderVo);
        return result;

    }



    @GetMapping("PageOrderList/{current}/{limit}/{userId}")
    public Result pageOrderList(@PathVariable long current,
                                @PathVariable long limit,
                                @PathVariable String userId){
        Result result = ticketOrderService.pageOrderList(current,limit,userId);
        return result;
    }


    // 前端提交表单时候，插入订单信息
    @PostMapping("submitOrder")
    public Result submitOrder(@RequestBody TicketOrder ticketOrder){
        boolean save = ticketOrderService.save(ticketOrder);
        if(save){
            return Result.ok().data("code", ResultCode.SUCCESS);
        }else{
            return Result.error().data("code", ResultCode.ERROR);
        }
    }
}

