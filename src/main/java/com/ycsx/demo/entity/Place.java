package com.ycsx.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@TableName("place")
@Data
public class Place {
    //主键，自增
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String unitType;
    private String leaderName;
    private String address;
    private Long telephone;
    private String cover;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "UTC")
    private Date createTime;
}
