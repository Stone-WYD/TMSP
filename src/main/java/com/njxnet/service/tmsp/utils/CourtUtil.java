package com.njxnet.service.tmsp.utils;

import cn.hutool.core.util.StrUtil;
import com.njxnet.service.tmsp.entity.Court;
import com.njxnet.service.tmsp.service.CourtService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CourtUtil {

    @Resource
    private CourtService courtService;

    private Map<String, Court> courtMap = new ConcurrentHashMap<>();

    public String getCourtName(String courtCode){
        if (StrUtil.isBlank(courtCode)) return null;
        Court theCourt = courtMap.get(courtCode);
        if (theCourt != null) {
            return theCourt.getCourtName();
        }else {
            Court court = courtService.query().eq("court_code", courtCode).one();
            if (court != null){
                courtMap.put(courtCode, court);
                return court.getCourtName();
            } else return null;
        }
    }
}
