package com.ycsx.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author ycsxdyf
 * @since 2022-04-25
 */
@Getter
@Setter
  @TableName("pool_detail")
public class PoolDetail implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 主键ID
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 学生ID
     */
      private Integer userId;

      /**
     * 集中实习岗位ID
     */
      private Integer poolId;

      /**
     * 当前状态
     */
      private Integer currentState;

      /**
     * 审核意见
     */
      private String comments;

      /**
     * 实习分数或评价
     */
      private String endResult;

      /**
     * 附件资料1
     */
      private String annexA;

      /**
     * 附件资料2
     */
      private String annexB;

      /**
     * 附件资料3
     */
      private String annexC;

      /**
     * 附件资料4
     */
      private String annexD;

      /**
     * 附件资料5
     */
      private String annexE;

      /**
     * 附件资料6
     */
      private String annexF;

      /**
     * 附件资料7-结束鉴定材料
     */
      private String annexEnd;

      /**
     * 创建时间
     */
      private LocalDateTime createTime;


}
