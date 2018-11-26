package com.zhaoguowen.comment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhaoguowen.comment.entity.Menu;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zhaoguowen
 * @since 2018-11-19
 */
public interface MenuMapper extends BaseMapper<Menu> {


    List<Menu> selectMenuWithAction();




}
