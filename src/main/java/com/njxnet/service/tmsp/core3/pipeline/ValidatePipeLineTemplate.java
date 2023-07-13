package com.njxnet.service.tmsp.core3.pipeline;

import com.njxnet.service.tmsp.core3.PipeLine;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: TMSP
 * @description: 短信内容校验模板类，初始化管道，获取管道用
 * @author: Stone
 * @create: 2023-07-11 21:16
 **/
@Data
public class ValidatePipeLineTemplate {

    private List<PipeLine> validatePipeLineList = new ArrayList<>();

}
