package com.ycsx.demo.mapper;

import com.ycsx.demo.entity.PoolDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ycsxdyf
 * @since 2022-04-25
 */
public interface PoolDetailMapper extends BaseMapper<PoolDetail> {

    @Select("select count(1) from pool_detail where user_id = #{userId} and current_state in (0,1)")
    Long getUserPoolStateCount(Integer userId);
}
