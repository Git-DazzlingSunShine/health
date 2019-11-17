package com.yanglei.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yanglei.annotation.SystemWebLog;
import com.yanglei.entry.PageResult;
import com.yanglei.entry.QueryPageBean;
import com.yanglei.entry.Result;
import com.yanglei.pojo.CheckGroup;
import com.yanglei.service.CheckGroupService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/checkgroup")
@RestController
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;

    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('CHECKGROUP_ADD')")
    @SystemWebLog(description = "添加检查组")
    public Result add(@RequestBody CheckGroup checkGroup) {
        return checkGroupService.add(checkGroup);
    }

    @RequestMapping("findPage")
    public PageResult findAll(@RequestBody QueryPageBean queryPageBean) {
        return checkGroupService.findPage(queryPageBean);
    }

    @RequestMapping("edit")
    @SystemWebLog(description = "修改检查组")
    public Result edit(@RequestBody CheckGroup checkGroup) {
        return checkGroupService.updateGroup(checkGroup);
    }

    @RequestMapping("findByIdUpdate")
    public Result findByIdUpdate(Integer id) {
        return checkGroupService.findByIdUpDate(id);
    }

    @RequestMapping("delete")
    @SystemWebLog(description = "删除检查组")
    @PreAuthorize("hasAuthority('CHECKGROUP_DELETE')")
    public Result delete(Integer id) {
        return checkGroupService.deleteGroupById(id);
    }

    @RequestMapping("queryAll")
    public Result queryAll() {
        return checkGroupService.queryAll();
    }

}
