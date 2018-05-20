package com.lingxi.system.controller;

import com.lingxi.framework.domain.ResultMsg;
import com.lingxi.framework.utils.BeanUtils;
import com.lingxi.framework.utils.ResultUtil;
import com.lingxi.system.entity.Permission;
import com.lingxi.system.entity.Role;
import com.lingxi.system.service.PermissionService;
import com.lingxi.system.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/system/permission")
public class PermissionController {

	@Autowired
	private PermissionService permissionService;
	@Autowired
	private RoleService roleService;

	/**
	 * 根据ID获取对象
	 * @param id
	 */
	@RequestMapping(value="/get",method=RequestMethod.POST)
	public ResultMsg get(Long id) {
		Permission entity = permissionService.get(id);
		entity.setRolesSerialize();
		return ResultUtil.success(entity);
	}

	/**
	 * 数据添加入口
	 * @RequestBody 注解为入参实（将传入参数自动注入实体类中）
	 * @param permission
	 */
	@RequestMapping(value="/save",method=RequestMethod.POST)
    public ResultMsg save(Permission permission, Long[] roleIds) {
		List<Role> roles = BeanUtils.newListBean(Role.class,roleIds);
		permission.setRoles(roles);
		return ResultUtil.success(permissionService.save(permission));
    }

	/**
	 * 删除数据，支持多删除
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/deleteByIds",method=RequestMethod.POST)
	public ResultMsg deleteByIds(Long[] ids) {
        permissionService.deleteByIds(ids);
		return ResultUtil.success("删除成功");
	}

	/**
	 * 分页查询
	 * @return
	 */
	@RequestMapping(value="/listPage",method=RequestMethod.POST)
	public ResultMsg listPage(Permission permission, @PageableDefault Pageable pageable){
		return ResultUtil.success(permissionService.findAll(permission,pageable));
	}

}
