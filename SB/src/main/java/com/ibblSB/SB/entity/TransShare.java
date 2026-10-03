package com.ibblSB.SB.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.Date;



@Getter
@Setter
@Entity
@Table(name = "T_TRANS_SHARE")
public class TransShare {



    @Id
    @Column(name = "OID")
    private Long oid;



    @Column(name = "FOLIO_BO")
    private String folioBo;



    @Column(name = "TR_ID")
    private String trId;



    @Column(name = "GRP_TR_ID")
    private String grpTrId;



    @Temporal(TemporalType.DATE)
    @Column(name = "TR_DATE")
    private Date trDate;



    @Column(name = "TR_TYPE")
    private String trType;



    @Column(name = "TR_CODE")
    private String trCode;



    @Column(name = "DR_SHARE")
    private Double drShare;



    @Column(name = "CR_SHARE")
    private Double crShare;



    @Column(name = "CONTRA_ACC")
    private String contraAcc;



    @Column(name = "INSTRUMENT")
    private String instrument;



    @Column(name = "PARTICULARS")
    private String particulars;



    @Column(name = "USER_ID")
    private String userId;



    @Column(name = "IS_VALID")
    private Integer isValid;



    @Column(name = "POST_DATE")
    private Date postDate;



}