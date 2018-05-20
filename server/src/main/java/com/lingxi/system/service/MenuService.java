package com.lingxi.system.service;

import com.lingxi.framework.base.BaseRepository;
import com.lingxi.framework.base.CRUDService;
import com.lingxi.framework.domain.callback.Callback;
import com.lingxi.framework.domain.exception.ServiceException;
import com.lingxi.framework.domain.jpa.SimpleQuery;
import com.lingxi.framework.utils.ArraysUtil;
import com.lingxi.framework.utils.BeanUtils;
import com.lingxi.framework.utils.EmptyUtil;
import com.lingxi.system.dao.MenuDao;
import com.lingxi.system.entity.Menu;
import com.lingxi.system.entity.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MenuService extends CRUDService<Menu,Long> {


    @Autowired
    private MenuDao menuDao;

    @Autowired
    private RoleService roleService;

    @Autowired
    public BaseRepository<Menu, Long> getBaseDao() {
        return menuDao;
    }


    @Override
    public Menu save(Menu entity) {

        if(EmptyUtil.isNotEmpty(entity.getPid())){ //修改父级节点
            Menu parent = get(entity.getPid());
            parent.setIsLeaf(false);
            this.menuDao.save(parent);
            entity.setLevel(parent.getLevel()+1);
        }else {
            entity.setLevel(1);
        }
        if(entity.getId() == null){//是否新增
            entity.setIsLeaf(true);
        }
        return super.save(entity);
    }


    @Override
    public void deleteByIds(Long... ids) {


        //設置查詢條件
        List<Menu> menus =  BeanUtils.newListBean(Menu.class,ids);
        Role role = new Role();
        role.setMenus(menus);
        List<Role> roles = roleService.findAll(role);

        if(EmptyUtil.isNotEmpty(roles)){
            throw new ServiceException("不能进行关联删除(错误代码:001)");
        }

        super.deleteByIds(ids);
    }

    @Override
    public List<Menu> findAll(Menu entity) {
        return menuDao.findAll((Root<Menu> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb)->
             new SimpleQuery(cb,root)
                .eq("disabled",entity.getDisabled())
                .eq("pid",entity.getPid())
                .in("roles.id", ArraysUtil.map(entity.getRoles(), (Role role) -> role.getId()))
                .toPredicate()
        ,new Sort(new Sort.Order(Sort.Direction.DESC,"sort")));
    }

    public List<Menu> getTopMenu() {
        return getTopMenu(null);
    }

    public List<Menu> getTopMenu(List<Role> roles) {
        if(roles != null && roles.size() == 0) return new ArrayList<>();
        List<Menu> menus =  menuDao.findAll((Root<Menu> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb)->
                    new SimpleQuery(cb,root)
                            .isNull("pid")
                            .eq("disabled",false)
                            .in("roles.id",ArraysUtil.map(roles, (Role role) -> role.getId()))
                            .toPredicate()
            ,new Sort(new Sort.Order(Sort.Direction.DESC,"sort")));


        return  new Callback<List<Menu>, List<Menu>>() {
            @Override
            public List<Menu> call(List<Menu> subMenus) {
                for(Menu menu : subMenus){
                    Menu query = new Menu();
                    query.setPid(menu.getId());
                    query.setRoles(roles);
                    if(menu.getIsLeaf()==null || !menu.getIsLeaf()){
                        List<Menu> children = findAll(query);
                        menu.setChildren(children);
                        this.call(children);
                    }
                }
                return menus;
            }
        }.call(menus);
    }

    private List<Menu> foo(List<Menu> menus){
        for(Menu menu : menus){
            Menu query = new Menu();
            query.setPid(menu.getId());
            menu.setChildren(this.findAll(query));
            foo(menu.getChildren());
        }
        return menus;
    }


    public List<Menu> getByRoles(List<Role> roles){
        Menu menu = new Menu();
        menu.setRoles(roles);
        return this.findAll(menu);
    }

}
