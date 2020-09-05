package com.study.dubbo.adaptive.service.impl;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.study.dubbo.adaptive.entity.Wheel;
import com.study.dubbo.adaptive.service.WheelMaker;

public class AdaptiveWheelMarker implements WheelMaker {
    @Override
    public Wheel makeWheel(URL url) {
        if (url==null){
            throw new IllegalArgumentException("url == null");
        }
        //1、从url中获取WheelMarker名称
        String wheelMarkerName = url.getParameter("Wheel.maker");
        if (wheelMarkerName==null){
            throw new IllegalArgumentException("wheelMarkerName == null");
        }
        //2、通过SPI加载具体的WheelMaker
        WheelMaker wheelMaker = ExtensionLoader.getExtensionLoader(WheelMaker.class).getExtension(wheelMarkerName);
        return wheelMaker.makeWheel(url);
    }
}
