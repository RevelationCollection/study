package com.study.web;

import com.study.entity.Trade;
import com.study.service.TradeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TradeWeb {

    @Resource
    private TradeService tradeService;

    @RequestMapping("saveTrade")
    public Trade save(Trade trade){
        tradeService.createTrade(trade);
        return trade;
    }

    @RequestMapping("get")
    public Trade getTrade(Long id){
        return tradeService.findById(id);
    }

}
