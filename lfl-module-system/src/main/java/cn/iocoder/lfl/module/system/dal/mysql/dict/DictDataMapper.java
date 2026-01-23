package cn.iocoder.lfl.module.system.dal.mysql.dict;

import cn.iocoder.lfl.framework.common.pojo.PageResult;
import cn.iocoder.lfl.framework.mybatis.core.mybatis.BaseMapperX;
import cn.iocoder.lfl.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.lfl.module.system.controller.admin.dict.vo.dictData.DictDataPageReqVO;
import cn.iocoder.lfl.module.system.dal.dataobject.dict.DictDataDO;

public interface DictDataMapper extends BaseMapperX<DictDataDO> {
    default DictDataDO selectByDictTypeAndValue(String dictType, String value){
        return selectOne(DictDataDO::getDictType, dictType, DictDataDO::getValue, value);
    }

    default DictDataDO selectByDictTypeAndLabel(String dictType, String label) {
        return selectOne(DictDataDO::getDictType, dictType, DictDataDO::getLabel, label);
    }

    default PageResult<DictDataDO> selectPage(DictDataPageReqVO pageVO) {
        return selectPage(pageVO,new LambdaQueryWrapperX<DictDataDO>()
                .likeIfPresent(DictDataDO::getLabel, pageVO.getLabel())
                .likeIfPresent(DictDataDO::getDictType, pageVO.getDictType())
                .eqIfPresent(DictDataDO::getStatus, pageVO.getStatus())
        );
    }
}
