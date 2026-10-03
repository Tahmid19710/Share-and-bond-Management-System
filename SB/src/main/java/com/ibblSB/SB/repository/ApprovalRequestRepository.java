package com.ibblSB.SB.repository;


import com.ibblSB.SB.entity.ApprovalRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface ApprovalRequestRepository
        extends JpaRepository<ApprovalRequest, Long> {



    // =========================================
    // CHECKER: Pending Checker Requests
    // =========================================

    List<ApprovalRequest> findByStatus(
            String status
    );



    // =========================================
    // CHECKER VIEW: Find by Transaction ID
    // ENTITY_ID = TR_ID
    // =========================================

    List<ApprovalRequest> findByEntityId(
            String entityId
    );



    // =========================================
    // APPROVER: Pending Approver Requests
    // =========================================

    List<ApprovalRequest> findByCurrentStageAndStatus(
            String currentStage,
            String status
    );



    // =========================================
    // MAKER: Own Requests
    // =========================================

    List<ApprovalRequest> findByMakerId(
            String makerId
    );


}