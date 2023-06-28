package com.njxnet.service.tmsp.utils;

import com.njxnet.service.tmsp.model.info.ResourceNodeInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 说明类的用途
 * @ClassName BuildUrils
 * @Author zzd
 * @Date 2020/8/6 14:55
 * @Version 1.0
 **/
public class BuildUtils {

    /**
     * 组装节点
     *
     * @param reList 节点信息列表
     * @return 节点信息列表
     */
    public static List<ResourceNodeInfo> buildNode(List<ResourceNodeInfo> reList) {
        List<ResourceNodeInfo> result = new ArrayList<>();
        for (ResourceNodeInfo out : reList) {
            out.setChildren(new ArrayList<>());
            for (ResourceNodeInfo in : reList) {
                if (out.getResourceId().equals(in.getParentResourceId())) {
                    out.getChildren().add(in);
                }
            }
        }
        for (ResourceNodeInfo info : reList) {
            if ("0".equals(info.getParentResourceId())) {
                result.add(info);
            }
        }
        return result;
    }

}
