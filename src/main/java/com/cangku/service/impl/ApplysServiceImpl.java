package com.cangku.service.impl;

import com.cangku.entity.Applys;
import com.cangku.mapper.ApplysMapper;
import com.cangku.service.ApplysService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import javax.annotation.Resource;
import com.baomidou.mybatisplus.core.metadata.IPage;
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cangku
 * @since 2023-11-13
 */
@Service
public class ApplysServiceImpl extends ServiceImpl<ApplysMapper, Applys> implements ApplysService {
    @Resource
    private ApplysMapper applysMapper;
    @Override
    public IPage pageCC(Page<Applys> page, Wrapper wrapper) {
        return applysMapper.pageCC(page,wrapper);
    }
}
