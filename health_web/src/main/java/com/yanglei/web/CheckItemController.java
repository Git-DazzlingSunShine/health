package com.yanglei.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yanglei.annotation.SystemWebLog;
import com.yanglei.content.MessageConstant;
import com.yanglei.entry.PageResult;
import com.yanglei.entry.QueryPageBean;
import com.yanglei.entry.Result;
import com.yanglei.exception.ForeignAssociationException;
import com.yanglei.pojo.CheckItem;
import com.yanglei.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    /**
     * 添加检查项
     *
     * @param checkItem
     * @return
     */
    @RequestMapping("add")
    @SystemWebLog(description = "添加检查项")
    @PreAuthorize("hasAuthority('CHECKGROUP_ADD')")
    public Result add(@RequestBody CheckItem checkItem) {
        Result result = codeUnique(checkItem.getCode());
        if (!result.isFlag()) {
            return result;
        }

        try {
            checkItemService.add(checkItem);
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
    }

    /**
     * 分页查询
     *
     * @param queryPageBean
     * @return 分页结果集
     */
    @RequestMapping("findpage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return checkItemService.findPage(queryPageBean);
    }

    /**
     * 根据编码查询检查项数据
     *
     * @param code
     * @return 返回标准结果集
     */
    @RequestMapping("selectByCode")
    public Result selectByCode(String code) {
        Result result = new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
        result.setData(checkItemService.selectByCode(code));
        return result;
    }

    /**
     * 检查项编码唯一性校验
     *
     * @param code
     * @return
     */
    @RequestMapping("codeUnique")
    public Result codeUnique(String code) {
        if (checkItemService.selectByCode(code) == null) {
            return new Result(true, MessageConstant.CHECKITEM_IS_NO);
        }
        return new Result(false, MessageConstant.CHECKITEM_EXISTED);
    }

    /**
     * 更新检查项
     *
     * @param checkItem
     * @return
     */
    @RequestMapping("update")
    @SystemWebLog(description = "修改检查项")
    @PreAuthorize("hasAuthority('CHECKGROUP_EDIT')")
    public Result update(@RequestBody CheckItem checkItem) {
        try {
            boolean update = checkItemService.updateByCode(checkItem);
            return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }
    }

    /**
     * 根据id删除检查项
     *
     * @param id
     * @return
     */
    @RequestMapping("delete")
    @SystemWebLog(description = "删除检查项")
    @PreAuthorize("hasAuthority('CHECKGROUP_DELETE')")
    public Result deleteByCode(Integer id) {
        Result result = new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        try {
            if (checkItemService.delete(id)) {
                result.setFlag(true);
                result.setMessage(MessageConstant.DELETE_CHECKITEM_SUCCESS);
            }
        } catch (ForeignAssociationException e) {
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            return result;
        }
        return result;
    }

    /**
     * 查询全部的检查项目
     *
     * @return 标椎实体对象
     */
    @RequestMapping("findAll")
    public Result findAll() {
        List<CheckItem> checkitems = checkItemService.findAll();
        Result result = new Result(false, "");
        if (checkitems != null && checkitems.size() > 0) {
            result.setFlag(true);
            result.setData(checkitems);
        }
        return result;
    }


}
