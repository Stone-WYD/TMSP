package com.njxnet.service.tmsp.model.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author bzs
 * @since 2022/6/9 11:29
 */
public class CaptchaDTO {
    @ApiModelProperty(notes = "图片", name = "img")
    private String img;
    @ApiModelProperty(notes = "key", name = "key")
    private String key;

    @Override
    public String toString() {
        return "CaptchaDTO{" +
                "img='" + img + '\'' +
                ", key='" + key + '\'' +
                '}';
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
