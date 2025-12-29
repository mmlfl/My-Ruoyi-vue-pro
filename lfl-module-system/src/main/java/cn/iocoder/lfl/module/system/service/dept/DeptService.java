package cn.iocoder.lfl.module.system.service.dept;

import cn.iocoder.lfl.module.system.controller.dept.vo.DeptListReqVO;
import cn.iocoder.lfl.module.system.controller.dept.vo.DeptRespVO;
import cn.iocoder.lfl.module.system.controller.dept.vo.DeptSaveReqVO;
import cn.iocoder.lfl.module.system.dal.dataobject.dept.DeptDO;

import javax.validation.Valid;
import java.util.List;

public interface DeptService {
    Long createDept(DeptSaveReqVO reqVO);

    void updateDept(DeptSaveReqVO reqVO);

    void deleteDept(Long id);

    void deleteDeptList(List<Long> ids);

    DeptRespVO getDept(Long id);

    List<DeptDO> getDeptList(DeptListReqVO reqVO);
}
