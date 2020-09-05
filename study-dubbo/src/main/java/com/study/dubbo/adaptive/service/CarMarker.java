package com.study.dubbo.adaptive.service;

import com.alibaba.dubbo.common.URL;
import com.study.dubbo.adaptive.entity.Car;

public interface CarMarker {
    Car makeCar(URL url);
}
