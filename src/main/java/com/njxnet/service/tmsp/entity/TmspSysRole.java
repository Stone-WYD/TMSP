package com.njxnet.service.tmsp.entity;

import java.util.Date;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * (TmspSysRole)表实体类
 *
 * @author Stone
 * @since 2023-06-26 16:55:28
 */
@SuppressWarnings("serial")
public class TmspSysRole extends Model<TmspSysRole> {
    //主键
    private Long id;
    //角色名称
    private String roleName;
    //所属法院
    private String courtCode;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //删除标记
    private Integer delMark;
    //是否超级管理员，0否，1是
    private Integer adminMark;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getCourtCode() {
        return courtCode;
    }

    public void setCourtCode(String courtCode) {
        this.courtCode = courtCode;
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

    public Integer getAdminMark() {
        return adminMark;
    }

    public void setAdminMark(Integer adminMark) {
        this.adminMark = adminMark;
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

