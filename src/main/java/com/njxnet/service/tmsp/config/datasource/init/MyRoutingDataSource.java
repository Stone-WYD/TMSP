package com.njxnet.service.tmsp.config.datasource.init;

import com.njxnet.service.tmsp.config.datasource.context.DsContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MyRoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DsContextHolder.get();
    }
}
