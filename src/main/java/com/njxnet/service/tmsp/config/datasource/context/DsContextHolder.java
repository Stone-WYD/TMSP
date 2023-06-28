package com.njxnet.service.tmsp.config.datasource.context;

public class DsContextHolder {
    private static final ThreadLocal<DsNode> CONTEXT_HOLDER = new ThreadLocal();

    // 设置数据源，同时要保存父线程的数据源
    public static void set(String dbType){
        DsNode current = CONTEXT_HOLDER.get();
        CONTEXT_HOLDER.set(new DsNode(current, dbType));
    }

    // 移除上下文
    public static void reset(){
        DsNode dsNode = CONTEXT_HOLDER.get();
        if (dsNode.getPre()!=null){
            CONTEXT_HOLDER.set(dsNode.getPre());
        } else {
            CONTEXT_HOLDER.remove();
        }
    }

    public static Object get() {
        if (CONTEXT_HOLDER.get() == null) return null;
        return CONTEXT_HOLDER.get().getDs();
    }
}
