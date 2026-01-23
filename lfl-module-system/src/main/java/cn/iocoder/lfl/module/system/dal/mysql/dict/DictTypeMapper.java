package cn.iocoder.lfl.module.system.dal.mysql.dict;

import cn.iocoder.lfl.framework.common.pojo.PageResult;
import cn.iocoder.lfl.framework.mybatis.core.mybatis.BaseMapperX;
import cn.iocoder.lfl.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.lfl.module.system.controller.admin.dict.vo.dictType.DictTypePageReqVO;
import cn.iocoder.lfl.module.system.dal.dataobject.dict.DictTypeDO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public interface DictTypeMapper extends BaseMapperX<DictTypeDO> {
    default DictTypeDO selectByName(String name) {
        return selectOne(DictTypeDO::getName,name);
    }

    default DictTypeDO selectByType(String type) {
        return selectOne(DictTypeDO::getType,type);
    }

    default PageResult<DictTypeDO> selectPage(DictTypePageReqVO pageVO) {
        return selectPage(pageVO,new LambdaQueryWrapperX<DictTypeDO>()
                .likeIfPresent(DictTypeDO::getName,pageVO.getName())
                .likeIfPresent(DictTypeDO::getType,pageVO.getType())
                .eqIfPresent(DictTypeDO::getStatus,pageVO.getStatus())
                .betweenIfPresent(DictTypeDO::getCreateTime,pageVO.getCreateTimes())
        );
    }
}
