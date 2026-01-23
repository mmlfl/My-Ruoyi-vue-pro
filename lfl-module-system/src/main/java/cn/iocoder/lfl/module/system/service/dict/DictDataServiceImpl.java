package cn.iocoder.lfl.module.system.service.dict;

import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.lfl.framework.common.enums.CommonStatusEnum;
import cn.iocoder.lfl.framework.common.pojo.PageResult;
import cn.iocoder.lfl.framework.common.util.object.BeanUtils;
import cn.iocoder.lfl.module.system.controller.admin.dict.vo.dictData.DictDataPageReqVO;
import cn.iocoder.lfl.module.system.controller.admin.dict.vo.dictData.DictDataSaveReqVO;
import cn.iocoder.lfl.module.system.dal.dataobject.dict.DictDataDO;
import cn.iocoder.lfl.module.system.dal.dataobject.dict.DictTypeDO;
import cn.iocoder.lfl.module.system.dal.mysql.dict.DictDataMapper;
import cn.iocoder.lfl.module.system.dal.mysql.dict.DictTypeMapper;
import cn.iocoder.lfl.module.system.enums.social.ErrorCodeConstants;
import cn.iocoder.lfl.module.system.exception.util.ServiceExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

import static cn.iocoder.lfl.module.system.exception.util.ServiceExceptionUtil.exception;

@Service
public class DictDataServiceImpl implements DictDataService{
    @Resource
    private DictTypeMapper dictTypeMapper;
    @Autowired
    private DictDataMapper dictDataMapper;

    @Override
    public Long createDictData(DictDataSaveReqVO reqVO) {
        //1.检验字典类型是否存在
        validateDictTypeExists(reqVO.getDictType());
        //2.检验字典数据键值是否唯一
        validateDictDataValueUnique(null,reqVO.getDictType(),reqVO.getValue());
        //3.检验字典数据标签是否唯一
        validateDictDataLabelUnique(null,reqVO.getDictType(),reqVO.getLabel());
        DictDataDO dictData = BeanUtils.toBean(reqVO, DictDataDO.class);
        dictDataMapper.insert(dictData);
        return dictData.getId();
    }

    @Override
    public void updateDictData(DictDataSaveReqVO reqVO) {
        //1.校验自己是否存在
        validateDictDataExsists(reqVO.getId());
        //已经是否有标签重复
        validateDictDataValueUnique(reqVO.getId(),reqVO.getDictType(),reqVO.getValue());
        //已经是否有值重复
        validateDictDataLabelUnique(reqVO.getId(),reqVO.getDictType(),reqVO.getLabel());
        DictDataDO dictData = BeanUtils.toBean(reqVO, DictDataDO.class);
        dictDataMapper.updateById(dictData);
    }

    @Override
    public void deleteDictData(Long id) {
        dictDataMapper.deleteById(id);
    }

    @Override
    public void deleteList(List<Long> ids) {
        dictDataMapper.deleteByIds(ids);
    }

    @Override
    public PageResult<DictDataDO> getDictDataPage(DictDataPageReqVO pageVO) {
        return dictDataMapper.selectPage(pageVO);
    }

    @Override
    public DictDataDO selectById(Long id) {
        return dictDataMapper.selectById(id);
    }

    private void validateDictDataLabelUnique(Long id, String dictType, String label) {
        DictDataDO dictDataDO = dictDataMapper.selectByDictTypeAndLabel(dictType, label);
        if(dictDataDO == null){
            return;
        }
        if(id == null){
            throw exception(ErrorCodeConstants.DICT_DATA_LABEL_DUPLICATE);
        }
        if(ObjectUtil.notEqual(id,dictDataDO.getId())){
            throw exception(ErrorCodeConstants.DICT_DATA_LABEL_DUPLICATE);
        }
    }

    private void validateDictDataExsists(Long id) {
        if(id == null){
            return;
        }
        DictDataDO dictDataDO = dictDataMapper.selectById(id);
        if(dictDataDO == null){
            throw exception(ErrorCodeConstants.DICT_DATA_NOT_EXISTS);
        }
    }

    private void validateDictDataValueUnique(Long id, String dictType, String value) {
        DictDataDO dictDataDO = dictDataMapper.selectByDictTypeAndValue(dictType, value);
        if(dictDataDO == null){
            return;
        }
        if(id == null){
            throw exception(ErrorCodeConstants.DICT_DATA_VALUE_DUPLICATE);
        }
        if(ObjectUtil.notEqual(id,dictDataDO.getId())){
            throw exception(ErrorCodeConstants.DICT_DATA_VALUE_DUPLICATE);
        }
    }

    private void validateDictTypeExists(String dictType) {
        DictTypeDO dictTypeDO = dictTypeMapper.selectByType(dictType);
        if(dictTypeDO == null){
            throw exception(ErrorCodeConstants.DICT_TYPE_NOT_EXISTS);
        }
        if(!CommonStatusEnum.ENABLE.getStatus().equals(dictTypeDO.getStatus())){
            throw exception(ErrorCodeConstants.DICT_TYPE_NOT_ENABLE);
        }
    }

}
