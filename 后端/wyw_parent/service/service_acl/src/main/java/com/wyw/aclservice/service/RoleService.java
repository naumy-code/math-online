package com.wyw.aclservice.service;

import com.wyw.aclservice.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2020-10-12
 */
public interface RoleService extends IService<Role> {

    /**
     * 根据用户获取角色数据
     */
    Map<String, Object> findRoleByUserId(String userId);

    /**
     * 根据用户分配角色
     * @param userId
     * @param roleId
     */
    void saveUserRoleRealtionShip(String userId, String[] roleId);

    /**
     * 通过用户id查询角色
     * @param id
     * @return
     */
    List<Role> selectRoleByUserId(String id);
}
