package com.ibblSB.SB.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class ShareholderFormDTO {


    // ======================
    // ACCOUNT SHARE
    // ======================
    @NotBlank()
    private String folioBo;

    private String custName;

    private String phone;

    private String email;

    private Integer custType;

    private Integer citizenType;

    private String residentType;

    private Integer isEmployee;

    private String tinNo;

    private Integer icbCode;

    private Integer isLien;

    private Integer shares;

    private Integer suspense;

    private Integer bonus;

    private Double balance;


    // ======================
    // ADDRESS
    // ======================

    private String add1;

    private String add2;

    private String add3;

    private String add4;

    private String countryName;


    // ======================
    // BANK
    // ======================

    private String bankName;

    private String accNo;

    private String branchName;

    private String routingNo;


}