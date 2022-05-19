package com.marvin.ayssystem;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.marvin.bean.Hello;
import com.marvin.mapper.HelloMapper;
import com.marvin.service.HelloService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Marvin
 * @Description com.marvin.ayssystem
 * @create 2021-12-14 1:05
 */

@SpringBootTest
public class TestHello {

    @Autowired
    HelloMapper helloMapper;

    @Autowired
    HelloService helloService;

    @Test
    void testSaveBatch(){
        ArrayList<Hello> list = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Hello hello = new Hello();
            hello.setName("jack"+i);
            hello.setPassword("123456");
            list.add(hello);
        }
        boolean flag = helloService.saveBatch(list);
        if(flag == true){
            System.out.println("插入成功");
        }else{
            System.out.println("插入失败");
        }
    }

    /**
     * 测试乐观锁
     */
    @Test
    void testOptimisticLocker(){
        /*
        乐观锁实现步骤：
        1、取出记录时，获取当前 version（先根据id查询）
        2、更新时，带上这个 version（后更新）
        */
        Hello hello = helloMapper.selectById(1471088447157542914L);

        hello.setPassword("456123");

        helloMapper.updateById(hello);
    }

    // 多个id批量查询
    @Test
    void testSelectBatch(){
        List<Hello> list = helloMapper.selectBatchIds(Arrays.asList(1, 2, 3));
//        System.out.println(list);
        list.forEach(System.out::println);

    }

    //分页查询
    @Test
    void testPage(){
        // 步骤：
        // 1、创建page对象
        // 传入两个参数： 当前页 和 每页显示记录数
        Page<Hello> page = new Page<>(2,3);
        // 2、调用mp分页查询的方法
        // 调用mp分页查询过程中，底层封装
        // 把分页所有数据封装到page对象里面
        helloMapper.selectPage(page, null);

        // 通过page对象获取分页数据
        System.out.println(page.getCurrent()); //当前页
        System.out.println(page.getRecords()); //每页数据list集合
        System.out.println(page.getSize()); // 每页显示记录数
        System.out.println(page.getTotal()); // 总记录数
        System.out.println(page.getPages()); // 总页数

        System.out.println(page.hasNext()); // 是否有下一页
        System.out.println(page.hasPrevious()); // 是否有上一页
    }

    @Test
    void testLogicDelete(){
        helloMapper.deleteById(2L);
    }

    // 查询所有记录
    @Test
    void findAll(){
        List<Hello> list = helloMapper.selectList(null);
        list.forEach(System.out::println);
    }

    // mp实现复杂查询操作  queryWrapper
    @Test
    void testSelectWrapper(){
        QueryWrapper<Hello> wrapper = new QueryWrapper<>();

        // ge：>= gt：> le：<= lt：<

        // eq：= ne：!=
//        wrapper.eq("name","jack4");
//        Hello hello = helloMapper.selectOne(wrapper);
//
//        System.out.println(hello);

        // last 直接拼接到 sql 的最后
//        wrapper.last("limit 1");
//        List<Hello> list = helloMapper.selectList(wrapper);
//        list.forEach(System.out::println);

        // orderby：升序（默认），orderByDesc：降序

        // select(String... sqlSelect) 指定要查询的列
        wrapper.select("name","password");
        List<Hello> list = helloMapper.selectList(wrapper);
        list.forEach(System.out::println);

    }
}
