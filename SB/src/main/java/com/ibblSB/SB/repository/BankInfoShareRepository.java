package com.ibblSB.SB.repository;


import com.ibblSB.SB.entity.BankInfoShare;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;



@Repository
public interface BankInfoShareRepository
        extends JpaRepository<BankInfoShare,String>{


    Optional<BankInfoShare> findByOid(String oid);


}