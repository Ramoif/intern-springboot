package com.ycsx.demo.service;

import com.ycsx.demo.entity.PoolDetail;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ycsxdyf
 * @since 2022-04-25
 */
public interface IPoolDetailService extends IService<PoolDetail> {
    Long selectCounts(Integer userId);
}
