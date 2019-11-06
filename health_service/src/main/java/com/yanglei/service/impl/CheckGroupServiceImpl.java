package com.yanglei.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yanglei.content.MessageConstant;
import com.yanglei.entry.PageResult;
import com.yanglei.entry.QueryPageBean;
import com.yanglei.entry.Result;
import com.yanglei.mapper.CheckGroupMapper;
import com.yanglei.mapper.CheckItemMapper;
import com.yanglei.pojo.CheckGroup;
import com.yanglei.pojo.CheckItem;
import com.yanglei.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupMapper checkGroupMapper;

    @Autowired
    private CheckItemMapper checkItemMapper;

    @Override
    public Result add(CheckGroup checkGroup) {
        List<Integer> checkItemIds = checkGroup.getCheckitemIds();
        if (CollectionUtil.isEmpty(checkItemIds) || checkItemIds.equals("null")) {
            return new Result(false, "请勾选检查项");
        }
        int add = checkGroupMapper.add(checkGroup);

        //添加检查组合检查项关系成功
        int i = setCheckItemAndCheckGroupRelation(checkItemIds, checkGroup.getId());
        if (i > 0) {
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        }
        return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);

    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Page<Object> pages = PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        String word = "";
        try {
            word = queryPageBean.getQueryString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<CheckGroup> groups = checkGroupMapper.findPage(word);

        return new PageResult(pages.getTotal(), groups);
    }

    @Override
    public Result findByIdUpDate(Integer id) {
        CheckGroup checkGroup = checkGroupMapper.findGroupById(id);

        List<CheckItem> checkItems = checkItemMapper.findAll();

        List<Integer> checkItemIds = checkGroupMapper.findCheckItemsByGroupId(id);
        checkGroup.setCheckitemIds(checkItemIds);
        checkGroup.setCheckItems(checkItems);

        return new Result(true, "", checkGroup);
    }

    @Override
    public Result updateGroup(CheckGroup checkGroup) {
        List<Integer> checkitemIds = checkGroup.getCheckitemIds();
        if (checkitemIds.isEmpty()) {
            return new Result(false, MessageConstant.CHECKITEM_IS_EMPTY);
        }

        CheckGroup uniqueGroup = checkGroupMapper.queryGroupByCode(checkGroup.getCode());
        if (uniqueGroup.getId() != null && uniqueGroup.getId() != checkGroup.getId()) {
            return new Result(false, "该检查组编码已存在！");
        }

        checkGroupMapper.updataGroup(checkGroup);
        checkGroupMapper.deleteCheckItemAndCheckGroupRelationBatch(checkGroup.getId());
        setCheckItemAndCheckGroupRelation(checkGroup.getCheckitemIds(), checkGroup.getId());
        return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    @Override
    public Result deleteGroupById(Integer id) {
        checkGroupMapper.deleteCheckItemAndCheckGroupRelationBatch(id);
        checkGroupMapper.deleteGroupById(id);
        return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }

    @Override
    public Result queryAll() {
        List<CheckGroup> checkGroups = checkGroupMapper.findPage(null);
        return new Result(true, "", checkGroups);
    }

    //插入检查组合检查项关联
    public int setCheckItemAndCheckGroupRelation(List<Integer> checkitemIds, Integer checkGroupId) {
        List<Map> params = new ArrayList<>();
        for (Integer checkitemId : checkitemIds) {
            Map map = new HashMap();
            map.put("checkgroup_id", checkGroupId);
            map.put("checkitem_id", checkitemId);
            params.add(map);
        }
        return checkGroupMapper.setCheckItemAndCheckGroupRelationBatch(params);
    }
}
