package com.lingxi.system.controller;

import com.lingxi.framework.domain.Page;
import com.lingxi.framework.domain.ResultMsg;
import com.lingxi.framework.domain.jpa.QlQuery;
import com.lingxi.framework.utils.BeanUtils;
import com.lingxi.framework.utils.ResultUtil;
import com.lingxi.system.dao.UserDao;
import com.lingxi.system.entity.Menu;
import com.lingxi.system.entity.Role;
import com.lingxi.system.entity.User;
import com.lingxi.system.service.MenuService;
import com.lingxi.system.service.RoleService;
import com.lingxi.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/system/role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private UserService userService;

	/**
	 * 根据ID获取对象
	 * @param id
	 */
	@RequestMapping(value="/get",method=RequestMethod.POST)
	public ResultMsg<Role> get(Long id) {
		return ResultUtil.success(roleService.get(id));
	}

	/**
	 * 数据添加入口（将传入参数自动注入实体类中）
	 * @param role 实体对象
	 */
	@RequestMapping(value="/save",method=RequestMethod.POST)
    public ResultMsg<Role> save(Role role,Long[] menusIds,Long[] userIds) {
		if(menusIds != null){
			role.setMenus(BeanUtils.newListBean(Menu.class,menusIds));
		}
		if(userIds != null){
			role.setUsers(BeanUtils.newListBean(User.class,userIds));
		}
		return ResultUtil.success(roleService.save(role));
    }

	/**
	 * 删除数据，支持多删除
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/deleteByIds",method=RequestMethod.POST)
	public ResultMsg<String> deleteByIds(Long[] ids) {
		roleService.deleteByIds(ids);
		return ResultUtil.success("删除成功");
	}


	@Autowired
	UserDao userDao;
	/**
	 * 分页查询
	 * @return
	 */
	@RequestMapping(value="/listPage",method=RequestMethod.POST)
	public ResultMsg<Page<Role>> listPage(Role role, @PageableDefault Pageable pageable){
		QlQuery query = new QlQuery().addQl("from User");
		org.springframework.data.domain.Page<User> users = userDao.findAll(query,pageable);
		return ResultUtil.success(roleService.findAll(role,pageable));
	}

	/**
	 * 获取角色下面的菜单
	 * @param role
	 * @return
	 */
	@RequestMapping(value="/getMenus",method=RequestMethod.POST)
	public ResultMsg<List<Menu>> getMenus(Role role) {
		return ResultUtil.success(menuService.getByRoles(Arrays.asList(role)));
	}
	/**
	 * 获取角色下面的成员
	 * @param role
	 * @return
	 */
	@RequestMapping(value="/getUser",method=RequestMethod.POST)
	public ResultMsg<List<User>> getUser(Role role) {
		return ResultUtil.success(userService.findByRoles(Arrays.asList(role)));
	}


	/**
	 * 查询用户信息 默认查询前10条
	 * @return
	 */
	@RequestMapping(value = "/querySearch", method = RequestMethod.POST)
	public ResultMsg<List<User>> querySearch(String search, @PageableDefault Pageable pageable) {
		User user = new User();
		user.setUserName(search);
		return ResultUtil.success(userService.getPageList(user, pageable).getContent());
	}

	/**
	 * 根据用户名查询
	 *
	 * @return
	 */
	@RequestMapping(value = "/getByUserName", method = RequestMethod.POST)
	public ResultMsg<User> getByUserName(String userName) {
		return ResultUtil.success(userService.findByUserName(userName));
	}

	/**
	 * 根据用户名查询角色
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResultMsg<List<Role>> list() {
		return ResultUtil.success(roleService.findAll(new Role()));
	}
}
