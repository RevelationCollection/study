package com.study;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.mybatis.plus.MybatisApplication;
import com.study.mybatis.plus.entity.User;
import com.study.mybatis.plus.entity.UserTest;
import com.study.mybatis.plus.mapper.UserMapper;
import com.study.mybatis.plus.mapper.UserTestMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = MybatisApplication.class)
public class TestMain {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserTestMapper testUserMapper;


    @Test
    void testSelectList() {
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    @Test
    public void testInsert() {
        User user = new User();
        user.setName("test_study_mybatisPlus");
        user.setAge(18);
        user.setEmail("123456@163.com");
        int result = userMapper.insert(user);
        System.out.println("影响的行数：" + result); //影响的行数
        System.out.println("id：" + user); //id自动回填
        //自动填充创建时间，mybatisPlus ->MetaObjectHandler  @TableField(fill = FieldFill.INSERT)
    }

    @Test
    public void testInsertUpperCase() {
        //驼峰转下划线
        UserTest userTest = new UserTest();
        userTest.setAge(18);
        userTest.setFullName("test___name");
        userTest.setEmail("45685@145@com");
        int result = testUserMapper.insert(userTest);
        System.out.println("影响的行数：" + result);
        System.out.println("id：" + userTest);
    }

    @Test
    public void testInsertAutoFill() {
        //自动填充更新时间，mybatisPlus ->MetaObjectHandler  @TableField(fill = FieldFill.INSERT_UPDATE)
        User user = new User();
        user.setAge(14);
        user.setId(1L);
        userMapper.updateById(user);
    }

    @Test
    public void testSelectBatchIds() {
        //多个id批量查询
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
        System.out.println(users);
    }

    @Test
    public void testSelectByMap() {
        //map条件查询
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "test_study_mybatisPlus");
        map.put("age", 18);
        List<User> users = userMapper.selectByMap(map);
        System.out.println(users);
    }

    @Test
    public void testSelectPage() {
        //分页查询
        Page<User> page = new Page<>(1, 5);
        Page<User> pageParam = userMapper.selectPage(page, null);
        pageParam.getRecords().forEach(System.out::println);
        System.out.println(pageParam.getCurrent());
        System.out.println(pageParam.getPages());
        System.out.println(pageParam.getSize());
        System.out.println(pageParam.getTotal());
        System.out.println(pageParam.hasNext());
        System.out.println(pageParam.hasPrevious());
    }

    @Test
    public void testSelectMapsPage() {
        //Page不需要泛型  当指定了特定的查询列时，希望分页结果列表只返回被查询的列，而不是很多null值
        Page<Map<String, Object>> page = new Page<>(1, 5);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("name", "age");
        Page<Map<String, Object>> pageParam = userMapper.selectMapsPage(page, queryWrapper);
        List<Map<String, Object>> records = pageParam.getRecords();
        records.forEach(System.out::println);
        System.out.println(pageParam.getCurrent());
        System.out.println(pageParam.getPages());
        System.out.println(pageParam.getSize());
        System.out.println(pageParam.getTotal());
        System.out.println(pageParam.hasNext());
        System.out.println(pageParam.hasPrevious());
    }


    @Test
    public void testLogicDelete() {
        //逻辑删除，删除后，无法查询出
        int result = userMapper.deleteById(1L);
        System.out.println(result);
    }

    @Test
    public void testSelectOne() {
        //eq、ne
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", "Tom");
        User user = userMapper.selectOne(queryWrapper);//只能返回一条记录，多余一条则抛出异常
        System.out.println(user);
    }

    @Test
    public void testDelete() {
        //ge、gt、le、lt、isNull、isNotNull
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .isNull("name")
                .ge("age", 12)
                .isNotNull("email");
        int result = userMapper.delete(queryWrapper);
        System.out.println("delete return count = " + result);
    }
}