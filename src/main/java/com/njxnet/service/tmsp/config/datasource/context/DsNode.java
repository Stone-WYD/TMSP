package com.njxnet.service.tmsp.config.datasource.context;

public class DsNode {
   private DsNode pre;
   private String ds;

    public DsNode(DsNode parent, String ds){
        pre = parent;
        this.ds = ds;
    }

    public DsNode getPre() {
        return pre;
    }

    public String getDs() {
        return ds;
    }
}
