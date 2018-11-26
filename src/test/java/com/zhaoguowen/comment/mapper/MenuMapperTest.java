package com.zhaoguowen.comment.mapper;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.zhaoguowen.comment.entity.Menu;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MenuMapperTest {

    @Autowired
    private MenuMapper menuMapper;

    @Test
    public void selectMenuWithAction() {

        List<Menu> menus = menuMapper.selectMenuWithAction();

        log.info("menus={}", menus);
    }

    @Test
    public void selectMenus() {
        List<Menu> menus = menuMapper.selectList(null);
        log.info("menus={}", menus);
    }

    @Test
    public void selectMenuTree() {
        List<Menu> menus = menuMapper.selectList(null);

        Multimap<Integer, Menu> menuMultimap = ArrayListMultimap.create();

        //处理第一层
        List<Menu> rootList = Lists.newArrayList();

        for (Menu menu : menus) {
            menuMultimap.put(menu.getParentId(), menu);
            if (menu.getParentId().equals(0)) {
                //处理根节点
                rootList.add(menu);
            }
        }

        //排序
        Collections.sort(rootList, Comparator.comparingInt(Menu::getOrderNum));

        recursionToTree(rootList, menuMultimap);

        log.info("menus={}", menus);
    }

    //递归成树形
    private void recursionToTree(List<Menu> rootList, Multimap<Integer, Menu> menuMultimap) {

        for (Menu menu : rootList) {

            List<Menu> childrenMenus = (List<Menu>) menuMultimap.get(menu.getId());
            if (CollectionUtils.isEmpty(childrenMenus)) {
                menu.setMenuList(Lists.newArrayList());
            } else {
                Collections.sort(childrenMenus, Comparator.comparingInt(Menu::getOrderNum));
                menu.setMenuList(childrenMenus);
                //递归处理
                recursionToTree(childrenMenus, menuMultimap);
            }

        }

    }

    @Test
    public void testBcry() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("123456");
        log.info("加密后密码={}", encode);
    }
}