package com.cangku.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cangku.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cangku.entity.Goodstype;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cangku
 * @since 2023-11-05
 */
public interface GoodsService extends IService<Goods> {
    IPage pageCC(Page<Goods> page, Wrapper wrapper);
}
