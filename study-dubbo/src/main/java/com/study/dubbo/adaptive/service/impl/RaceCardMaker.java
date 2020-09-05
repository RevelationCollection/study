package com.study.dubbo.adaptive.service.impl;

import com.alibaba.dubbo.common.URL;
import com.study.dubbo.adaptive.entity.Car;
import com.study.dubbo.adaptive.entity.Wheel;
import com.study.dubbo.adaptive.service.CarMarker;
import com.study.dubbo.adaptive.service.WheelMaker;

public class RaceCardMaker implements CarMarker {

    WheelMaker wheelMaker;

    // 通过 setter 注入 AdaptiveWheelMaker
    public void setWheelMaker(WheelMaker wheelMaker) {
        this.wheelMaker = wheelMaker;
    }

    @Override
    public Car makeCar(URL url) {
        Wheel wheel = wheelMaker.makeWheel(url);
//        return new Car(wheel, ...);
        return null;
    }

}
