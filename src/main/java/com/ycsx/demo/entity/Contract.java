package com.ycsx.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author ycsxdyf
 * @since 2022-02-14
 */
@Getter
@Setter
@TableName("contract")
public class Contract implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * contract表ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 甲方-实习基地
     */
    private String partA;

    /**
     * 乙方-学生
     */
    private String partB;

    /**
     * 实习的详细地址
     */
    private String address;

    /**
     * 所属学校
     */
    private String school;

    /**
     * 联系电话
     */
    private String partCharge;

    private String partPhone;

    private String stuPhone;

    /**
     * 实习生目前班级、学号
     */

    private String gradeClass;

    private String gradeNum;

    /**
     * 审核状态（1已提交/2未通过/3已通过/4结束鉴定）
     */
    private Integer currentState;

    private String currentComment;

    /**
     * 实习开始时间
     */
    private LocalDate startTime;

    /**
     * 实习结束时间
     */
    private LocalDate endTime;

    /**
     * 协议创建时间
     */
    private LocalDateTime createTime;

    /**
     * 协议变更时间
     */
    private LocalDateTime updateTime;

    private String points;

}
