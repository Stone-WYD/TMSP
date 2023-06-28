package com.njxnet.service.tmsp.model;

import java.io.Serializable;

/**
 * @Description 验证码类的用途
 * @ClassName CaptchaDTO
 * @Author zzd
 * @Create 2019/8/22 10:31
 * @Version 1.0
 **/
public class CaptchaModel implements Serializable {

    private String text;

    private String key;

    private long timeout;

    public CaptchaModel() {
    }

    public CaptchaModel(String key, String text, long timeout) {
        this.text = text;
        this.key = key;
        this.timeout = timeout;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
