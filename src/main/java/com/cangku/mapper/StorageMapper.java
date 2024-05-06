package com.cangku.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cangku.entity.Storage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cangku.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cangku
 * @since 2023-11-04
 */
@Mapper
public interface StorageMapper extends BaseMapper<Storage> {
    IPage pageCC(Page<Storage> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
