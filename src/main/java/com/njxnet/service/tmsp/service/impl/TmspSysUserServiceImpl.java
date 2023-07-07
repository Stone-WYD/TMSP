package com.njxnet.service.tmsp.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njxnet.service.tmsp.common.AjaxResult;
import com.njxnet.service.tmsp.common.AjaxResultUtil;
import com.njxnet.service.tmsp.constants.DelEnum;
import com.njxnet.service.tmsp.constants.FreezeIEnum;
import com.njxnet.service.tmsp.dao.TmspSysUserDao;
import com.njxnet.service.tmsp.entity.TmspSysResource;
import com.njxnet.service.tmsp.entity.TmspSysUser;
import com.njxnet.service.tmsp.model.dto.TmspSysUserDTO;
import com.njxnet.service.tmsp.model.info.ResourceNodeInfo;
import com.njxnet.service.tmsp.model.info.TmspSysUserInfo;
import com.njxnet.service.tmsp.model.query.SysUserQuery;
import com.njxnet.service.tmsp.model.vo.LoginVO;
import com.njxnet.service.tmsp.service.TmspSysResourceService;
import com.njxnet.service.tmsp.service.TmspSysUserService;
import com.njxnet.service.tmsp.utils.BuildUtils;
import com.njxnet.service.tmsp.utils.CommonUtil;
import com.njxnet.service.tmsp.utils.CourtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.njxnet.service.tmsp.common.ResultStatusCode.LOGIN_ERROR;

/**
 * (TmspSysUser)表服务实现类
 *
 * @author Stone
 * @since 2023-06-26 16:08:25
 */
@Slf4j
@Service("tmspSysUserService")
public class TmspSysUserServiceImpl extends ServiceImpl<TmspSysUserDao, TmspSysUser> implements TmspSysUserService {

    @Resource
    private CourtUtil courtUtil;

    @Resource
    private TmspSysResourceService tmspSysResourceService;

    @Override
    public AjaxResult<LoginVO> sysLogin(String username, String password) {
        TmspSysUser user = query().eq("user_name", username).eq("password", password).one();
        // 用户名或密码不正确
        if (user == null)
            return AjaxResultUtil.getBussiseFalseAjaxResult(new AjaxResult<>(), LOGIN_ERROR.getName(), LOGIN_ERROR.getCode());
        LoginVO loginVO = BeanUtil.copyProperties(user, LoginVO.class);
        // 填充法院名
        String courtName = courtUtil.getCourtName(user.getCourtCode());
        if (StrUtil.isNotBlank(courtName)) loginVO.setCourtName(courtName);
        // 获取用户权限列表 全部取出
        List<TmspSysResource> resources = tmspSysResourceService.query().list();
        List<ResourceNodeInfo> list = resources.stream()
                .map((resource -> BeanUtil.copyProperties(resource, ResourceNodeInfo.class)))
                .collect(Collectors.toList());
        List<ResourceNodeInfo> menuList = BuildUtils.buildNode(list);
        loginVO.setList(menuList);
        // 检查是否被冻结
        StpUtil.checkDisable(user.getId());
        // 登录
        StpUtil.login(user.getId());
        // 返回结果
        AjaxResult ajaxResult = new AjaxResult<>();
        AjaxResultUtil.getTrueAjaxResult(ajaxResult);
        ajaxResult.setData(loginVO);
        return ajaxResult;
    }

    @Override
    public AjaxResult freezeUser(Long id) {
        update().set("status", FreezeIEnum.FREEZING.getCode()).eq("id", id).update();
        // 封禁账号
        // 先踢下线
        StpUtil.kickout(id);
        // 再封禁账号
        StpUtil.disable(id, -1);
        // 返回结果
        AjaxResult ajaxResult = new AjaxResult<>();
        return AjaxResultUtil.getTrueAjaxResult(ajaxResult);
    }

    @Override
    public AjaxResult unfreezeUser(Long id) {
        update().set("status", FreezeIEnum.USING.getCode()).eq("id", id).update();
        // 解除封禁
        StpUtil.untieDisable(id);
        // 返回结果
        AjaxResult ajaxResult = new AjaxResult<>();
        return AjaxResultUtil.getTrueAjaxResult(ajaxResult);
    }

    @Override
    public AjaxResult deleteUser(Long id) {
        update().set("del_mark", DelEnum.DEL.getCode()).eq("id", id).update();
        // 返回结果
        AjaxResult ajaxResult = new AjaxResult<>();
        return AjaxResultUtil.getTrueAjaxResult(ajaxResult);
    }

    @Override
    public AjaxResult<Page<TmspSysUserDTO>> queryUsers(SysUserQuery query) {
        AjaxResult<Page<TmspSysUserDTO>> result = new AjaxResult<>();
        // 根据用户名查询用户 分页查询
        String username = query.getUsername();
        Page<TmspSysUser> page = page(new Page<>(query.getPage(), query.getSize()),
                query().eq("del_mark", DelEnum.EXIST.getCode())
                        .like(StrUtil.isNotBlank(username), "user_name", "%" + username + "%")
                        .getWrapper());
        // 为空返回结果
        if (CollectionUtil.isEmpty(page.getRecords())) {
            result.setData( new Page<>(query.getPage(), query.getSize()) );
            return AjaxResultUtil.getTrueAjaxResult(result);
        }
        // 转换为DTO返回结果
        List<TmspSysUserDTO> data = page.getRecords().stream()
                .map(user -> BeanUtil.copyProperties(user, TmspSysUserDTO.class))
                .collect(Collectors.toList());
        for (TmspSysUserDTO dto : data) dto.setCourtName(courtUtil.getCourtName(dto.getCourtCode()));
        // 返回结果
        Page resultPage = BeanUtil.copyProperties(page, Page.class);
        resultPage.setRecords(data);
        result.setData(resultPage);
        return AjaxResultUtil.getTrueAjaxResult(result);
    }

    @Override
    public AjaxResult updateUser(TmspSysUserInfo userInfo) {
        update().set(StrUtil.isNotBlank(userInfo.getUserName()), "user_name", userInfo.getUserName())
                .set(StrUtil.isNotBlank(userInfo.getPassword()), "password", userInfo.getPassword())
                .set(StrUtil.isNotBlank(userInfo.getCourtCode()), "court_code", userInfo.getCourtCode())
                .set("update_time", new Date())
                .eq("id", userInfo.getId())
                .update();
        return AjaxResultUtil.getTrueAjaxResult(new AjaxResult<>());
    }

    @Override
    public AjaxResult insertUser(TmspSysUserInfo userInfo) {
        TmspSysUser user = BeanUtil.copyProperties(userInfo, TmspSysUser.class);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        save(user);
        log.info("新增用户id为：" + user.getId());
        // 返回结果
        return AjaxResultUtil.getTrueAjaxResult(new AjaxResult<>());
    }
}

