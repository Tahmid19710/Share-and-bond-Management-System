package com.ibblSB.SB.repository;


import com.ibblSB.SB.entity.ApprovalAction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;



@Repository
public interface ApprovalActionRepository
        extends JpaRepository<ApprovalAction, Long> {



    List<ApprovalAction> findByRequestId(Long requestId);



    List<ApprovalAction> findByRequestIdOrderByActionAtDesc(
            Long requestId
    );


}