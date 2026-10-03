package com.ibblSB.SB.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;



@Entity
@Table(name="T_SHAREHOLDER_CHANGE_REQUEST")
@Data
public class ShareholderChangeRequest {


    @Id
    @Column(name="CHANGE_ID")
    private String changeId;


    @Column(name="FOLIO_BO")
    private String folioBo;


    @Column(name="OPERATION_CODE")
    private String operationCode;


    @Lob
    @Column(name="OLD_VALUE")
    private String oldValue;


    @Lob
    @Column(name="NEW_VALUE")
    private String newValue;


    @Column(name="CREATED_BY")
    private String createdBy;


    @Column(name="CREATED_IP")
    private String createdIp;


    @Column(name="CREATED_AT")
    private LocalDateTime createdAt;


    @Column(name="UPDATED_AT")
    private LocalDateTime updatedAt;


    @Column(name="VERSION_NO")
    private Integer versionNo;


}