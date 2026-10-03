package com.ibblSB.SB.repository;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;



@Repository
public class SequenceRepository {


    private final JdbcTemplate jdbcTemplate;



    public SequenceRepository(
            JdbcTemplate jdbcTemplate
    ){

        this.jdbcTemplate = jdbcTemplate;

    }




    public Long getNextShareMovementSequence(){


        return jdbcTemplate.queryForObject(

                "SELECT SEQ_SHARE_MOVEMENT.NEXTVAL FROM DUAL",

                Long.class

        );


    }

    public Long getNextGroupTransferSequence(){


        return jdbcTemplate.queryForObject(

                "SELECT SEQ_SHARE_MOVEMENT.NEXTVAL FROM DUAL",

                Long.class

        );


    }

    public Long getNextTransShareOid(){


        return jdbcTemplate.queryForObject(

                "SELECT SEQ_TRANS_SHARE_OID.NEXTVAL FROM DUAL",

                Long.class

        );


    }


}