package com.ycsx.demo.service;

import com.ycsx.demo.common.Result;
import com.ycsx.demo.entity.PoolCentralized;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ycsxdyf
 * @since 2022-04-25
 */
public interface IPoolCentralizedService extends IService<PoolCentralized> {

    int quantityPlus(Integer id);
}
