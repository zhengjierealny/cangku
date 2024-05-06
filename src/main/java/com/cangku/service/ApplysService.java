package com.cangku.service;

import com.cangku.entity.Applys;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cangku
 * @since 2023-11-13
 */
public interface ApplysService extends IService<Applys> {
    IPage pageCC(Page<Applys> page, Wrapper wrapper);
}
