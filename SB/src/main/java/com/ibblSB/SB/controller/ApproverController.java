package com.ibblSB.SB.controller;


import com.ibblSB.SB.entity.AccountShare;
import com.ibblSB.SB.entity.ApprovalAction;
import com.ibblSB.SB.entity.ApprovalRequest;

import com.ibblSB.SB.repository.ApprovalActionRepository;
import com.ibblSB.SB.repository.ApprovalRequestRepository;

import com.ibblSB.SB.service.AccountShareService;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;



@Controller
@RequestMapping("/approver")
public class ApproverController {



    private final ApprovalRequestRepository requestRepository;

    private final ApprovalActionRepository actionRepository;

    private final AccountShareService accountShareService;




    public ApproverController(

            ApprovalRequestRepository requestRepository,

            ApprovalActionRepository actionRepository,

            AccountShareService accountShareService

    ){

        this.requestRepository = requestRepository;

        this.actionRepository = actionRepository;

        this.accountShareService = accountShareService;

    }
    // ==============================
// APPROVER DASHBOARD
// ==============================

    @GetMapping("/dashboard")
    public String dashboard(){

        return "approver-dashboard";

    }





    // ==============================
    // Approver List
    // ==============================


    @GetMapping("/list")
    public String list(Model model){


        List<ApprovalRequest> requests =

                requestRepository.findByCurrentStageAndStatus(
                        "APPROVER",
                        "PENDING_APPROVER"
                );



        model.addAttribute(
                "requests",
                requests
        );


        return "approver-list";


    }









    // ==============================
    // APPROVE
    // ==============================


    @GetMapping("/approve/{id}")
    public String approve(

            @PathVariable Long id

    ){



        ApprovalRequest request =

                requestRepository
                        .findById(id)
                        .orElse(null);



        if(request != null){


            LocalDateTime now =
                    LocalDateTime.now();



            // ==============================
            // Update Approval Request
            // ==============================


            request.setStatus(
                    "APPROVED"
            );


            request.setCurrentStage(
                    "COMPLETED"
            );


            request.setApproverId(
                    "test.approver"
            );


            request.setDecidedAt(
                    now
            );


            request.setUpdatedAt(
                    now
            );


            requestRepository.save(
                    request
            );







            // ==============================
            // UPDATE T_ACCOUNT_SHARE STATUS
            // ==============================


            AccountShare shareholder =

                    accountShareService.findById(
                            request.getEntityId()
                    );



            System.out.println(
                    "ENTITY ID = "
                            + request.getEntityId()
            );


            System.out.println(
                    "SHAREHOLDER = "
                            + shareholder
            );



            if(shareholder != null){


                shareholder.setStatus(
                        1
                );


                accountShareService.save(
                        shareholder
                );


            }









            // ==============================
            // Approval Action Log
            // ==============================


            ApprovalAction action =

                    new ApprovalAction();



            action.setRequestId(
                    id
            );


            action.setAction(
                    "APPROVED"
            );


            action.setActorId(
                    "test.approver"
            );


            action.setActorIp(
                    "127.0.0.1"
            );


            action.setStage(
                    "APPROVER"
            );


            action.setRemarks(
                    "Approved by final approver"
            );


            action.setActionAt(
                    now
            );



            actionRepository.save(
                    action
            );



        }



        return "redirect:/approver/list";


    }









    // ==============================
    // REJECT
    // ==============================


    @GetMapping("/reject/{id}")
    public String reject(

            @PathVariable Long id

    ){



        ApprovalRequest request =

                requestRepository
                        .findById(id)
                        .orElse(null);



        if(request != null){



            LocalDateTime now =

                    LocalDateTime.now();




            request.setStatus(
                    "REJECTED"
            );


            request.setCurrentStage(
                    "COMPLETED"
            );


            request.setApproverId(
                    "test.approver"
            );


            request.setDecidedAt(
                    now
            );


            request.setUpdatedAt(
                    now
            );



            requestRepository.save(
                    request
            );







            ApprovalAction action =

                    new ApprovalAction();



            action.setRequestId(
                    id
            );


            action.setAction(
                    "REJECTED"
            );


            action.setActorId(
                    "test.approver"
            );


            action.setActorIp(
                    "127.0.0.1"
            );


            action.setStage(
                    "APPROVER"
            );


            action.setRemarks(
                    "Rejected by final approver"
            );


            action.setActionAt(
                    now
            );



            actionRepository.save(
                    action
            );



        }



        return "redirect:/approver/list";


    }



}