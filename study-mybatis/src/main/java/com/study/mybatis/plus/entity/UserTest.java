package com.study.mybatis.plus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class UserTest {
    @TableId(type = IdType.ASSIGN_ID)  //ASSIGN_ID （使用了雪花算法） 默认
    private Long customerId;
    private String fullName;
    private Integer age;
    private String email;
}