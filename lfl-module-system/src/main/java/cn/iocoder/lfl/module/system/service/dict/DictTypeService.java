package cn.iocoder.lfl.module.system.service.dict;

import cn.iocoder.lfl.framework.common.pojo.PageResult;
import cn.iocoder.lfl.module.system.controller.admin.dict.vo.dictType.DictTypePageReqVO;
import cn.iocoder.lfl.module.system.controller.admin.dict.vo.dictType.DictTypeRespVO;
import cn.iocoder.lfl.module.system.controller.admin.dict.vo.dictType.DictTypeSaveReqVO;
import cn.iocoder.lfl.module.system.controller.admin.dict.vo.dictType.DictTypeSimpleRespVO;
import cn.iocoder.lfl.module.system.dal.dataobject.dict.DictTypeDO;

import javax.validation.Valid;
import java.util.List;

public interface DictTypeService {
    Long createDictType(DictTypeSaveReqVO reqVO);

    void updateDictType(DictTypeSaveReqVO reqVO);

    void deleteDictType(Long id);

    void deleteDictTypeList(List<Long> ids);

    List<DictTypeSimpleRespVO> getSimpleDictTypeList();

    PageResult<DictTypeDO> getDictTypePage(DictTypePageReqVO pageVO);

    DictTypeRespVO getDictType(Long id);
}
