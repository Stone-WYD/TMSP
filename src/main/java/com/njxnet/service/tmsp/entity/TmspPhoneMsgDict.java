package com.njxnet.service.tmsp.entity;

import java.util.Date;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * (TmspPhoneMsgDict)表实体类
 *
 * @author Stone
 * @since 2023-06-28 14:26:10
 */
@SuppressWarnings("serial")
public class TmspPhoneMsgDict extends Model<TmspPhoneMsgDict> {
    //短信模板id，业务无关
    @ApiModelProperty(notes = "短信模板id，查询用，业务无关", name = "短信模板id，查询用，业务无关")
    private Long id;

    //短信类型
    @ApiModelProperty(notes = "短信类型", name = "短信类型")
    private String type;

    //模板id，业务相关
    @ApiModelProperty(notes = "模板id，业务相关", name = "模板id，业务相关")
    private String mouldId;

    //模板名称
    @ApiModelProperty(notes = "模板名称", name = "模板名称")
    private String mouldName;

    //模板变量值
    @ApiModelProperty(notes = "模板变量值", name = "模板变量值")
    private String mouldKey;

    //创蓝短信模板值
    @ApiModelProperty(notes = "创蓝短信模板值", name = "创蓝短信模板值")
    private String msgvalue;

    //字数
    @ApiModelProperty(notes = "字数", name = "字数")
    private Integer letterNum;

    //备注
    @ApiModelProperty(notes = "备注", name = "备注")
    private String comment;

    //创建时间
    @ApiModelProperty(notes = "创建时间", name = "创建时间")
    private Date createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMouldId() {
        return mouldId;
    }

    public void setMouldId(String mouldId) {
        this.mouldId = mouldId;
    }

    public String getMouldName() {
        return mouldName;
    }

    public void setMouldName(String mouldName) {
        this.mouldName = mouldName;
    }

    public String getMouldKey() {
        return mouldKey;
    }

    public void setMouldKey(String mouldKey) {
        this.mouldKey = mouldKey;
    }

    public String getMsgvalue() {
        return msgvalue;
    }

    public void setMsgvalue(String msgvalue) {
        this.msgvalue = msgvalue;
    }

    public Integer getLetterNum() {
        return letterNum;
    }

    public void setLetterNum(Integer letterNum) {
        this.letterNum = letterNum;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "TmspPhoneMsgDict{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", mouldId='" + mouldId + '\'' +
                ", mouldName='" + mouldName + '\'' +
                ", mouldKey='" + mouldKey + '\'' +
                ", msgvalue='" + msgvalue + '\'' +
                ", letterNum=" + letterNum +
                ", comment='" + comment + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.id;
    }
    }

