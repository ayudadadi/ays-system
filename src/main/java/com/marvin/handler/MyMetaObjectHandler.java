package com.marvin.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Marvin
 * @Description mp插入更新自动填充处理器类
 * @create 2021-12-15 19:09
 */

@Component  //一定要加这个注解，交给spring管理，否则失效
public class MyMetaObjectHandler implements MetaObjectHandler {
    // 使用mp完成自动添加操作
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("version", 1, metaObject);
        this.setFieldValByName("created", new Date(), metaObject);
        this.setFieldValByName("updated", new Date(), metaObject);
        this.setFieldValByName("releaseDate", new Date(), metaObject);

    }

    // 使用mp完成自动更新操作
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updated", new Date(), metaObject);

    }
}
