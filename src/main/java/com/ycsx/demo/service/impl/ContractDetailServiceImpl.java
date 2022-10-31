package com.ycsx.demo.service.impl;

import com.ycsx.demo.entity.Contract;
import com.ycsx.demo.entity.ContractDetail;
import com.ycsx.demo.entity.ContractDetailDTO;
import com.ycsx.demo.mapper.ContractDetailMapper;
import com.ycsx.demo.mapper.ContractMapper;
import com.ycsx.demo.service.IContractDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycsx.demo.service.IContractService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ycsxdyf
 * @since 2022-02-16
 */
@Service
public class ContractDetailServiceImpl extends ServiceImpl<ContractDetailMapper, ContractDetail> implements IContractDetailService {

}
