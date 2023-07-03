package com.njxnet.service.tmsp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.njxnet.service.tmsp.common.AjaxResult;
import com.njxnet.service.tmsp.entity.TmspSysUser;
import com.njxnet.service.tmsp.model.dto.TmspSysUserDTO;
import com.njxnet.service.tmsp.model.info.TmspSysUserInfo;
import com.njxnet.service.tmsp.model.query.SysUserQuery;
import com.njxnet.service.tmsp.model.vo.LoginVO;

/**
 * (TmspSysUser)表服务接口
 *
 * @author Stone
 * @since 2023-06-26 16:08:24
 */
public interface TmspSysUserService extends IService<TmspSysUser> {

    AjaxResult<LoginVO> sysLogin(String username, String password);

    AjaxResult freezeUser(Long id);

    AjaxResult unfreezeUser(Long id);

    AjaxResult deleteUser(Long id);

    AjaxResult<Page<TmspSysUserDTO>> queryUsers(SysUserQuery query);

    AjaxResult updateUser(TmspSysUserInfo userDTO);

    AjaxResult insertUser(TmspSysUserInfo user);
}

