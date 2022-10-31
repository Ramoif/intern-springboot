package com.ycsx.demo.service.impl;

import com.ycsx.demo.entity.PoolDetail;
import com.ycsx.demo.mapper.PoolDetailMapper;
import com.ycsx.demo.service.IPoolDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ycsxdyf
 * @since 2022-04-25
 */
@Service
public class PoolDetailServiceImpl extends ServiceImpl<PoolDetailMapper, PoolDetail> implements IPoolDetailService {
    @Resource
    private PoolDetailMapper poolDetailMapper;

    @Override
    public Long selectCounts(Integer userId) {
        return poolDetailMapper.getUserPoolStateCount(userId);
    }
}
