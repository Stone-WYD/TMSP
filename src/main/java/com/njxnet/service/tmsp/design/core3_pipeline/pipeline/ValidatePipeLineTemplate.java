package com.njxnet.service.tmsp.design.core3_pipeline.pipeline;

import com.njxnet.service.tmsp.design.core3_pipeline.PipeLine;
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
