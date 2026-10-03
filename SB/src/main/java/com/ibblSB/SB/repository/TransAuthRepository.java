package com.ibblSB.SB.repository;


import com.ibblSB.SB.entity.TransAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TransAuthRepository
        extends JpaRepository<TransAuth,String> {


}