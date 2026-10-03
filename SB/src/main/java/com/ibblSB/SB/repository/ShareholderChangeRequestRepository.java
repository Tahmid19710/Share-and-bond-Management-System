package com.ibblSB.SB.repository;


import com.ibblSB.SB.entity.ShareholderChangeRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ShareholderChangeRequestRepository
        extends JpaRepository<ShareholderChangeRequest, String> {


}