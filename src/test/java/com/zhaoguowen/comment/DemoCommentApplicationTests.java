package com.zhaoguowen.comment;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhaoguowen.comment.entity.Menu;
import com.zhaoguowen.comment.mapper.MenuMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DemoCommentApplicationTests {


    @Autowired
    private MenuMapper menuMapper;

    @Test
    public void contextLoads() {

        Menu menu = menuMapper.selectById(1);

        log.info("menu={}", menu);
    }


    @Test
    public void testPage() {

        //Menu menu = menuMapper.selectById(1);

        IPage<Menu> page = new Page<>(1, 1);
        IPage<Menu> menuIPage = menuMapper.selectPage(page, null);
        log.info("menuIPage = {}", menuIPage);

    }


}
