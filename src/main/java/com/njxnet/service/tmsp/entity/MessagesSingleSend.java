package com.njxnet.service.tmsp.entity;

import java.util.Date;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.njxnet.service.tmsp.constant.MessageSendStatusEnum;

import java.io.Serializable;

/**
 * (MessagesSingleSend)表实体类
 *
 * @author Stone
 * @since 2023-07-07 13:56:18
 */
@SuppressWarnings("serial")
public class MessagesSingleSend extends Model<MessagesSingleSend> {
    //主键
    private Long id;
    //标题
    private String title;
    //发送人姓名
    private String userName;
    //手机号
    private String phoneNumber;
    //状态
    private MessageSendStatusEnum status;
    //发送内容
    private String content;
    //创建时间
    private Date createTime;
    //完成发送时间
    private Date finishTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public MessageSendStatusEnum getStatus() {
        return status;
    }

    public void setStatus(MessageSendStatusEnum status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
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

