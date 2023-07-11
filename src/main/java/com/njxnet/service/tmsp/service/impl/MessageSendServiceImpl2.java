package com.njxnet.service.tmsp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.njxnet.service.tmsp.common.AjaxResult;
import com.njxnet.service.tmsp.common.AjaxResultUtil;
import com.njxnet.service.tmsp.common.BaseException;
import com.njxnet.service.tmsp.common.ResultStatusCode;
import com.njxnet.service.tmsp.core.PostContext;
import com.njxnet.service.tmsp.core.PostProcessorContainer;
import com.njxnet.service.tmsp.core.send.SendMessageWay;
import com.njxnet.service.tmsp.core2.send.SendMessageOuterPostProcessor2;
import com.njxnet.service.tmsp.core2.send.SendMessagePostProcessor2;
import com.njxnet.service.tmsp.model.dto.PhoneSendMsgDTO;
import com.njxnet.service.tmsp.model.dto.TmspPhoneSendDTO;
import com.njxnet.service.tmsp.model.info.SendInfo;
import com.njxnet.service.tmsp.service.MessageSendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import static com.njxnet.service.tmsp.common.ResultStatusCode.NO_OUTHANDLER;
import static com.njxnet.service.tmsp.common.ResultStatusCode.SEND_DTO_EMPTY_ERROR;

/**
 * @program: TMSP
 * @description: 使用模板方法实现发送短信需求
 * @author: Stone
 * @create: 2023-07-10 10:44
 **/
@Slf4j
@Service("messageSendServiceImpl2")
@ConditionalOnProperty(name = "config.core.version", havingValue = "2")
public class MessageSendServiceImpl2 implements MessageSendService {

    @Resource
    private ThreadPoolExecutor poolExecutor;

    @Resource
    private SendMessageWay sendMessageWay;

    protected PostContext<SendInfo> context;

    /**
     * @Description: 模板方法，具体实现在子类中
     * @Author: Stone
     * @Date: 2023/7/10
     */
    @Override
    public AjaxResult messageSend(TmspPhoneSendDTO dto) {
        PostProcessorContainer outPostProcessorContainer = PostProcessorContainer.getInstance(SendMessageOuterPostProcessor2.class);
        PostProcessorContainer postProcessorContainer = PostProcessorContainer.getInstance(SendMessagePostProcessor2.class);
        SendInfo sendInfo = BeanUtil.copyProperties(dto, SendInfo.class);
        // 转换得到mobileList
        mobileListFill(sendInfo);
        context = new PostContext(sendInfo);

        // 1.发送前的操作（黑名单校验等）
        if (outPostProcessorContainer.handleBefore(context)) {
            // 没有进行发送前的操作，则直接返回空结果
            return AjaxResultUtil.getBussiseFalseAjaxResult(new AjaxResult<>(), NO_OUTHANDLER.getName(), NO_OUTHANDLER.getCode());
        }
        // 2. 保存发送记录到数据库
        dataRecordSave();
        // 3.异步操作
        poolExecutor.submit(() -> {
            // 3.1.发送短信前的操作
            postProcessorContainer.handleBefore(context);
            // 3.2.参数准备
            paramPostProcessor();
            // 3.4.发送短信
            theMessageSend();
            // 3.5.解析结果，更新记录
            messageAnalysis();
            // 3.6.发送短信后的操作
            postProcessorContainer.handleAfter(context);
        });

        // 4.发送后的操作（数据库）
        outPostProcessorContainer.handleAfter(context);

        // 5.返回结果
        return AjaxResultUtil.getTrueAjaxResult(new AjaxResult<>());
    }

    protected void dataRecordSave() {

    }

    /**
     * @Description: 参数准备
     * @Author: Stone
     * @Date: 2023/7/10
     */
    protected void paramPostProcessor() {
        SendInfo sendInfo = context.getT();
        // 准备参数给调用组件调用
        PhoneSendMsgDTO dto = BeanUtil.copyProperties(sendInfo, PhoneSendMsgDTO.class);
        List<PhoneSendMsgDTO> dtoList = new ArrayList<>();
        dtoList.add(dto);
        // 准备群发参数
        List<String> mobileList = sendInfo.getMobileList();
        if (CollectionUtil.isNotEmpty(mobileList)) {
            for (String mobile : mobileList) {
                // 克隆出只有手机号不同的传参
                PhoneSendMsgDTO clone;
                try {
                    clone = (PhoneSendMsgDTO) dto.clone();
                } catch (CloneNotSupportedException e) {
                    log.info(e.getMessage());
                    throw new BaseException(ResultStatusCode.FAIL.getCode(), ResultStatusCode.FAIL.getName());
                }
                clone.setMobile(mobile);
                dtoList.add(clone);
            }
        }
        sendInfo.setPhoneSendMsgDTOList(dtoList);
    }

    /**
    * @Description: 短信发送
    * @Author: Stone
    * @Date: 2023/7/11
    */
    protected void theMessageSend() {
        SendInfo sendInfo = context.getT();
        List<PhoneSendMsgDTO> phoneSendMsgDTOList = sendInfo.getPhoneSendMsgDTOList();
        // 调用接口
        if (CollectionUtil.isEmpty(phoneSendMsgDTOList)) {
            throw new BaseException(SEND_DTO_EMPTY_ERROR.getCode(), SEND_DTO_EMPTY_ERROR.getName());
        }
        sendMessageWay.sendMessage(sendInfo, phoneSendMsgDTOList);
    }

    /**
    * @Description: 结果分析
    * @Author: Stone
    * @Date: 2023/7/11
    */
    protected void messageAnalysis() {

    }

    /**
    * @Description: 手机号List获取
    * @Author: Stone
    * @Date: 2023/7/11
    */
    private void mobileListFill(SendInfo sendInfo) {
        String mobiles = sendInfo.getMobiles();
        if (StrUtil.isNotBlank(mobiles)) {
            List<String> mobileList = new ArrayList<>();
            // 如果只有一条手机号
            if (!mobiles.contains(",")) {
                mobileList.add(mobiles);
            } else {
                mobileList = StrUtil.split(mobiles, ",");
            }
            sendInfo.setMobileList(mobileList);
        }
    }
}
