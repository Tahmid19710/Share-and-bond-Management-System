package com.ibblSB.SB.entity;


import jakarta.persistence.*;
import lombok.Data;



@Entity
@Table(name="T_ACCOUNT_SHARE")
@Data
public class AccountShare {


    @Id
    @Column(name="OID")
    private String oid;



    @Column(name="FOLIO_BO")
    private String folioBo;



    @Column(name="CUST_NAME")
    private String custName;



    @Column(name="PHONE")
    private String phone;



    @Column(name="EMAIL")
    private String email;



    @Column(name="CUST_TYPE")
    private Integer custType;



    @Column(name="CITIZEN_TYPE")
    private Integer citizenType;



    @Column(name="RESIDENT_TYPE")
    private String residentType;



    @Column(name="IS_VALID")
    private Integer isValid;



    @Column(name="IS_EMPLOYEE")
    private Integer isEmployee;



    @Column(name="TIN_NO")
    private String tinNo;



    @Column(name="ICB_CODE")
    private Integer icbCode;



    @Column(name="IS_LIEN")
    private Integer isLien;



    @Column(name="SHARES")
    private Integer shares;



    @Column(name="SUSPENSE")
    private Integer suspense;



    @Column(name="BONUS")
    private Integer bonus;



    @Column(name="BALANCE")
    private Double balance;



    @Column(name="MAKER_ID")
    private String makerId;



    @Column(name="CHECKER_ID")
    private String checkerId;



    @Column(name="STATUS")
    private Integer status;



}