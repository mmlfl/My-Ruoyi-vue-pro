package cn.iocoder.lfl.module.system.service.dept;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.lfl.framework.common.util.collection.CollectionUtils;
import cn.iocoder.lfl.framework.common.util.object.BeanUtils;
import cn.iocoder.lfl.module.system.controller.admin.dept.vo.dept.DeptListReqVO;
import cn.iocoder.lfl.module.system.controller.admin.dept.vo.dept.DeptRespVO;
import cn.iocoder.lfl.module.system.controller.admin.dept.vo.dept.DeptSaveReqVO;
import cn.iocoder.lfl.module.system.dal.dataobject.dept.DeptDO;
import cn.iocoder.lfl.module.system.dal.mysql.dept.DeptMapper;
import cn.iocoder.lfl.module.system.dal.redis.RedisKeyConstants;
import cn.iocoder.lfl.module.system.enums.social.ErrorCodeConstants;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

import static cn.iocoder.lfl.module.system.enums.social.ErrorCodeConstants.DEPT_EXITS_CHILDREN;
import static cn.iocoder.lfl.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service
public class DeptServiceImpl implements DeptService{
    @Resource
    private DeptMapper deptMapper;

    @Override
    public Long createDept(DeptSaveReqVO reqVO) {
        if(reqVO.getParentId() == null){
            reqVO.setParentId(DeptDO.PARENT_ROOT_ID);
        }
        //1.校验父部门的有效性
        validateParentDept(reqVO.getParentId(),null);
        //2.校验部门名称是否唯一 注意部门名只是在同一个父部门下唯一
        validateNameUnique(reqVO.getParentId(),reqVO.getName(),null);

        //3.插入部门数据
        DeptDO dept = BeanUtils.toBean(reqVO, DeptDO.class);
        deptMapper.insert(dept);
        return dept.getId();
    }

    @Override
    public void updateDept(DeptSaveReqVO reqVO) {
        if(reqVO.getParentId() == null){
            reqVO.setParentId(DeptDO.PARENT_ROOT_ID);
        }
        //1.校验父部门的有效性
        validateParentDept(reqVO.getParentId(),reqVO.getId());
        //2.校验部门名称是否唯一 注意部门名只是在同一个父部门下唯一
        validateNameUnique(reqVO.getParentId(),reqVO.getName(),reqVO.getId());

        //3.更新部门数据
        DeptDO dept = BeanUtils.toBean(reqVO, DeptDO.class);
        deptMapper.updateById(dept);
    }

    @Override
    public void deleteDept(Long id) {
        validateCanRemoveDept(id);
        deptMapper.deleteById(id);
    }

    @Override
    public void deleteDeptList(List<Long> ids) {
        // 校验是否有子部门
        for (Long id : ids) {
            if (deptMapper.selectCountByParentId(id) > 0) {
                throw exception(DEPT_EXITS_CHILDREN);
            }
        }

        // 批量删除部门
        deptMapper.deleteByIds(ids);
    }

    @Override
    public DeptRespVO getDept(Long id) {
        DeptDO deptDO = deptMapper.selectById(id);
        if(deptDO == null){
            throw exception(ErrorCodeConstants.DEPT_NOT_FOUND);
        }
        return BeanUtils.toBean(deptDO,DeptRespVO.class);
    }

    @Override
    public List<DeptDO> getDeptList(DeptListReqVO reqVO) {
        List<DeptDO> deptDOS = deptMapper.selectList(reqVO);
        return deptDOS;
    }

    @Override
    @Cacheable(cacheNames = RedisKeyConstants.DEPT_CHILDREN_ID_LIST,key = "#deptId")
    public Set<Long> getChildDeptIdListFromCache(Long deptId) {
        List<DeptDO> childDeptList = getChildDeptList(deptId);
        return CollectionUtils.convertSet(childDeptList,DeptDO::getId);
    }

    @Override
    public List<DeptDO> getChildDeptList(Collection<Long> deptIds) {
        if(CollUtil.isEmpty(deptIds)){
            return Collections.emptyList();
        }
        List<DeptDO> children = new LinkedList<>();
        for (int i = 0; i < Short.MAX_VALUE; i++) {//防止出现bug时死循环
            List<DeptDO> deptDOS = deptMapper.selectListByParentId(deptIds);
            if(CollUtil.isEmpty(deptDOS)){
                break;
            }
            children.addAll(deptDOS);
            deptIds = deptDOS.stream().map(DeptDO::getId).toList();
        }
        return children;
    }


    private void validateCanRemoveDept(Long id) {
        if(deptMapper.selectById(id) == null){
            throw exception(ErrorCodeConstants.DEPT_NOT_FOUND);
        }
        if(deptMapper.selectCountByParentId(id) > 0){
            throw exception(DEPT_EXITS_CHILDREN);
        }
    }

    private void validateNameUnique(Long parentId,String name,Long id) {
        DeptDO deptDO = deptMapper.selectByParentIdAndName(parentId,name);
        if(deptDO == null){
            return;
        }
        if(id == null){
            throw exception(ErrorCodeConstants.DEPT_NAME_DUPLICATE);
        }
        if(ObjectUtil.notEqual(id,deptDO.getId())){
            throw exception(ErrorCodeConstants.DEPT_NAME_DUPLICATE);
        }
    }

    private void validateParentDept(Long parentId, Long childId) {
        if(parentId==null || DeptDO.PARENT_ROOT_ID.equals(parentId)){
            return;
        }
        DeptDO parentDept = deptMapper.selectById(parentId);
        if(parentDept == null){
            throw exception(ErrorCodeConstants.DEPT_PARENT_NOT_EXITS);
        }
        if(Objects.equals(parentDept.getId(),childId)){
           throw exception(ErrorCodeConstants.DEPT_PARENT_ERROR);
        }
        //校验父部门是否是自己的子部门 防止形成死循环
        if(childId == null){
           return;
        }
        for (int i = 0; i < Short.MAX_VALUE; i++) {
            parentId = parentDept.getParentId();
            if(Objects.equals(parentId,childId)){
                throw exception(ErrorCodeConstants.DEPT_PARENT_IS_CHILD);
            }
            if(parentId == null || DeptDO.PARENT_ROOT_ID.equals(parentId)){
                break;
            }
            parentDept = deptMapper.selectById(parentId);
            if(parentDept == null){
                return;
            }
        }
    }
}
