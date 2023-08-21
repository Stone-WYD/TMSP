package com.njxnet.service.tmsp.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.github.cage.Cage;
import com.github.cage.IGenerator;
import com.github.cage.image.ConstantColorGenerator;
import com.github.cage.image.Painter;
import com.njxnet.service.tmsp.common.AjaxResult;
import com.njxnet.service.tmsp.common.AjaxResultUtil;
import com.njxnet.service.tmsp.common.BaseException;
import com.njxnet.service.tmsp.entity.TmspSysUser;
import com.njxnet.service.tmsp.common.ResultStatusCode;
import com.njxnet.service.tmsp.model.CaptchaModel;
import com.njxnet.service.tmsp.model.dto.CaptchaDTO;
import com.njxnet.service.tmsp.model.query.AdminLoginQuery;
import com.njxnet.service.tmsp.model.vo.LoginVO;
import com.njxnet.service.tmsp.service.TmspSysUserService;
import com.njxnet.service.tmsp.util.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping(value = "/manager")
@RestController
@Api(value = "登录", protocols = "http/https", tags = "登录")
@Slf4j
public class LoginController {

    @Resource
    private TmspSysUserService sysUserService;

    // 将冻结的账号封禁
    @PostConstruct
    public void init(){
        List<TmspSysUser> list = sysUserService.query().eq("status", 0).list();
        if (CollectionUtil.isNotEmpty(list)) {
            list.forEach(user -> StpUtil.disable(user.getId(), -1));
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "管理员用户登录", notes = "管理员用户登录")
    public AjaxResult<LoginVO> login(HttpServletRequest request, @RequestBody @Validated AdminLoginQuery param) throws BaseException {
        Map<String, CaptchaModel> capMap = Constants.capMap;
        CaptchaModel cModel = capMap.get(param.getCaptchaKey());
        if (null == cModel || !param.getCaptchaValue().equalsIgnoreCase(cModel.getText())) {
            throw new BaseException(ResultStatusCode.CAPTCHA_ERROR.getCode(), ResultStatusCode.CAPTCHA_ERROR.getName());
        }
        return sysUserService.sysLogin(param.getUsername(), param.getPassword());
    }

    @ApiOperation(value = "获取验证码", notes = "获取验证码")
    @RequestMapping(value = "/getCaptcha", method = RequestMethod.POST)
    public AjaxResult<CaptchaDTO> getCaptcha() {
        AjaxResult<CaptchaDTO> ajaxResult = new AjaxResult<>();
        IGenerator<Color> ig = new ConstantColorGenerator(Color.BLACK);
        Painter painter = new Painter(115, 38, null, null, null, null);
        Cage cage = new Cage(painter, null, ig, null, null, null, null);
        //获取验证码字符串
        String text = cage.getTokenGenerator().next().toUpperCase().substring(0, 4);
        //将用户名+时间戳生成唯一标识
        String kapKey = UUID.randomUUID().toString().replace("-", "");
        Map<String, CaptchaModel> capMap = Constants.capMap;
        CaptchaModel captchaModel = new CaptchaModel(kapKey, text, System.currentTimeMillis()+Constants.CAP_TIMEOUT);
        capMap.put(kapKey, captchaModel);
        //生成图片
        ByteArrayOutputStream outputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            cage.draw(text, outputStream);
            BASE64Encoder encoder = new BASE64Encoder();
            String base64 = encoder.encode(outputStream.toByteArray());
            String captchaBase64 = "data:image/jpeg;base64," + base64.replaceAll("\r\n", "");
            CaptchaDTO captchaDTO = new CaptchaDTO();
            captchaDTO.setImg(captchaBase64);
            captchaDTO.setKey(kapKey);
            AjaxResultUtil.getTrueAjaxResult(ajaxResult);
            ajaxResult.setData(captchaDTO);
            log.info("请求的验证码为：{}:{}", kapKey, text);
        } catch (IOException e) {
            log.error("获取验证码异常：{}", e.getMessage());
            AjaxResultUtil.getFalseAjaxResult(ajaxResult);
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                log.error("获取验证码异常：{}", e.getMessage());
                AjaxResultUtil.getFalseAjaxResult(ajaxResult);
            }
        }
        return ajaxResult;
    }

    /*@ApiOperation(value = "字符串加密", notes = "字符串加密", hidden = true)
    @RequestMapping(value = "/getEncryption", method = RequestMethod.POST)
    public AjaxResult<String> getEncryption(String param) {
        String decrypt = DESUtil.encrypt(Constants.secretKey, param);
        AjaxResult<String> ajaxResult = new AjaxResult<>();
        AjaxResultUtil.getTrueAjaxResult(ajaxResult);
        ajaxResult.setData(decrypt);
        return ajaxResult;
    }*/
}
