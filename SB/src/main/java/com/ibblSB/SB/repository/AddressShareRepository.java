package com.ibblSB.SB.repository;


import com.ibblSB.SB.entity.AddressShare;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;



@Repository
public interface AddressShareRepository
        extends JpaRepository<AddressShare,String>{


    Optional<AddressShare> findByOid(String oid);


}