package com.njxnet.service.tmsp.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njxnet.framework.common.model.AjaxResult;
import com.njxnet.service.tmsp.model.dto.TmspSysUserDTO;
import com.njxnet.service.tmsp.model.info.TmspSysUserInfo;
import com.njxnet.service.tmsp.model.query.SysUserQuery;
import com.njxnet.service.tmsp.service.TmspSysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping(value = "/users/manage")
@RestController
@Api(value = "用户管理", protocols = "http/https", tags = "用户管理")
@Slf4j
public class SysUserController {

    @Resource
    private TmspSysUserService sysUserService;

    // 新增用户
    @ApiOperation(value = "新增用户", notes = "新增用户")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public AjaxResult insertUser(@RequestBody @Validated(value = TmspSysUserInfo.insert.class) TmspSysUserInfo user) {
        return sysUserService.insertUser(user);
    }

    // 查询用户
    @ApiOperation(value = "查询用户", notes = "查询用户")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public AjaxResult<Page<TmspSysUserDTO>> queryUsers(@RequestBody @Validated SysUserQuery query) {
        return sysUserService.queryUsers(query);
    }

    // 编辑用户
    @ApiOperation(value = "编辑用户", notes = "编辑用户")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public AjaxResult updateUser(@RequestBody @Validated(value = TmspSysUserInfo.update.class) TmspSysUserInfo userInfo) {
        return sysUserService.updateUser(userInfo);
    }

    // 冻结用户
    @ApiOperation(value = "冻结用户", notes = "冻结用户")
    @RequestMapping(value = "/freeze", method = RequestMethod.POST)
    public AjaxResult freezeUser(@RequestBody @Validated(value = TmspSysUserInfo.update.class) TmspSysUserInfo userInfo) {
        return sysUserService.freezeUser(userInfo.getId());
    }

    // 解除封禁
    @ApiOperation(value = "解冻用户", notes = "解冻用户")
    @RequestMapping(value = "/unfreeze", method = RequestMethod.POST)
    public AjaxResult unfreezeUser(@RequestBody @Validated(value = TmspSysUserInfo.update.class) TmspSysUserInfo userInfo) {
        return sysUserService.unfreezeUser(userInfo.getId());
    }

    // 删除用户
    @ApiOperation(value = "删除用户", notes = "删除用户")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public AjaxResult deleteUser(@RequestBody @Validated(value = TmspSysUserInfo.update.class) TmspSysUserInfo userInfo) {
        return sysUserService.deleteUser(userInfo.getId());
    }
}
