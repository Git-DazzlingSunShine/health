package com.yanglei.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yanglei.entry.PageResult;
import com.yanglei.entry.QueryPageBean;
import com.yanglei.entry.Result;
import com.yanglei.pojo.CheckGroup;
import com.yanglei.service.CheckGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/checkgroup")
@RestController
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;

    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup) {
        return checkGroupService.add(checkGroup);
    }

    @RequestMapping("findPage")
    public PageResult findAll(@RequestBody QueryPageBean queryPageBean) {
        return checkGroupService.findPage(queryPageBean);
    }

    @RequestMapping("edit")
    public Result edit(@RequestBody CheckGroup checkGroup) {
        return checkGroupService.updateGroup(checkGroup);
    }

    @RequestMapping("findByIdUpdate")
    public Result findByIdUpdate(Integer id) {
        return checkGroupService.findByIdUpDate(id);
    }

    @RequestMapping("delete")
    public Result delete(Integer id) {
        return checkGroupService.deleteGroupById(id);
    }

    @RequestMapping("queryAll")
    public Result queryAll() {
        return checkGroupService.queryAll();
    }

}
