package com.cangku.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cangku.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cangku
 * @since 2023-10-15
 */
@Service
public interface UserService extends IService<User> {

    IPage pageC(IPage<User> page);

    IPage pageCC(Page<User> page, Wrapper wrapper);
}
