package com.njxnet.service.tmsp.design.core4_aware;

import lombok.Data;

/**
 * @program: TMSP
 * @description: 检测内容，信息承载类
 * @author: Stone
 * @create: 2023-07-13 10:35
 **/
@Data
public class ValidateContent {

    private String content;

    private String illegalContent;

    private String cause;
}
