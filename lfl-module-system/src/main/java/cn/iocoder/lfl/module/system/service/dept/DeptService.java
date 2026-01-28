package cn.iocoder.lfl.module.system.service.dept;

import cn.iocoder.lfl.module.system.controller.admin.dept.vo.dept.DeptListReqVO;
import cn.iocoder.lfl.module.system.controller.admin.dept.vo.dept.DeptRespVO;
import cn.iocoder.lfl.module.system.controller.admin.dept.vo.dept.DeptSaveReqVO;
import cn.iocoder.lfl.module.system.dal.dataobject.dept.DeptDO;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public interface DeptService {
    Long createDept(DeptSaveReqVO reqVO);

    void updateDept(DeptSaveReqVO reqVO);

    void deleteDept(Long id);

    void deleteDeptList(List<Long> ids);

    DeptRespVO getDept(Long id);

    List<DeptDO> getDeptList(DeptListReqVO reqVO);


    Set<Long> getChildDeptIdListFromCache(Long deptId);

    default List<DeptDO> getChildDeptList(Long deptId){
        return getChildDeptList(Collections.singleton(deptId));
    }

    List<DeptDO> getChildDeptList(Collection<Long> deptIds);
}
