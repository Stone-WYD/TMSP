package com.njxnet.service.tmsp.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AjaxResult<T> {

    private T data;

    private int code;

    private String message;

    public AjaxResult(int code, String name) {
        AjaxResult<?> result = new AjaxResult<>();
        result.setMessage(name);
        result.setCode(code);
    }
}
