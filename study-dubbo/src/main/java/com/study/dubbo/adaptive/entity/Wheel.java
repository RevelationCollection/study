package com.study.dubbo.adaptive.entity;

public class Wheel {

    private String name;
    private Double weight;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Wheel{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                '}';
    }
}
