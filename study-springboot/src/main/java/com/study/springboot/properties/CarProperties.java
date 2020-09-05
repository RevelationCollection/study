package com.study.springboot.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@PropertySource(value = {"car.properties"})
@Component
public class CarProperties {
    @Value("${car.name}")
    public String name;

    @Value("${age}")
    public Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "CarProperties{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
