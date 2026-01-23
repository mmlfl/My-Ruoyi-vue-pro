package cn.iocoder.lfl.module.system.service.dict;

import cn.iocoder.lfl.framework.common.pojo.PageResult;
import cn.iocoder.lfl.module.system.controller.admin.dict.vo.dictData.DictDataPageReqVO;
import cn.iocoder.lfl.module.system.controller.admin.dict.vo.dictData.DictDataSaveReqVO;
import cn.iocoder.lfl.module.system.dal.dataobject.dict.DictDataDO;

import javax.validation.Valid;
import java.util.List;

public interface DictDataService {
    Long createDictData(DictDataSaveReqVO reqVO);

    void updateDictData(DictDataSaveReqVO reqVO);

    void deleteDictData(Long id);

    void deleteList(List<Long> ids);

    PageResult<DictDataDO> getDictDataPage(DictDataPageReqVO pageVO);

    DictDataDO selectById(Long id);
}
