package com.njxnet.service.tmsp.model.info;

import com.njxnet.service.tmsp.entity.TmspSysResource;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author bzs
 * @since 2022/6/8 18:27
 */
public class ResourceNodeInfo extends TmspSysResource {

    @ApiModelProperty(notes = "子栏目列表", name = "children")
    private List<ResourceNodeInfo> children;

    public List<ResourceNodeInfo> getChildren() {
        return children;
    }

    public void setChildren(List<ResourceNodeInfo> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "ResourceNodeInfo{" +
                "children=" + children +
                '}';
    }
}
