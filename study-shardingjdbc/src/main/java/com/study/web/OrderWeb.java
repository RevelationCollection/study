package com.study.web;

import com.study.entity.Trade;
import com.study.service.TradeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("order")
public class OrderWeb {

    @Resource
    private TradeService tradeService;

    @RequestMapping("save")
    public Trade saveOrder(Trade trade){
        tradeService.createOrder(trade);
        return trade;
    }
    @RequestMapping("saveAutoId")
    public Trade saveOrderAutoId(Trade trade){
        tradeService.createOrderAutoId(trade);
        return trade;
    }

    @RequestMapping("findByCustomerId")
    public Trade findByCustomerId(Long customerId){

        return tradeService.findOrderByCustomerId(customerId);
    }

}
