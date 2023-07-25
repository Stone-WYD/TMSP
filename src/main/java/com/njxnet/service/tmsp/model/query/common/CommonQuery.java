package com.njxnet.service.tmsp.model.query.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @program: HNCC
 * @description: 一般查询类
 * @author: Stone
 * @create: 2023-07-21 13:57
 **/
@Data
@ToString
public class CommonQuery implements Serializable {

    @ApiModelProperty(notes = "开始日期", name = "beginDate")
    private LocalDateTime beginDate;

    @ApiModelProperty(notes = "结束日期", name = "endDate")
    private LocalDateTime endDate;

    @ApiModelProperty(notes = "法院id", name = "法院id")
    private String courtId;

    @ApiModelProperty(notes = "页码", name = "page")
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小为1")
    private Long page;

    @ApiModelProperty(notes = "每页数量", name = "size")
    @NotNull(message = "每页数量不能为空")
    @Min(value = 1, message = "每页数量最少为1")
    private Long size;
}
