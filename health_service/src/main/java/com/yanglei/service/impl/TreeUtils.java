package com.yanglei.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yanglei.pojo.Menu;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TreeUtils {
    //根节点，用来存放root菜单
    private List<Menu> rootList;
    //全部菜单
    private List<Menu> boodyList;

    public TreeUtils(List<Menu> rootList, List<Menu> boodyList) {
        this.rootList = rootList;
        this.boodyList = boodyList;
    }

    //获取菜单树
    public List<Menu> getTree() {
        if (boodyList != null && !boodyList.isEmpty()) {
            Map<Object, Object> map = Maps.newHashMapWithExpectedSize(boodyList.size());
            rootList.forEach(beanTree -> getChild(beanTree, map));
            return rootList;
        }
        return null;
    }

    public void getChild(Menu treeDto, Map<Object, Object> map) {
        ArrayList<Menu> childList = Lists.newArrayList();
        boodyList.stream()
                .filter(c -> c.getParentMenuId() != null && !map.containsKey(c.getParentMenuId()))
                .filter(c -> c.getParentMenuId().equals(treeDto.getId()))
                .forEach(c -> {
                    //把已经处理完的菜单id、父级菜单id存入map，用于下次标示已处理过
                    map.put(c.getId(), c.getParentMenuId());
                    getChild(c, map);
                    childList.add(c);
                });
        treeDto.setChildren(childList);
    }
}
