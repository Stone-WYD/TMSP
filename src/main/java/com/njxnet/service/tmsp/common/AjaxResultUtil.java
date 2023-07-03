package com.njxnet.service.tmsp.common;

public class AjaxResultUtil {
    public static <T> AjaxResult<T> getTrueAjaxResult(AjaxResult<T> ajaxResult) {
        ajaxResult.setCode(ResultStatusCode.SUCCESS.getCode());
        ajaxResult.setMessage(ResultStatusCode.SUCCESS.getName());
        return ajaxResult;
    }

    public static <T> AjaxResult<T> getFalseAjaxResult(AjaxResult<T> ajaxResult) {
        ajaxResult.setCode(ResultStatusCode.ERROR.getCode());
        ajaxResult.setMessage(ResultStatusCode.ERROR.getName());
        return ajaxResult;
    }


    public static <T> AjaxResult<T> getBussiseFalseAjaxResult(AjaxResult<T> ajaxResult, String name, int code) {
        ajaxResult.setCode(code);
        ajaxResult.setMessage(name);
        return ajaxResult;
    }
}
