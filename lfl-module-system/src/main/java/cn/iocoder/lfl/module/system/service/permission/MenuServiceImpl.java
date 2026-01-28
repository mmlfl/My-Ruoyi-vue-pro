package cn.iocoder.lfl.module.system.service.permission;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.iocoder.lfl.framework.common.util.collection.CollectionUtils;
import cn.iocoder.lfl.framework.common.util.object.BeanUtils;
import cn.iocoder.lfl.module.system.controller.admin.permission.vo.menu.MenuListReqVO;
import cn.iocoder.lfl.module.system.controller.admin.permission.vo.menu.MenuRespVO;
import cn.iocoder.lfl.module.system.controller.admin.permission.vo.menu.MenuSaveReqVO;
import cn.iocoder.lfl.module.system.dal.dataobject.permission.MenuDO;
import cn.iocoder.lfl.module.system.dal.mysql.permission.MenuMapper;
import cn.iocoder.lfl.module.system.dal.redis.RedisKeyConstants;
import cn.iocoder.lfl.module.system.enums.permission.MenuTypeEnum;
import cn.iocoder.lfl.module.system.enums.social.ErrorCodeConstants;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;

import static cn.iocoder.lfl.module.system.exception.util.ServiceExceptionUtil.exception;

@Service
public class MenuServiceImpl implements MenuService{
    @Resource
    private MenuMapper menuMapper;

    @Override
    public MenuDO getMenuIdByPermission(String permission) {
        return null;
    }

    @Override
    @Cacheable(value = RedisKeyConstants.PERMISSION_MENU_ID_LIST,key = "#permission")
    public List<Long> getMenuIdListByPermissionFromCache(String permission) {
        List<MenuDO> menuDOS = menuMapper.selectListByPermission(permission);
        return CollectionUtils.convertList(menuDOS,MenuDO::getId);
    }

    @Override
    @CacheEvict(value = RedisKeyConstants.PERMISSION_MENU_ID_LIST,key = "#reqVO.permission",
                    condition = "#reqVO.permission != null ")
    public Long createMenu(MenuSaveReqVO reqVO) {
        //校验父菜单
        validateParentMenu(reqVO.getParentId(),null);
        //校验菜单
        validateMenuName(reqVO.getParentId(),reqVO.getName(),null);
        validateComponentName(reqVO.getComponentName(),null);
        //创建菜单
        MenuDO menu = BeanUtils.toBean(reqVO, MenuDO.class);
        initMenuProperty(menu);
        menuMapper.insert(menu);

        return menu.getId();
    }

    @Override
    @CacheEvict(value = RedisKeyConstants.PERMISSION_MENU_ID_LIST,allEntries = true)//更新可能会涉及两个permission,所以直接清空缓存.
    public void updateMenu(MenuSaveReqVO reqVO) {
        //检验更新的菜单是否存在
        if(menuMapper.selectById(reqVO.getId()) == null){
            throw exception(ErrorCodeConstants.MENU_NOT_EXISTS);
        }
        //校验父菜单
        validateParentMenu(reqVO.getParentId(),reqVO.getId());
        //校验菜单
        validateMenuName(reqVO.getParentId(),reqVO.getName(),reqVO.getId());
        validateComponentName(reqVO.getComponentName(),reqVO.getId());
        //更新菜单
        MenuDO menu = BeanUtils.toBean(reqVO, MenuDO.class);
        initMenuProperty(menu);
        menuMapper.updateById(menu);
    }

    @Override
    public void deleteMenu(Long id) {
        if(menuMapper.selectById(id) == null){
            throw exception(ErrorCodeConstants.MENU_NOT_EXISTS);
        }
        menuMapper.deleteById(id);
    }

    @Override
    public void deleteMenuList(List<Long> ids) {
        if(CollUtil.isEmpty(ids)){
            return;
        }
        menuMapper.deleteByIds(ids);
    }

    @Override
    public MenuRespVO getMenu(Long id) {
        MenuDO menuDO = menuMapper.selectById(id);
        if(menuDO == null){
            throw exception(ErrorCodeConstants.MENU_NOT_EXISTS);
        }
        return BeanUtils.toBean(menuDO,MenuRespVO.class);
    }

    @Override
    public List<MenuRespVO> getMenuList(MenuListReqVO reqVO) {
        List<MenuDO> menuDOS = menuMapper.selectList(reqVO);
        menuDOS.sort(Comparator.comparing(MenuDO::getSort));

        return BeanUtils.toBean(menuDOS, MenuRespVO.class);
    }


    private void validateComponentName(String componentName, Long id) {
        if(componentName == null){
            return;
        }
        MenuDO menuDO = menuMapper.selectByComponentName(componentName);
        if(menuDO == null){
            return;
        }
        if(!menuDO.getId().equals(id)){
            throw exception(ErrorCodeConstants.MENU_COMPONENT_NAME_DUPLICATE);
        }
    }

    /**
     * 校验菜单是否合法
     * <p>
     * 1. 校验相同父菜单编号下，是否存在相同的菜单名
     *
     * @param name     菜单名字
     * @param parentId 父菜单编号
     * @param id       菜单编号
     */
    private void validateMenuName(Long parentId,String name, Long id) {
        if(parentId == null){
            return;
        }
        MenuDO menuDO = menuMapper.selectByParentIdAndName(parentId, name);
        if(menuDO == null){
            return;
        }
        if(!menuDO.getId().equals(id)){
            throw exception(ErrorCodeConstants.MENU_NAME_DUPLICATE);
        }
    }

    private void validateParentMenu(Long parentId, Long childId) {
        if(parentId == null || parentId.equals(MenuDO.ID_ROOT)){
            return;
        }
        MenuDO menuDO = menuMapper.selectById(parentId);
        if(menuDO == null){
            throw exception(ErrorCodeConstants.MENU_PARENT_NOT_EXISTS);
        }
        if(childId != null && childId.equals(parentId)){
            throw exception(ErrorCodeConstants.MENU_PARENT_ERROR);
        }
        if(!MenuTypeEnum.DIR.getType().equals(menuDO.getType())
                && !MenuTypeEnum.MENU.getType().equals(menuDO.getType())){
            throw exception(ErrorCodeConstants.MENU_PARENT_NOT_DIR_OR_MENU);
        }
    }

    /**
     * 初始化菜单的通用属性。
     * <p>
     * 例如说，只有目录或者菜单类型的菜单，才设置 icon
     *
     * @param menu 菜单
     */
    private void initMenuProperty(MenuDO menu) {
        if(MenuTypeEnum.BUTTON.getType().equals(menu.getType())){
            menu.setComponent("");
            menu.setComponentName("");
            menu.setIcon("");
            menu.setPath("");
        }
    }


    /**
     * 获取自身的bean对象 使得spring aop可以发挥作用
     */
    private MenuServiceImpl getSelf(){
        return SpringUtil.getBean(getClass());
    }
}
