package com.study.dao;

import com.study.entity.Trade;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper {

    void save(Trade t);

    void saveByAuto(Trade t);


    Trade findCustomerId(@Param("customerId")Long customerId);
}
