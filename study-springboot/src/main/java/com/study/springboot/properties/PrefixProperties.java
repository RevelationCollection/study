package com.study.springboot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties(prefix = "custom.apple")
@Component
public class PrefixProperties {
    private String name;

    private Integer weight;

    private List<String> goods;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public List<String> getGoods() {
        return goods;
    }

    public void setGoods(List<String> goods) {
        this.goods = goods;
    }

    @Override
    public String toString() {
        return "PrefixProperties{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                ", goods=" + goods +
                '}';
    }
}
