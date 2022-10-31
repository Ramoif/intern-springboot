package com.ycsx.demo.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycsx.demo.entity.ContractDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ycsxdyf
 * @since 2022-02-16
 */
public interface ContractDetailMapper extends BaseMapper<ContractDetail> {
    @Select("select * from contract_detail where contract_id = #{contractId}")
    ContractDetail getByContractId(Integer contractId);

    @Select("select * from contract_detail where user_id = #{userId}")
    List<ContractDetail> getByUserId(Integer userId);

    List<ContractDetail> getDetailByUserId(Integer userId);

    // 连表一对一查询
    Page<ContractDetail> findDetailPage(Page<ContractDetail> page, @Param("current_state") String current_state);

    // 给鉴定阶段的详细分页查询
    Page<ContractDetail> findContractDetailPage(Page<ContractDetail> page, @Param("current_state") String current_state);

    // 导出查询所有
    List<ContractDetail> getAllDetails();
}
