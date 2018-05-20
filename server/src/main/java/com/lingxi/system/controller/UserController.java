package com.lingxi.system.controller;

import com.lingxi.framework.domain.ResultMsg;
import com.lingxi.framework.utils.BeanUtils;
import com.lingxi.framework.utils.ResultUtil;
import com.lingxi.system.entity.Role;
import com.lingxi.system.entity.User;
import com.lingxi.system.service.RoleService;
import com.lingxi.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/system/user")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * 根据ID获取对象
	 *
	 * @param id
	 */
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	public ResultMsg get(Long id) {
		User entity = userService.get(id);
		entity.setRolesSerialize();
		return ResultUtil.success(entity);
	}

	/**
	 * 数据添加入口（将传入参数自动注入实体类中）
	 *
	 * @param entity 实体对象
	 * @param entity 角色IDs
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResultMsg save(User entity, Long[] roleIds) {
		List<Role> roles = BeanUtils.newListBean(Role.class,roleIds);
		entity.setRoles(roles);
		return ResultUtil.success(userService.save(entity));
	}

	/**
	 * 删除数据，支持多删除
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/deleteByIds", method = RequestMethod.POST)
	public ResultMsg deleteByIds(Long[] ids) {
		userService.deleteByIds(ids);
		return ResultUtil.success("删除成功");
	}


	/**
	 * 分页查询
	 *
	 * @return
	 */
	@RequestMapping(value = "/listPage", method = RequestMethod.POST)
	public ResultMsg listPage(User user, @PageableDefault Pageable pageable) {
		return ResultUtil.success(userService.getPageList(user, pageable));
	}
}
