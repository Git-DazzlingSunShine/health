package com.yanglei.mapper;

import com.yanglei.pojo.CheckItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckItemMapper {

    public void add(CheckItem checkItem);

    public List<CheckItem> findPage(@Param("queryString") String queryString);

    CheckItem selectByCode(@Param("code") String code);

    public int delete(@Param("id") Integer id);

    int updateByCode(CheckItem checkItem);

    /**
     * 根据id查询从表关联总数
     * @param id
     * @return
     */
    Long findCountByCheckItemId(Integer id);

    /**
     * 查询t_checkitem表中全部检查项目
     * @return
     */
    List<CheckItem> findAll();

}
