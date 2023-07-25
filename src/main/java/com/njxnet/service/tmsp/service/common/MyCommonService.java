package com.njxnet.service.tmsp.service.common;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njxnet.service.tmsp.common.AjaxResult;
import com.njxnet.service.tmsp.common.AjaxResultUtil;
import com.njxnet.service.tmsp.model.query.common.CommonQuery;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: HNCC
 * @description: 通用服务类，带泛型， T:查什么类型数据，Q:查询条件，DTO:查询后转换成什么类型返回
 * @author: Stone
 * @create: 2023-07-24 18:07
 **/
 public class MyCommonService {

    public static <T, Q extends CommonQuery, DTO> AjaxResult queryForPage(Q query, Class<DTO> dtoClass, PageQuery<T> pageQuery) {
        Page<T> pageResult = pageQuery.query();
        // 类型转换
        List<T> pageResultRecords = pageResult.getRecords();
        List<DTO> dtoResult = pageResultRecords.stream()
                .map(r -> BeanUtil.copyProperties(r, dtoClass)).collect(Collectors.toList());
        // 返回结果
        Page resultPage = BeanUtil.copyProperties(pageResult, Page.class);
        resultPage.setRecords(dtoResult);
        AjaxResult result = new AjaxResult<>();
        result.setData(resultPage);
        return AjaxResultUtil.getTrueAjaxResult(result);
    }
}
