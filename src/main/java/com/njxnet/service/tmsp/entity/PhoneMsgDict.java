package com.njxnet.service.tmsp.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * (PhoneMsgDict)表实体类
 *
 * @author Stone
 * @since 2023-06-28 11:09:01
 */
@SuppressWarnings("serial")
public class PhoneMsgDict extends Model<PhoneMsgDict> {
    
    private Integer id;
    //短信类型
    private String type;
    //模板id
    private String mouldId;
    //模板变量值
    private String mouldKey;
    //创蓝短信模板值
    private String msgvalue;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    @Override
    public String toString() {
        return "PhoneMsgDict{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", mouldId='" + mouldId + '\'' +
                ", mouldKey='" + mouldKey + '\'' +
                ", msgvalue='" + msgvalue + '\'' +
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

