package com.ycsx.demo.service.impl;

import com.ycsx.demo.entity.PoolCentralized;
import com.ycsx.demo.mapper.PoolCentralizedMapper;
import com.ycsx.demo.service.IPoolCentralizedService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ycsxdyf
 * @since 2022-04-25
 */
@Service
public class PoolCentralizedServiceImpl extends ServiceImpl<PoolCentralizedMapper, PoolCentralized> implements IPoolCentralizedService {

    @Resource
    private PoolCentralizedMapper mapper;

    @Override
    public int quantityPlus(Integer id) {
        return mapper.quantityPlus(id);
    }
}
