package com.njxnet.service.tmsp.entity;

import java.util.Date;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.njxnet.service.tmsp.constants.FreezeIEnum;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * (TmspSysUser)表实体类
 *
 * @author Stone
 * @since 2023-06-26 16:55:36
 */
@SuppressWarnings("serial")
public class TmspSysUser extends Model<TmspSysUser> {
    //主键
    private Long id;
    //用户名
    private String userName;
    //密码
    private String password;
    //所属法院
    private String courtCode;
    //角色标识
    private String roleId;
    //状态（0-冻结，1-可用）
    private FreezeIEnum status;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //删除标记 0-删除 1-未删除
    private Integer delMark;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCourtCode() {
        return courtCode;
    }

    public void setCourtCode(String courtCode) {
        this.courtCode = courtCode;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public FreezeIEnum getStatus() {
        return status;
    }

    public void setStatus(FreezeIEnum status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDelMark() {
        return delMark;
    }

    public void setDelMark(Integer delMark) {
        this.delMark = delMark;
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

