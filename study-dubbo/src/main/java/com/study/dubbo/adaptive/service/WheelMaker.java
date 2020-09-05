package com.study.dubbo.adaptive.service;

import com.alibaba.dubbo.common.URL;
import com.study.dubbo.adaptive.entity.Wheel;

public interface WheelMaker {
    /** 造轮子 */
    Wheel makeWheel(URL url);
}
