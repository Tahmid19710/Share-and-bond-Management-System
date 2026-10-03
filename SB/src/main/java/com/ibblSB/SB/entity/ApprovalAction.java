package com.ibblSB.SB.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Table(name="T_APPROVAL_ACTION")
@Data
public class ApprovalAction {


    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "approval_action_seq"
    )
    @SequenceGenerator(
            name="approval_action_seq",
            sequenceName="APPROVAL_ACTION_SEQ",
            allocationSize=1
    )
    @Column(name="ACTION_ID")
    private Long actionId;



    @Column(name="REQUEST_ID")
    private Long requestId;



    @Column(name="ACTION")
    private String action;



    @Column(name="ACTOR_ID")
    private String actorId;



    @Column(name="ACTOR_IP")
    private String actorIp;



    @Column(name="STAGE")
    private String stage;



    @Column(name="REMARKS")
    private String remarks;



    @Column(name="ACTION_AT")
    private LocalDateTime actionAt;


}