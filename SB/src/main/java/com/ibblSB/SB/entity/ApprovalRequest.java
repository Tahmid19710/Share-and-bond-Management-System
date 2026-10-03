package com.ibblSB.SB.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "T_APPROVAL_REQUEST")
@Data
public class ApprovalRequest {


    // =========================================
    // PRIMARY KEY
    // =========================================

    @Id
    @Column(name = "REQUEST_ID")
    private Long requestId;


    // =========================================
    // ENTITY INFORMATION
    // =========================================

    @Column(name = "ENTITY_ID")
    private String entityId;


    @Column(name = "ENTITY_TYPE")
    private String entityType;


    // =========================================
    // MAKER INFORMATION
    // =========================================

    @Column(name = "MAKER_ID")
    private String makerId;


    @Column(name = "MAKER_IP")
    private String makerIp;


    // =========================================
    // CHECKER INFORMATION
    // =========================================

    @Column(name = "CHECKER_ID")
    private String checkerId;


    @Column(name = "CHECKER_IP")
    private String checkerIp;


    // =========================================
    // APPROVER INFORMATION
    // =========================================

    @Column(name = "APPROVER_ID")
    private String approverId;


    @Column(name = "APPROVER_IP")
    private String approverIp;


    // =========================================
    // WORKFLOW
    // =========================================

    @Column(name = "CURRENT_STAGE")
    private String currentStage;


    @Column(name = "STATUS")
    private String status;


    @Column(name = "OPERATION_CODE")
    private String operationName;


    // =========================================
    // SOURCE INFORMATION
    // =========================================

    @Column(name = "SOURCE_ID")
    private String sourceId;


    @Column(name = "SOURCE_TYPE")
    private String sourceType;


    // =========================================
    // BUSINESS INFORMATION
    // =========================================

    @Column(name = "BUSINESS_DATE")
    private LocalDate businessDate;


    @Column(name = "BUSINESS_REF")
    private String businessReference;


    // =========================================
    // DATE / TIME
    // =========================================

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;


    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;


    @Column(name = "DECIDED_AT")
    private LocalDateTime decidedAt;


    // =========================================
    // VERSION
    // =========================================

    @Column(name = "VERSION_NO")
    private Integer versionNo;

}