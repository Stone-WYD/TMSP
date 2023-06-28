package com.njxnet.service.tmsp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * 法院表(Court)表实体类
 *
 * @author Stone
 * @since 2023-06-26 17:42:20
 */
@SuppressWarnings("serial")
public class Court extends Model<Court> {
    
    private String id;
    
    private String courtId;
    //法院编号
    private String courtCode;
    //法院名称
    private String courtName;
    //父级
    private Integer parentId;
    //法院概况
    private String courtOverview;
    //管辖范围
    private String jurisdiction;
    //邮编
    private String postcode;
    //部门职位
    private String deptPosition;
    //投诉渠道
    private String complaintChannel;
    //办公地址
    private String address;
    //接待时间
    private String receptionTime;
    //立案咨询电话
    private String caseFillingTel;
    //诉讼服务电话
    private String litigationServiceTel;
    //法院信息
    private String courtInfo;
    //经度
    private String longitude;
    //纬度
    private String latitude;
    //城市名
    private String city;
    //省份 
    private String province;
    //法院封面
    private String coverPath;
    //法院编码（华宇）
    private String courtCodeHy;
    
    private String payQrcodePath;
    
    private String logoPath;
    //设备故障通知号码
    private String faultNoticeMobile;
    
    private String framework;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourtId() {
        return courtId;
    }

    public void setCourtId(String courtId) {
        this.courtId = courtId;
    }

    public String getCourtCode() {
        return courtCode;
    }

    public void setCourtCode(String courtCode) {
        this.courtCode = courtCode;
    }

    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getCourtOverview() {
        return courtOverview;
    }

    public void setCourtOverview(String courtOverview) {
        this.courtOverview = courtOverview;
    }

    public String getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(String jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getDeptPosition() {
        return deptPosition;
    }

    public void setDeptPosition(String deptPosition) {
        this.deptPosition = deptPosition;
    }

    public String getComplaintChannel() {
        return complaintChannel;
    }

    public void setComplaintChannel(String complaintChannel) {
        this.complaintChannel = complaintChannel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReceptionTime() {
        return receptionTime;
    }

    public void setReceptionTime(String receptionTime) {
        this.receptionTime = receptionTime;
    }

    public String getCaseFillingTel() {
        return caseFillingTel;
    }

    public void setCaseFillingTel(String caseFillingTel) {
        this.caseFillingTel = caseFillingTel;
    }

    public String getLitigationServiceTel() {
        return litigationServiceTel;
    }

    public void setLitigationServiceTel(String litigationServiceTel) {
        this.litigationServiceTel = litigationServiceTel;
    }

    public String getCourtInfo() {
        return courtInfo;
    }

    public void setCourtInfo(String courtInfo) {
        this.courtInfo = courtInfo;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public String getCourtCodeHy() {
        return courtCodeHy;
    }

    public void setCourtCodeHy(String courtCodeHy) {
        this.courtCodeHy = courtCodeHy;
    }

    public String getPayQrcodePath() {
        return payQrcodePath;
    }

    public void setPayQrcodePath(String payQrcodePath) {
        this.payQrcodePath = payQrcodePath;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getFaultNoticeMobile() {
        return faultNoticeMobile;
    }

    public void setFaultNoticeMobile(String faultNoticeMobile) {
        this.faultNoticeMobile = faultNoticeMobile;
    }

    public String getFramework() {
        return framework;
    }

    public void setFramework(String framework) {
        this.framework = framework;
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

