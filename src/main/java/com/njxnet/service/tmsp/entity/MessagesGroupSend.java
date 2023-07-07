package com.njxnet.service.tmsp.entity;

import java.util.Date;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * 群发短信发送情况(MessagesGroupSend)表实体类
 *
 * @author Stone
 * @since 2023-07-07 13:55:54
 */
@SuppressWarnings("serial")
public class MessagesGroupSend extends Model<MessagesGroupSend> {
    //主键
    private Long id;
    //标题
    private String title;
    //发送内容
    private String content;
    //号码数
    private Integer phonesCount;
    //成功发送的数量
    private Integer sendCount;
    //未知是否发送成功数量
    private Integer unknownCount;
    //用户名
    private String userName;
    //创建时间
    private Date createDate;
    //需要发送短信的手机号
    private String phoneNumbers;
    //没能成功发出短信的手机号（包含失败和未知的）
    private String failPhoneNumbers;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getPhonesCount() {
        return phonesCount;
    }

    public void setPhonesCount(Integer phonesCount) {
        this.phonesCount = phonesCount;
    }

    public Integer getSendCount() {
        return sendCount;
    }

    public void setSendCount(Integer sendCount) {
        this.sendCount = sendCount;
    }

    public Integer getUnknownCount() {
        return unknownCount;
    }

    public void setUnknownCount(Integer unknownCount) {
        this.unknownCount = unknownCount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(String phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public String getFailPhoneNumbers() {
        return failPhoneNumbers;
    }

    public void setFailPhoneNumbers(String failPhoneNumbers) {
        this.failPhoneNumbers = failPhoneNumbers;
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

