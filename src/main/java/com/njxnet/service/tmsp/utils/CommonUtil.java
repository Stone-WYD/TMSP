package com.njxnet.service.tmsp.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.njxnet.service.tmsp.common.BaseException;
import com.njxnet.service.tmsp.constants.MessageSendStatusEnum;
import com.njxnet.service.tmsp.entity.MessagesGroupSend;
import com.njxnet.service.tmsp.entity.MessagesSingleSend;
import com.njxnet.service.tmsp.model.info.SendInfo;
import com.njxnet.service.tmsp.service.BlackListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.njxnet.service.tmsp.common.ResultStatusCode.PHONES_EMPTY_ERROR;


@Slf4j
@Component
public class CommonUtil {

    @Resource
    private BlackListService blackListService;


    // 单发短信发送记录的创建
    public MessagesSingleSend createSingleSend(SendInfo message, MessagesSingleSend messagesSingleSend) {
        messagesSingleSend.setTitle(message.getTitle());
        messagesSingleSend.setUserName(message.getUserName());
        messagesSingleSend.setContent(message.getContent());
        messagesSingleSend.setCreateTime(new Date());
        messagesSingleSend.setPhoneNumber(message.getMobile());
        messagesSingleSend.setStatus(MessageSendStatusEnum.UNKNOW);
        return messagesSingleSend;
    }

    // 群发短信发送记录的创建
    public MessagesGroupSend createGroupSend(SendInfo message, MessagesGroupSend messagesGroupSend) {
        messagesGroupSend.setTitle(message.getTitle());
        messagesGroupSend.setUserName(message.getUserName());
        messagesGroupSend.setContent(message.getContent());
        messagesGroupSend.setCreateDate(new Date());
        messagesGroupSend.setPhoneNumbers(message.getMobiles());

        int count = message.getMobiles().split(",").length;
        messagesGroupSend.setUnknownCount(count);
        messagesGroupSend.setPhonesCount(count);
        return messagesGroupSend;
    }

    // 检查手机号是否在黑名单中
    public boolean isPhoneInBlackList(String phone){
        return blackListService.query().eq("phone_number", phone).one() != null;
    }

    // 删除手机号序列中的黑名单手机号
    public List<String> removeBlackListPhoneReturnList(String mobiles) {
        List<String> resultList = new ArrayList<>();
        List<String> mobileList = Arrays.stream(mobiles.split(",")).map(p -> p.trim()).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(mobileList)) throw new BaseException(PHONES_EMPTY_ERROR.getCode(), PHONES_EMPTY_ERROR.getName());

        for (String mobile : mobileList){
            if (!isPhoneInBlackList(mobile)) {
                resultList.add(mobile);
            }
        }
        return resultList;
    }

    public String removeBlackListPhoneReturnString(String mobiles) {
        List<String> resultList = new ArrayList<>();
        List<String> mobileList = Arrays.stream(mobiles.split(",")).map(p -> p.trim()).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(mobileList)) throw new BaseException(PHONES_EMPTY_ERROR.getCode(), PHONES_EMPTY_ERROR.getName());

        for (String mobile : mobileList){
            if (!isPhoneInBlackList(mobile)) {
                resultList.add(mobile);
            }
        }
        if (resultList.size() == 1){
            return resultList.get(0);
        }
        return StrUtil.join(",", resultList);
    }


    public static boolean isEmpty(String s){
        if (s == null || s.isEmpty()) return true;
        return false;
    }

    public static boolean isNotEmpty(String s){
        if (s == null || s.isEmpty()) return false;
        return true;
    }

    public static String getPercent(int x, int y) {
        double d1 = x * 1.0;
        double d2 = y * 1.0;
        NumberFormat percentInstance = NumberFormat.getPercentInstance();
        // 设置保留几位小数，这里设置的是保留两位小数
        percentInstance.setMinimumFractionDigits(2);
        return percentInstance.format(d1 / d2);
    }

}
