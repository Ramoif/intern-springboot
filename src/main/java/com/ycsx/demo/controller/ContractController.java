package com.ycsx.demo.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.word.Word07Writer;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycsx.demo.common.Result;
import com.ycsx.demo.entity.ContractDetail;
import com.ycsx.demo.entity.User;
import com.ycsx.demo.mapper.ContractDetailMapper;
import com.ycsx.demo.mapper.ContractMapper;
import com.ycsx.demo.service.IContractDetailService;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import java.awt.*;
import java.net.URLEncoder;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.ycsx.demo.service.IContractService;
import com.ycsx.demo.entity.Contract;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ycsxdyf
 * @since 2022-02-14
 */
@RestController
@RequestMapping("/contract")
public class ContractController {

    @Resource
    private IContractService contractService;

    @Resource
    private ContractMapper contractMapper;

    @Resource
    private ContractDetailMapper contractDetailMapper;

    @Resource
    private IContractDetailService cdService;

    /**
     * 查询当前用户是否有正在进行的实习
     * @param userId
     * @return
     */
    @GetMapping("/queryProcessing/{userId}")
    public Result<?> queryProcessing(@PathVariable Integer userId) {
        Long counts = contractMapper.getUserInternStateCount(userId);
        return Result.success(counts);
    }

    // 新增或更新
    @PostMapping
    public boolean save(@RequestBody Contract contract) {
        boolean bool = contractService.saveOrUpdate(contract);

        // 如果更新的结果是已审核，则创建一个新的ContractDetail
        if (contract.getCurrentState() == 3) {
            // 避免重复操作，需要判断这两主键ID是否已经在数据库中
            long count = contractDetailMapper.selectCount(new QueryWrapper<ContractDetail>()
                    .eq("contract_id", contract.getId())
                    .eq("user_id", contract.getUserId()));
            if (!(count > 0)) {
                ContractDetail contractDetail = new ContractDetail();
                contractDetail.setUserId(contract.getUserId());
                contractDetail.setContractId(contract.getId());
                boolean save = cdService.save(contractDetail);
                if (!save) {
                    bool = false;
                }
            }
        }
        return bool;
    }

    // 删除
    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return contractService.removeById(id);
    }

    // (改)根据用户id查询
    @GetMapping("/{userId}")
    public Result<?> findOne(@PathVariable Integer userId) {
        return Result.success(contractMapper.getByUserId(userId));
    }

    // 分页
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Contract> wrapper = Wrappers.<Contract>lambdaQuery().like(Contract::getPartB, search);
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Contract::getPartB, search);
        }
        wrapper.orderByDesc(Contract::getCreateTime);
        Page<Contract> contractPage = contractMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(contractPage);
    }

    /**
     * 记录数
     */
    @GetMapping("/counts")
    public Result<?> getCounts() {
        return Result.success(contractService.count());
    }

    /**
     * 导出协议至word文档
     *
     * @author hbycdyf
     * @since 0215
     */
    @GetMapping("/export/{id}")
    public void exportUser(HttpServletResponse response, @PathVariable Integer id) throws Exception {

        Contract contract = contractService.getById(id);
        String partA = contract.getPartA();
        String partB = contract.getPartB();
        String address = contract.getAddress();
        String partCharge = contract.getPartCharge();
        String partPhone = contract.getPartPhone();
        String school = contract.getSchool();
        String gradeClass = contract.getGradeClass();
        String gradeNum = contract.getGradeNum();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startTime = dateFormat.format(contract.getStartTime());
        String endTime = dateFormat.format(contract.getEndTime());

        Word07Writer writer = new Word07Writer();
        writer.addText(ParagraphAlignment.CENTER, new Font("宋体", Font.PLAIN, 18), "自主实习实训申请书");
        writer.addText(ParagraphAlignment.RIGHT, new Font("宋体", Font.PLAIN, 10), "编号 : " + id);
        writer.addText(new Font("宋体", Font.PLAIN, 10), "实习生姓名 : " + partB);
        writer.addText(new Font("宋体", Font.PLAIN, 10), "实习单位(基地) : " + partA);
        writer.addText(new Font("宋体", Font.PLAIN, 10), "实习地址 : " + address);
        writer.addText(new Font("宋体", Font.PLAIN, 10), "单位负责人 : " + partCharge);
        writer.addText(new Font("宋体", Font.PLAIN, 10), "单位联系电话 : " + partPhone);
        writer.addText(new Font("宋体", Font.PLAIN, 10), "实习生就读学校 : " + school);
        writer.addText(new Font("宋体", Font.PLAIN, 10), "实习生班级 : " + gradeClass);
        writer.addText(new Font("宋体", Font.PLAIN, 10), "实习生学号 : " + gradeNum);
        writer.addText(new Font("宋体", Font.PLAIN, 10), "实习生联系电话 : " + contract.getStuPhone());
        writer.addText(new Font("宋体", Font.PLAIN, 10), "");
        writer.addText(new Font("宋体", Font.BOLD, 12), "一、实习协议期限");
        writer.addText(new Font("宋体", Font.PLAIN, 10), "第一条、本协议生效日期 ：" + startTime + " 至 " + endTime + "。");
        writer.addText(new Font("宋体", Font.PLAIN, 10), "");
        writer.addText(new Font("宋体", Font.BOLD, 12), "二、申请人承诺内容");
        writer.addText(new Font("宋体", Font.PLAIN, 8), "1、在外实习期间本人学业由自己负责，保证按时参加考试，完成学校的各项要求；");
        writer.addText(new Font("宋体", Font.PLAIN, 8), "2、在外实习期间本人的人身财产安全由自己负责；");
        writer.addText(new Font("宋体", Font.PLAIN, 8), "3、在外实习期间本人的交通安全由自己负责；");
        writer.addText(new Font("宋体", Font.PLAIN, 8), "4、在外实习期间遵守学校的各项规章制度，与指导教师始终保持联系；");
        writer.addText(new Font("宋体", Font.PLAIN, 8), "5、学生在自主实习期间要主动联系就业单位。");
        writer.addText(new Font("宋体", Font.PLAIN, 10), "");
        writer.addText(new Font("宋体", Font.PLAIN, 10), "是否同意 ： ");
        writer.addText(new Font("宋体", Font.PLAIN, 10), "");
        writer.addText(new Font("宋体", Font.PLAIN, 10), "学部意见 ： ");
        writer.addText(new Font("宋体", Font.PLAIN, 10), "");
        writer.addText(new Font("宋体", Font.PLAIN, 10), "备注 ： ");
        writer.addText(new Font("宋体", Font.PLAIN, 10), "");

        // 5、设置浏览器响应（必须）格式固定
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("实习协议书导出", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".docx");
        // 6、输出流
        ServletOutputStream out = response.getOutputStream();
        // 7、刷新到输出流
        writer.flush(out, true);
        // 8、关闭
        out.close();
        writer.close();
    }

    /**
     * 导出Excel
     *
     * @param response
     * @throws Exception
     */
    @GetMapping("/export")
    public void exportUser(HttpServletResponse response) throws Exception {
        // 1、查询所有用户
        List<Contract> list = contractService.list();
        // 2.1、使用在内存操作-写到浏览器的方式
        ExcelWriter writer = ExcelUtil.getWriter(true);
        // 2.2、写出到磁盘
        // ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/User.xlsx");
        // 3、自定义标题别名，把遍历出来的字段标题替换为别名
        writer.addHeaderAlias("id", "id");
        writer.addHeaderAlias("partB", "实习生姓名");
        writer.addHeaderAlias("partA", "实习基地名称");
        writer.addHeaderAlias("address", "实习基地地址");
        writer.addHeaderAlias("school", "所读学校");
        writer.addHeaderAlias("partCharge", "实习单位负责人");
        writer.addHeaderAlias("partPhone", "单位负责人电话");
        writer.addHeaderAlias("stuPhone", "学生联系电话");
        writer.addHeaderAlias("gradeNum", "学生学号");
        writer.addHeaderAlias("updateTime", "最后修改时间");
        writer.addHeaderAlias("points", "实习鉴定成绩");
        // 4、把list内容一次性写到excel
        writer.write(list, true);
        // 自适应宽度
        writer.autoSizeColumnAll();
        // 5、设置浏览器响应（必须）格式固定
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("实习信息导出", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        // 6、输出流
        ServletOutputStream out = response.getOutputStream();
        // 7、把Excel刷新到输出流
        writer.flush(out, true);
        // 8、关闭
        out.close();
        writer.close();
    }
}

