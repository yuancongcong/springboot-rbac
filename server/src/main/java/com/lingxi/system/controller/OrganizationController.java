package com.lingxi.system.controller;

import com.lingxi.framework.domain.ResultMsg;
import com.lingxi.framework.utils.ResultUtil;
import com.lingxi.system.entity.Organization;
import com.lingxi.system.service.OrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping(value = "/system/organization")
public class OrganizationController {

	@Autowired
	private OrganizationService organizationService;

	/**
	 * 根据ID获取对象
	 * @param id
	 */
	@RequestMapping(value="/get",method=RequestMethod.POST)
	public ResultMsg get(Long id) {
		return ResultUtil.success(organizationService.get(id));
	}

	/**
	 * 数据添加入口
	 * @RequestBody 注解为入参实（将传入参数自动注入实体类中）
	 * @param organization
	 */
	@RequestMapping(value="/save",method=RequestMethod.POST)
    public ResultMsg save(Organization organization) {
		return ResultUtil.success(organizationService.save(organization));
    }

	/**
	 * 删除数据，支持多删除
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/deleteByIds",method=RequestMethod.POST)
	public ResultMsg deleteByIds(Long[] ids) {
        organizationService.deleteByIds(ids);
		return ResultUtil.success("删除成功");
	}

	/**
	 * 分页查询
	 * @return
	 */
	@RequestMapping(value="/listPage",method=RequestMethod.POST)
	public ResultMsg listPage(Organization organization, @PageableDefault Pageable pageable){
		return ResultUtil.success(organizationService.findAll(organization,pageable));
	}
}
