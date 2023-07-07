package com.njxnet.service.tmsp.core.send.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.njxnet.service.tmsp.common.BaseException;
import com.njxnet.service.tmsp.core.PostContext;
import com.njxnet.service.tmsp.core.send.SendMessageOuterPostProcessor;
import com.njxnet.service.tmsp.model.info.SendInfo;
import com.njxnet.service.tmsp.service.BlackListService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.njxnet.service.tmsp.common.ResultStatusCode.PHONES_EMPTY_ERROR;

@Component
public class SendMessageOuterCheckBlackListPostProcessor implements SendMessageOuterPostProcessor {

    @Resource
    private BlackListService blackListService;

    @Override
    public boolean handleBefore(PostContext<SendInfo> postContext) {
        SendInfo sendInfo = postContext.getT();
        if (StrUtil.isBlank(sendInfo.getMobile()) && StrUtil.isBlank(sendInfo.getMobiles())){
            // 没有手机号
            throw new BaseException(PHONES_EMPTY_ERROR.getCode(), PHONES_EMPTY_ERROR.getName());
        }

        if (StrUtil.isNotBlank(sendInfo.getMobile())) {
            // 有 mobile，说明是单发
            if (isPhoneInBlackList(sendInfo.getMobile())) {
                throw new BaseException(PHONES_EMPTY_ERROR.getCode(), PHONES_EMPTY_ERROR.getName());
            }
        }
        if (StrUtil.isNotBlank(sendInfo.getMobiles())){
            // 有 mobiles，说明是群发
            String mobiles = removeBlackListPhoneReturnString(sendInfo.getMobiles());
            sendInfo.setMobiles(mobiles);
        }
        return false;
    }

    private boolean isPhoneInBlackList(String phone){
        return blackListService.query().eq("phone_number", phone).one() != null;
    }

    private String removeBlackListPhoneReturnString(String mobiles) {
        List<String> resultList = new ArrayList<>();
        List<String> mobileList = Arrays.stream(mobiles.split(",")).map(String::trim).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(mobileList)) throw new BaseException(PHONES_EMPTY_ERROR.getCode(), PHONES_EMPTY_ERROR.getName());

        for (String mobile : mobileList){
            if (!isPhoneInBlackList(mobile)) {
                resultList.add(mobile);
            }
        }
        if (resultList.size() == 0){
            throw new BaseException(PHONES_EMPTY_ERROR.getCode(), PHONES_EMPTY_ERROR.getName());
        }
        if (resultList.size() == 1){
            return resultList.get(0);
        }
        return StrUtil.join(",", resultList);
    }
}
