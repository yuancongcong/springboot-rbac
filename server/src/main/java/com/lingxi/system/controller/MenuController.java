package com.lingxi.system.controller;

import com.lingxi.framework.domain.ResultMsg;
import com.lingxi.framework.utils.EmptyUtil;
import com.lingxi.framework.utils.ResultUtil;
import com.lingxi.system.entity.Menu;
import com.lingxi.system.entity.Role;
import com.lingxi.system.service.MenuService;
import com.lingxi.system.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/system/menu")
public class MenuController{

	@Autowired
	private MenuService menuService;
	@Autowired
	private RoleService roleService;

	/**
	 * 根据ID获取对象
	 * @param id
	 */
	@RequestMapping(value="/get",method=RequestMethod.POST)
	public ResultMsg get(Long id) {
		return ResultUtil.success(menuService.get(id));
	}

	/**
	 * 数据添加入口
	 * @RequestBody 注解为入参实（将传入参数自动注入实体类中）
	 * @param entity
	 */
	@RequestMapping(value="/save",method=RequestMethod.POST)
    public ResultMsg save(Menu entity) {
		return ResultUtil.success(menuService.save(entity));
    }

	/**
	 * 删除数据，支持多删除
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/deleteByIds",method=RequestMethod.POST)
	public ResultMsg deleteByIds(Long[] ids) {
		menuService.deleteByIds(ids);
		return ResultUtil.success("删除成功");
	}

	/**
	 * 获取列表
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	public ResultMsg list(HttpServletRequest request){
		List<Menu> result = null;
		//管理员查询所有菜单，其他角色查询当前菜单
		if(request.isUserInRole("ROLE_ADMIN")){
			result =  menuService.getTopMenu();
		}else{
			//获取当前用户角色
			List<Role> roles = roleService.findByUserName(request.getRemoteUser());
			result = menuService.getTopMenu(roles);
		}
		return ResultUtil.success(result);
	}
}
