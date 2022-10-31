package com.ycsx.demo.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycsx.demo.entity.PoolCentralized;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ycsxdyf
 * @since 2022-04-25
 */
public interface PoolCentralizedMapper extends BaseMapper<PoolCentralized> {
    @Update("update pool_centralized set current_size = current_size + 1  where pool_centralized.id = #{id}")
    int quantityPlus(Integer id);

    Page<PoolCentralized> findCurrentStatePage(Page<PoolCentralized> page, @Param("current_state") String current_state);
}
