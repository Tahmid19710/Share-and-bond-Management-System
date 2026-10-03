package com.ibblSB.SB.entity;


import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name="T_BANKINFO_SHARE")
@Data
public class BankInfoShare {


    @Id
    @Column(name="OID")
    private String oid;


    @Column(name="FOLIO_BO")
    private String folioBo;


    @Column(name="ACC_NO")
    private String accNo;


    @Column(name="ACCOUNT_NO")
    private String accountNo;


    @Column(name="BANK_NAME")
    private String bankName;


    @Column(name="BRANCH_NAME")
    private String branchName;


    @Column(name="BRANCH")
    private String branch;


    @Column(name="ROUTING_NO")
    private String routingNo;


    @Column(name="ROUTING_NUMBER")
    private String routingNumber;


}