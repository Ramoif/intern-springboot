package com.ycsx.demo.entity;

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
 * @since 2022-04-25
 */
@Getter
@Setter
  @TableName("pool_centralized")
public class PoolCentralized implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 主键ID
     */
        private Integer id;

      /**
     * 发布人ID
     */
      private Integer teacherId;

      /**
     * 岗位名称
     */
      private String postName;

      /**
     * 单位名称
     */
      private String chargeName;

      /**
     * 地址
     */
      private String address;

      /**
     * 展示图片
     */
      private String addressPhoto;

      /**
     * 联系电话
     */
      private String addressTelephone;

      /**
     * 开始时间
     */
      private LocalDate startTime;

      /**
     * 结束时间
     */
      private LocalDate endTime;

      /**
     * 当前状态
     */
      private Integer currentState;

      /**
     * 最大人数
     */
      private Integer maxSize;

      /**
     * 当前人数
     */
      private Integer currentSize;

      /**
     * 描述
     */
      private String poolDescription;

      /**
     * 创建时间
     */
      private LocalDateTime createTime;


}
