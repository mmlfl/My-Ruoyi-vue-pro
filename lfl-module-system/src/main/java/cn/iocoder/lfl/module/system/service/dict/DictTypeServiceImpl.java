package cn.iocoder.lfl.module.system.service.dict;

import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.lfl.framework.common.pojo.PageResult;
import cn.iocoder.lfl.framework.common.util.object.BeanUtils;
import cn.iocoder.lfl.module.system.controller.admin.dict.vo.dictType.DictTypePageReqVO;
import cn.iocoder.lfl.module.system.controller.admin.dict.vo.dictType.DictTypeRespVO;
import cn.iocoder.lfl.module.system.controller.admin.dict.vo.dictType.DictTypeSaveReqVO;
import cn.iocoder.lfl.module.system.controller.admin.dict.vo.dictType.DictTypeSimpleRespVO;
import cn.iocoder.lfl.module.system.dal.dataobject.dict.DictTypeDO;
import cn.iocoder.lfl.module.system.dal.mysql.dict.DictTypeMapper;
import cn.iocoder.lfl.module.system.enums.social.ErrorCodeConstants;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.lfl.module.system.exception.util.ServiceExceptionUtil.exception;

@Service
public class DictTypeServiceImpl implements DictTypeService{
    @Resource
    private DictTypeMapper dictTypeMapper;

    @Override
    public Long createDictType(DictTypeSaveReqVO reqVO) {
        //1.校验参数
        validateDictTypeForCreateOrUpdate(null,reqVO);
        //插入数据
        DictTypeDO dictTypeDO = BeanUtils.toBean(reqVO, DictTypeDO.class);
        dictTypeMapper.insert(dictTypeDO);
        return dictTypeDO.getId();
    }

    @Override
    public void updateDictType(DictTypeSaveReqVO reqVO) {
        //校验参数
        validateDictTypeForCreateOrUpdate(reqVO.getId(),reqVO);
        //更新数据
        DictTypeDO dictTypeDO = BeanUtils.toBean(reqVO, DictTypeDO.class);
        dictTypeMapper.updateById(dictTypeDO);
    }

    @Override
    public void deleteDictType(Long id) {
        dictTypeMapper.deleteById(id);
    }

    @Override
    public void deleteDictTypeList(List<Long> ids) {
        dictTypeMapper.deleteByIds(ids);
    }

    @Override
    public List<DictTypeSimpleRespVO> getSimpleDictTypeList() {
        List<DictTypeDO> dictTypeDOS = dictTypeMapper.selectList();
        return BeanUtils.toBean(dictTypeDOS,DictTypeSimpleRespVO.class);
    }

    @Override
    public PageResult<DictTypeDO> getDictTypePage(DictTypePageReqVO pageVO) {
        return dictTypeMapper.selectPage(pageVO);
    }

    @Override
    public DictTypeRespVO getDictType(Long id) {
        //1.校验字典类型是否存在
        DictTypeDO dictTypeDO = validateDictTypeExists(id);
        return BeanUtils.toBean(dictTypeDO,DictTypeRespVO.class);
    }

    private void validateDictTypeForCreateOrUpdate(Long id, DictTypeSaveReqVO reqVO) {
        //.校验是否存在
        validateDictTypeExists(id);
        //.校验名称唯一
        validateDictTypeNameUnique(id,reqVO.getName());
        //.校验类型唯一
        validateDictTypeTypeUnique(id,reqVO.getType());
    }

    private void validateDictTypeTypeUnique(Long id,String type) {
        DictTypeDO dictTypeDO = dictTypeMapper.selectByType(type);
        if(dictTypeDO == null){
            return;
        }
        if(id == null){
            throw exception(ErrorCodeConstants.DICT_TYPE_TYPE_DUPLICATE);
        }
        if(ObjectUtil.notEqual(id,dictTypeDO.getId())){
            throw exception(ErrorCodeConstants.DICT_TYPE_TYPE_DUPLICATE);
        }
    }

    private void validateDictTypeNameUnique(Long id,String name) {
        DictTypeDO dictTypeDO = dictTypeMapper.selectByName(name);
        if(dictTypeDO == null){
            return;
        }
        if(id == null){
            throw exception(ErrorCodeConstants.DICT_TYPE_NAME_DUPLICATE);
        }
        if(ObjectUtil.notEqual(id,dictTypeDO.getId())){
            throw exception(ErrorCodeConstants.DICT_TYPE_NAME_DUPLICATE);
        }
    }

    private DictTypeDO validateDictTypeExists(Long id) {
        if(id == null){
            return null;
        }
        DictTypeDO dictType = dictTypeMapper.selectById(id);
        if(dictType == null){
            throw exception(ErrorCodeConstants.DICT_TYPE_NOT_EXISTS);
        }
        return dictType;
    }
}
