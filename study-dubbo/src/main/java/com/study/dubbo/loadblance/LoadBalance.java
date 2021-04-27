package com.study.dubbo.loadblance;

import java.util.List;

public interface LoadBalance {

    /** 根据地区选择可用通道（国际、中国） */
    SmsChannel select(String location, List<SmsChannel> smsChannelList);

}
