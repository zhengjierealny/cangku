package com.cangku.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cangku.entity.Goods;
import com.cangku.entity.Record;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cangku
 * @since 2023-11-05
 */
public interface RecordService extends IService<Record> {
    IPage pageCC(Page<Record> page, Wrapper wrapper);
}
