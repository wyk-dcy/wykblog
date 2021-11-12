package com.wykblog.blog.mapper;

import com.wykblog.blog.entity.TType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 关注公众号：小L星光
 * @since 2020-11-30
 */
public interface TTypeMapper extends BaseMapper<TType> {

    List<TType> getAllType();

    List<TType> getAll();
}
