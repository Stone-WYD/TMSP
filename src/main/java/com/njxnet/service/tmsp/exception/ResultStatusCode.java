package com.njxnet.service.tmsp.exception;

/**
 * 返回状态码枚举
 */
public enum ResultStatusCode {
    SUCCESS(0, "操作成功"),
    FAIL(1, "操作失败"),
    ERROR(500, "服务器内部错误"),
    TOKEN_NOT_FOUND(1000,"用户token信息不存在"),
    UNKNOWN_FILE(1011, "未知文件"),
    FILE_UPLOAD_FAIL(1012, "文件上传失败"),
    CAPTCHA_ERROR(2001, "验证码输入错误"),
    LOGIN_ERROR(2002, "用户名或者密码错误"),
    NO_RIGHT_TO_DO_IT(2003, "对不起，您没有权限执行本操作"),
    LOGIN_FREEZE(2004,"账号已被冻结"),
    MISSING_PARAM(2005, "参数缺失"),
    USER_EXIST(2006, "本法院或其他法院已存在该用户名"),
    AUTH_EXIST(2008,"该角色已存在"),
    DATA_EXIST(2009,"该数据项已存在"),
    COMPANY_EXIST(2010,"该公司已存在"),
    USED_DATA_ITEM(2011,"存在被使用数据项,请检查"),
    PERSON_EXIST(2012,"该人员已存在"),
    USED_MODEL(2013,"该模型正在使用,无法进行操作"),
    USED_TARGET(2014,"该指标已经被使用,无法进行操作"),
    TARGET_EXIST(2015,"该指标已经存在"),


    ;

    /**
     * 状态码
     */
    private int code;

    /**
     * 状态信息
     */
    private String name;

    ResultStatusCode(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
