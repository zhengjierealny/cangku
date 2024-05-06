package com.cangku.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cangku.entity.Storage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cangku.entity.User;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cangku
 * @since 2023-11-04
 */
@Service
public interface StorageService extends IService<Storage> {
    IPage pageCC(Page<Storage> page, Wrapper wrapper);
}
