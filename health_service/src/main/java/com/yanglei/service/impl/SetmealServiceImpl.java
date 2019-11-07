package com.yanglei.service.impl;

import cn.hutool.json.JSONObject;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yanglei.content.MessageConstant;
import com.yanglei.entry.PageResult;
import com.yanglei.entry.QueryPageBean;
import com.yanglei.entry.Result;
import com.yanglei.mapper.CheckGroupMapper;
import com.yanglei.mapper.SetmealMapper;
import com.yanglei.pojo.CheckGroup;
import com.yanglei.pojo.Setmeal;
import com.yanglei.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private CheckGroupMapper checkGroupMapper;

    @Override
    public Result add(Setmeal setmeal) {
        try {
            List<Integer> checkGroupIds = setmeal.getCheckGroupIds();
            if (checkGroupIds.isEmpty()) {
                return new Result(false, MessageConstant.CHECKGROUP_IS_EMPTY);
            }
            setmealMapper.add(setmeal);
            setSetmealAndCheckGroupRelation(setmeal.getCheckGroupIds(), setmeal.getId());
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            return new Result(false, MessageConstant.CHECKGROUP_IS_EMPTY);
        }


    }

    @Override
    public PageResult queryPage(QueryPageBean queryPageBean) {
        Page<Object> pages = PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        String queryString = "";
        try {
            queryString = queryPageBean.getQueryString();
        } catch (Exception e) {
            System.err.println("未传入查询条件");
        }
        //查询
        List<Setmeal> setmeals = setmealMapper.queryAll(queryString);

        return new PageResult(pages.getTotal(), setmeals);
    }

    @Override
    public Result delete(Integer id) {
        try {
            deleteSetmealAndCheckGroupRelation(id);
            setmealMapper.deletSetmealByid(id);
            return new Result(true, MessageConstant.DELETE_SETMEAL_SUCCESS);
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_SETMEAL_FAIL);
        }

    }

    @Override
    public Result update(Setmeal setmeal) {
        try {
            List<Integer> checkGroupIds = setmeal.getCheckGroupIds();
            Integer setmealId = setmeal.getId();
            setmealMapper.updateSetmeal(setmeal);
            //删除套餐和检查组关联
            deleteSetmealAndCheckGroupRelation(setmealId);
            setSetmealAndCheckGroupRelation(checkGroupIds, setmealId);
            return new Result(true, MessageConstant.EDIT_SETMEAL_SUCCESS);
        } catch (NullPointerException e) {
            return new Result(false, MessageConstant.CHECKGROUP_IS_EMPTY);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_SETMEAL_FAIL);
        }
    }

    @Override
    public Result querySetmealById(Integer id) {
        try {
            Setmeal setmeal = setmealMapper.querySetmealById(id);
            List<CheckGroup> groups = checkGroupMapper.findPage(null);
            setmeal.setCheckGroups(groups);
            List<Integer> checkGroupIds = setmealMapper.querySetmealAndCheckGroupRelation(id);
            setmeal.setCheckGroupIds(checkGroupIds);
            return new Result(true, "", setmeal);
        } catch (Exception e) {
            return new Result(false, "数据不存在");
        }

    }

    //插入检查套餐和检查组的关系
    public int setSetmealAndCheckGroupRelation(List<Integer> checkGroupIds, Integer setmealId) {
        List<Map> params = new ArrayList<>();
        for (Integer checkGroupId : checkGroupIds) {
            Map map = new HashMap();
            map.put("setmeal_id", setmealId);
            map.put("checkgroup_id", checkGroupId);
            params.add(map);
        }
        return setmealMapper.setSetmealAndCheckGroupRelationBatch(params);
    }

    public int deleteSetmealAndCheckGroupRelation(Integer setmealId) {
        return setmealMapper.deleteSetmealAndCheckGroupRelation(setmealId);
    }

}
