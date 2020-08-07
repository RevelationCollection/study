package com.study.dao;

import com.study.entity.Trade;
import org.apache.ibatis.annotations.Param;

public interface TradeMapper {

    void save(Trade t);


    Trade findById(@Param("id")Long orderId);
}
