package com.ycsx.demo.mapper;

import com.ycsx.demo.entity.Contract;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ycsxdyf
 * @since 2022-02-14
 */
public interface ContractMapper extends BaseMapper<Contract> {
    @Select("select * from contract where user_id = #{userId}")
    List<Contract> getByUserId(Integer userId);

    // 查询当前用户处于实习过程的协议数量
    @Select("select count(1) from contract where user_id = #{userId} and current_state in (0,1,2,3,4,5,6,7,8)")
    Long getUserInternStateCount(Integer userId);
}
