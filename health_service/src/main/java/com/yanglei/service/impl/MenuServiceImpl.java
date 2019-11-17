package com.yanglei.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.yanglei.content.MessageConstant;
import com.yanglei.entry.Result;
import com.yanglei.mapper.MenuMapper;
import com.yanglei.mapper.RolteMapper;
import com.yanglei.pojo.Menu;
import com.yanglei.pojo.User;
import com.yanglei.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service(interfaceClass = MenuService.class)
@Transactional
public class MenuServiceImpl implements MenuService {
    @Autowired
    MenuMapper menuMapper;
    @Autowired
    RolteMapper rolteMapper;


    @Override
    public Result queryAllMenu(User user) {
        Integer userId = user.getId();
        //查询角色的菜单id
        List<Integer> menuIds = rolteMapper.queryMenuIdByUserId(userId);
        if (CollectionUtil.isEmpty(menuIds)) {
            return new Result(false, MessageConstant.GET_MENU_FAIL);
        }

        //查询菜单
        List<Menu> menus = menuMapper.queryMenuByMenuIds(menuIds);

        if (CollectionUtil.isEmpty(menus)) {
            return new Result(false, MessageConstant.GET_MENU_FAIL);
        }

        //存放根节点
        List<Menu> rootMenu = new ArrayList<>();
        for (Menu menu : menus) {
            if (menu.getLevel() == 1) {
                rootMenu.add(menu);
            }
        }
        TreeUtils treeUtils = new TreeUtils(rootMenu, menus);
        List<Menu> tree = treeUtils.getTree();
//
//        //根据根节点排序
//        Collections.sort(rootMenu, Comparator.comparingInt(Menu::getPriority));
//
//        for (Menu menu : rootMenu) {
//            List<Menu> child = getChild(menu.getId(), menus);
//            menu.setChildren(child);
//        }
//        return new Result(true, MessageConstant.GET_MENU_SUCCESS, rootMenu);
        return new Result(true, MessageConstant.GET_MENU_SUCCESS, tree);
    }

    //递归处理子菜单，已淘汰
    public List<Menu> getChild(Integer id, List<Menu> menus) {
        //子菜单
        List<Menu> childList = new ArrayList<>();
        for (Menu menu : menus) {
            if (menu.getParentMenuId() == null) {
                continue;
            }
            if (menu.getParentMenuId().equals(id)) {
                childList.add(menu);
            }
        }
        for (Menu menu : childList) {
            menu.setChildren(getChild(menu.getId(), menus));
        }
        Collections.sort(childList, Comparator.comparingInt(Menu::getId));
        if (childList.size() == 0) {
            return new ArrayList();
        }
        return childList;
    }
}
