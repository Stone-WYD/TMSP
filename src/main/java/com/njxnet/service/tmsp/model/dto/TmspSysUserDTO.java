package com.njxnet.service.tmsp.model.dto;

import com.njxnet.service.tmsp.constants.FreezeIEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TmspSysUserDTO {
    //主键
    private Long id;

    //用户名
    @ApiModelProperty(notes = "用户名", name = "userName")
    private String userName;

    //法院code
    @ApiModelProperty(notes = "法院code", name = "courtCode")
    private String courtCode;

    //所属法院
    @ApiModelProperty(notes = "所属法院", name = "courtName")
    private String courtName;

    //状态（0-冻结，1-可用）
    private FreezeIEnum status;

    //创建时间
    private Date createTime;


}
