package com.zhaoguowen.comment.config;


import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Date;

@Configuration
@EnableTransactionManagement
//@MapperScan("com.zhaoguowen.comment.mapper")
@Slf4j
public class MybatisPlusConfig {


    @Bean
    public PaginationInterceptor paginationInterceptor() {

        return new PaginationInterceptor();
    }


    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }

    @Bean
    public MetaObjectHandler createDateMetaObjectHandle() {
        return new CreateDateMetaObjectHandler();
    }


    /**
     * 自定义数据填充
     */
    public static class CreateDateMetaObjectHandler implements MetaObjectHandler {

        @Override
        public void insertFill(MetaObject metaObject) {
            Object originalObject = metaObject.getOriginalObject();
            log.info(originalObject.getClass().getName());
            Date now = new Date();
            this.setFieldValByName("createDate", now, metaObject);
            this.setFieldValByName("modifyDate", now, metaObject);

        }

        @Override
        public void updateFill(MetaObject metaObject) {
            this.setFieldValByName("modifyDate", new Date(), metaObject);
        }
    }
}
