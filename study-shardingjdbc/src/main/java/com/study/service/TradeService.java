package com.study.service;

import com.study.dao.OrderMapper;
import com.study.dao.TradeMapper;
import com.study.entity.Trade;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TradeService {

    @Resource
    private TradeMapper tradeMapper;

    @Resource
    private OrderMapper orderMapper;

    public void createTrade(Trade trade){
        this.tradeMapper.save(trade);
    }

    public Trade findById(Long id){

        return tradeMapper.findById(id);
    }

    public void createOrder(Trade trade){
        orderMapper.save(trade);
    }
    public void createOrderAutoId(Trade trade){
        orderMapper.saveByAuto(trade);
    }

    public Trade findOrderByCustomerId(Long customerId){

        return this.orderMapper.findCustomerId(customerId);
    }
}
