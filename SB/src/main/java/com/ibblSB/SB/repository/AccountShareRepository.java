package com.ibblSB.SB.repository;


import com.ibblSB.SB.entity.AccountShare;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface AccountShareRepository
        extends JpaRepository<AccountShare, String> {



    // =====================================
    // FIND BY STATUS
    // =====================================

    List<AccountShare> findByStatus(Integer status);




    // =====================================
    // FIND SHAREHOLDER BY FOLIO BO
    // SHARE TRANSFER SEARCH
    // =====================================

    AccountShare findByFolioBo(String folioBo);



}