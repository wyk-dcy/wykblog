package com.wykblog.blog.service;

import com.wykblog.blog.entity.TType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wykblog.blog.utils.RespBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 关注公众号：小L星光
 * @since 2020-11-30
 */
public interface TTypeService extends IService<TType> {

    RespBean pageTypes(Long current, Long size);

    RespBean getTypeByName(String name);

    RespBean deleteTypeById(Long id);

    RespBean updateType(TType tType);

    RespBean getTypeById(Long id);

    RespBean getAllType();


}
