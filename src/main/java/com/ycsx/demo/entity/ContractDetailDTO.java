package com.ycsx.demo.entity;

import lombok.Data;

@Data
public class ContractDetailDTO {
    private Integer id;
    private Integer userId;
    private Integer contractId;
    private String partA;
    private String partB;
    private String address;
    private String school;
    private String partCharge;
    private String endResult;
}
