package com.ibblSB.SB.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;



@Getter
@Setter
@Entity
@Table(name = "T_TRANS_AUTH")
public class TransAuth {



    @Id
    @Column(name = "OID", length = 20)
    private String oid;



    @Column(name = "FOLIO_BO", length = 16)
    private String folioBo;



    @Column(name = "TR_ID", length = 20)
    private String trId;



    @Column(name = "TR_CODE", length = 40)
    private String trCode;



    @Temporal(TemporalType.DATE)
    @Column(name = "TR_DATE")
    private Date trDate;



    @Column(name = "DR_AMT")
    private Double drAmt;



    @Column(name = "CR_AMT")
    private Double crAmt;



    @Column(name = "TR_STATE")
    private Integer trState;



    @Column(name = "MAKER_ID", length = 60)
    private String makerId;



    @Column(name = "MAKER_IP", length = 20)
    private String makerIp;



    @Column(name = "CHECKER_ID", length = 60)
    private String checkerId;



    @Column(name = "CHECKER_IP", length = 20)
    private String checkerIp;



    @Column(name = "REF_INSTR_NO", length = 20)
    private String refInstrNo;



    @Column(name = "CONTRA_ACC_NO", length = 17)
    private String contraAccNo;



    @Column(name = "INSTR_NO", length = 20)
    private String instrNo;



    @Column(name = "INSTR_TYPE", length = 2)
    private String instrType;



    @Temporal(TemporalType.DATE)
    @Column(name = "INSTR_DATE")
    private Date instrDate;



    @Column(name = "PARTICULAR", length = 255)
    private String particular;



    @Column(name = "REMARKS", length = 255)
    private String remarks;



    @Temporal(TemporalType.DATE)
    @Column(name = "MODIFY_DATE")
    private Date modifyDate;
}