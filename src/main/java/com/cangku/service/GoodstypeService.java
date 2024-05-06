package com.cangku.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cangku.entity.Goodstype;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cangku.entity.Storage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cangku
 * @since 2023-11-05
 */
public interface GoodstypeService extends IService<Goodstype> {
    IPage pageCC(Page<Goodstype> page, Wrapper wrapper);
}
