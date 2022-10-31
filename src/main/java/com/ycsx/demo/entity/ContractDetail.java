package com.ycsx.demo.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import org.omg.CORBA.OBJECT_NOT_EXIST;

/**
 * <p>
 *
 * </p>
 *
 * @author ycsxdyf
 * @since 2022-02-16
 */
@Getter
@Setter
@TableName("contract_detail")
public class ContractDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 协议ID
     */
    private Integer contractId;

    /**
     * 文件状态（1-待审核/2-拒绝/3-已审核/4-结束鉴定）
     */
    private Integer currentState;

    /**
     * 审核意见
     */
    private String comments;

    /**
     * 附件1-实习申请表
     */
    private String annexA;

    /**
     * 附件2-单位简介表
     */
    private String annexB;

    /**
     * 附件3-实习介绍信
     */
    private String annexC;

    /**
     * 附件4-单位接收函
     */
    private String annexD;

    /**
     * 附件5-安全承诺书
     */
    private String annexE;

    /**
     * 附件6-家长认可函
     */
    private String annexF;

    /**
     * 材料7-结束实习-单位鉴定表
     */
    private String annexEnd;

    private String endResult;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @TableField(exist = false)
    private Contract contract;

}
