package com.ycsx.demo.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycsx.demo.common.Result;
import com.ycsx.demo.entity.Contract;
import com.ycsx.demo.entity.User;
import com.ycsx.demo.mapper.ContractDetailMapper;
import com.ycsx.demo.mapper.ContractMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.ycsx.demo.service.IContractDetailService;
import com.ycsx.demo.entity.ContractDetail;

import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ycsxdyf
 * @since 2022-02-16
 */
@RestController
@RequestMapping("/contract-detail")
public class ContractDetailController {

    @Resource
    private IContractDetailService contractDetailService;

    @Resource
    private ContractMapper contractMapper;

    @Resource
    private ContractDetailMapper contractDetailMapper;

    // 新增或更新
    @PostMapping
    public boolean save(@RequestBody ContractDetail contractDetail) {
        boolean bool = contractDetailService.saveOrUpdate(contractDetail);
        // 同步更新Contract C=3  CD=0 / C=4  CD=1 / C=5  CD=2 / C=6  CD=3
        Contract updateContract = new Contract();
        if (contractDetail.getCurrentState() == 1) {
            updateContract.setCurrentState(4);
            contractMapper.update(updateContract, new UpdateWrapper<Contract>()
                    .eq("id", contractDetail.getContractId()));
        }
        if (contractDetail.getCurrentState() == 2) {
            updateContract.setCurrentState(5);
            contractMapper.update(updateContract, new UpdateWrapper<Contract>()
                    .eq("id", contractDetail.getContractId()));
        }
        if (contractDetail.getCurrentState() == 3) {
            updateContract.setCurrentState(6);
            contractMapper.update(updateContract, new UpdateWrapper<Contract>()
                    .eq("id", contractDetail.getContractId()));
        }
        if (contractDetail.getCurrentState() == 4) {
            updateContract.setCurrentState(7);
            contractMapper.update(updateContract, new UpdateWrapper<Contract>()
                    .eq("id", contractDetail.getContractId()));
        }
        if (contractDetail.getCurrentState() == 5) {
            updateContract.setCurrentState(8);
            contractMapper.update(updateContract, new UpdateWrapper<Contract>()
                    .eq("id", contractDetail.getContractId()));
        }
        if (contractDetail.getCurrentState() == 6) {
            updateContract.setCurrentState(9);
            contractMapper.update(updateContract, new UpdateWrapper<Contract>()
                    .eq("id", contractDetail.getContractId()));
        }
        if (StrUtil.isNotBlank(contractDetail.getEndResult())) {
            if (contractDetail.getEndResult().equals("合格")) {
                updateContract.setPoints("合格");
                contractMapper.update(updateContract, new UpdateWrapper<Contract>()
                        .eq("id", contractDetail.getContractId()));
            }
            if (contractDetail.getEndResult().equals("优秀")) {
                updateContract.setPoints("优秀");
                contractMapper.update(updateContract, new UpdateWrapper<Contract>()
                        .eq("id", contractDetail.getContractId()));
            }
            if (contractDetail.getEndResult().equals("不合格")) {
                updateContract.setPoints("不合格");
                contractMapper.update(updateContract, new UpdateWrapper<Contract>()
                        .eq("id", contractDetail.getContractId()));
            }
        }
        return bool;
    }

    // 删除
    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return contractDetailService.removeById(id);
    }

    // 查询所有
    @GetMapping
    public Result<?> findContractPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                      @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<ContractDetail> wrapper = Wrappers.<ContractDetail>lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(ContractDetail::getCurrentState, search);
        }
        Page<ContractDetail> detailPage = contractDetailMapper.findDetailPage(new Page<>(pageNum, pageSize), search);
        return Result.success(detailPage);
    }

    // 查询所有
    @GetMapping("/appraisal")
    public Result<?> findContractDetailPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                            @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<ContractDetail> wrapper = Wrappers.<ContractDetail>lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(ContractDetail::getCurrentState, search);
        }
        Page<ContractDetail> detailPage = contractDetailMapper.findContractDetailPage(new Page<>(pageNum, pageSize), search);
        return Result.success(detailPage);
    }

    // (改)根据用户id查询Details（区别Contract）
    @GetMapping("/findDetails/{userId}")
    public Result<?> findOne(@PathVariable Integer userId) {
        return Result.success(contractDetailMapper.getDetailByUserId(userId));
    }

    // 根据ContractId查询
    @GetMapping("/{contractId}")
    public ContractDetail findByContractId(@PathVariable Integer contractId) {
        return contractDetailMapper.getByContractId(contractId);
    }

    // 分页
    @GetMapping("/page")
    public Page<ContractDetail> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                         @RequestParam(defaultValue = "10") Integer pageSize) {
        return contractDetailService.page(new Page<>(pageNum, pageSize));
    }

    /**
     * 总记录数
     */
    @GetMapping("/counts")
    public Result<?> getCounts() {
        return Result.success(contractDetailService.count());
    }

}

