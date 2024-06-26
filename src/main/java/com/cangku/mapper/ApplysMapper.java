package com.cangku.mapper;

import com.cangku.entity.Applys;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cangku.entity.Record;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cangku
 * @since 2023-11-13
 */
@Mapper
public interface ApplysMapper extends BaseMapper<Applys> {
    IPage pageCC(Page<Applys> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
