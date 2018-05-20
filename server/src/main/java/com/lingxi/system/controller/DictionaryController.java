package com.lingxi.system.controller;

import com.lingxi.framework.domain.ResultMsg;
import com.lingxi.framework.utils.EmptyUtil;
import com.lingxi.framework.utils.ResultUtil;
import com.lingxi.system.entity.Dictionary;
import com.lingxi.system.service.DictionaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping(value = "/system/dict")
public class DictionaryController {

	@Autowired
	private DictionaryService dictionaryService;

	/**
	 * 根据ID获取对象
	 *
	 * @param id
	 */
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	public ResultMsg get(Long id) {
		Dictionary entity = dictionaryService.get(id);
		return ResultUtil.success(entity);
	}

	/**
	 * 数据添加入口（将传入参数自动注入实体类中）
	 *
	 * @param entity 实体对象
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResultMsg save(Dictionary entity) {
		return ResultUtil.success(dictionaryService.save(entity));
	}

	/**
	 * 删除数据，支持多删除
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/deleteByIds", method = RequestMethod.POST)
	public ResultMsg deleteByIds(Long[] ids) {
		dictionaryService.deleteByIds(ids);
		return ResultUtil.success("删除成功");
	}


	/**
	 * 分页查询
	 *
	 * @return
	 */
	@RequestMapping(value = "/listPage", method = RequestMethod.POST)
	public ResultMsg listPage(Dictionary query,@PageableDefault(sort = {"sort","code"},direction = Sort.Direction.DESC) Pageable pageable) {
		return ResultUtil.success(dictionaryService.findAll(query, pageable));
	}

	/**
	 * 查询
	 *
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResultMsg list(Dictionary dictionary,boolean isRepeat) {
		if(EmptyUtil.isNotEmpty(dictionary.getCode())){
			return ResultUtil.success(dictionaryService.findByParentCode(dictionary.getCode(),isRepeat));
		}

		return ResultUtil.success(dictionaryService.findAll(dictionary));
	}
}
