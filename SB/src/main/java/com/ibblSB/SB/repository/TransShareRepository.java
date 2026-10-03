package com.ibblSB.SB.repository;


import com.ibblSB.SB.entity.TransShare;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface TransShareRepository
        extends JpaRepository<TransShare, Long> {



    @Query(value =
            "SELECT * FROM (" +
                    "SELECT * FROM T_TRANS_SHARE " +
                    "ORDER BY POST_DATE DESC" +
                    ") WHERE ROWNUM <= 5",
            nativeQuery = true)
    List<TransShare> findLatestTransactions();



    List<TransShare> findByTrId(String trId);


}