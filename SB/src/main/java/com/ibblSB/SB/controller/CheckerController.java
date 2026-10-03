package com.ibblSB.SB.controller;


import com.ibblSB.SB.entity.AccountShare;
import com.ibblSB.SB.entity.ApprovalAction;
import com.ibblSB.SB.entity.ApprovalRequest;
import com.ibblSB.SB.entity.TransShare;

import com.ibblSB.SB.repository.ApprovalActionRepository;
import com.ibblSB.SB.repository.ApprovalRequestRepository;
import com.ibblSB.SB.repository.TransShareRepository;

import com.ibblSB.SB.service.AccountShareService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;



@Controller
@RequestMapping("/checker")
public class CheckerController {



    private final ApprovalRequestRepository requestRepository;

    private final ApprovalActionRepository actionRepository;

    private final AccountShareService accountShareService;

    private final TransShareRepository transShareRepository;



    public CheckerController(

            ApprovalRequestRepository requestRepository,

            ApprovalActionRepository actionRepository,

            AccountShareService accountShareService,

            TransShareRepository transShareRepository

    ){

        this.requestRepository = requestRepository;

        this.actionRepository = actionRepository;

        this.accountShareService = accountShareService;

        this.transShareRepository = transShareRepository;

    }




    // =========================================================
    // CHECKER DASHBOARD
    // =========================================================


    @GetMapping("/dashboard")
    public String dashboard(Model model){


        List<ApprovalRequest> shareTransfers =

                requestRepository.findByStatus(
                                "PENDING_CHECKER"
                        )
                        .stream()
                        .filter(req ->
                                "SHARE_TRANSFER".equals(
                                        req.getEntityType()
                                )
                        )
                        .toList();



        model.addAttribute(
                "shareTransferCount",
                shareTransfers.size()
        );


        model.addAttribute(
                "shareTransfers",
                shareTransfers
        );


        return "checker-dashboard";

    }

     // =========================================================
     // CHECKER SHARE TRANSFER PENDING LIST
     // =========================================================


    @GetMapping("/share-transfer")
    public String shareTransferList(Model model){


        List<ApprovalRequest> requests =

                requestRepository.findByStatus(
                                "PENDING_CHECKER"
                        )
                        .stream()
                        .filter(request ->
                                "SHARE_TRANSFER".equals(
                                        request.getEntityType()
                                )
                        )
                        .toList();



        model.addAttribute(
                "requests",
                requests
        );


        return "checker-share-transfer";

    }

    // =========================================================
// VIEW SHARE TRANSFER DETAILS
// =========================================================


    @GetMapping("/share-transfer/view/{requestId}")
    public String viewShareTransfer(

            @PathVariable Long requestId,

            Model model

    ){


        ApprovalRequest request =

                requestRepository.findById(
                                requestId
                        )
                        .orElse(null);



        if(request == null){

            return "redirect:/checker/share-transfer";

        }



        List<TransShare> transfers =

                transShareRepository.findByTrId(
                        request.getEntityId()
                );



        model.addAttribute(
                "request",
                request
        );

        model.addAttribute(
                "fromChecker",
                true
        );
        model.addAttribute(
                "transfers",
                transfers
        );



        return "checker-share-transfer-view";


    }
    // =========================================================
   // CHECKER DATATABLE DATA
   // =========================================================


    @GetMapping("/data")
    @ResponseBody
    public Map<String,Object> checkerData(


            @RequestParam(defaultValue="0")
            int start,


            @RequestParam(defaultValue="10")
            int length,


            @RequestParam(defaultValue="1")
            int draw,


            @RequestParam(
                    value="search[value]",
                    defaultValue=""
            )
            String search


    ){



        List<ApprovalRequest> requests =

                requestRepository.findByStatus(
                        "PENDING_CHECKER"
                );




        List<Map<String,Object>> rows =

                new ArrayList<>();




        for(ApprovalRequest request : requests){



            AccountShare shareholder =

                    accountShareService.findById(
                            request.getEntityId()
                    );



            Map<String,Object> row =

                    new HashMap<>();



            row.put(
                    "requestId",
                    request.getRequestId()
            );



            row.put(
                    "entityId",
                    request.getEntityId()
            );

            row.put(
                    "entityId",
                    request.getEntityId()
            );

            row.put(
                    "makerId",
                    request.getMakerId()
            );



            row.put(
                    "operationName",
                    request.getOperationName()
            );



            row.put(
                    "status",
                    request.getStatus()
            );



            row.put(
                    "createdAt",
                    request.getCreatedAt()
            );




            if(shareholder != null){


                row.put(
                        "folioBo",
                        shareholder.getFolioBo()
                );


                row.put(
                        "custName",
                        shareholder.getCustName()
                );


                row.put(
                        "phone",
                        shareholder.getPhone()
                );


            }
            else{


                row.put(
                        "folioBo",
                        ""
                );


                row.put(
                        "custName",
                        ""
                );


                row.put(
                        "phone",
                        ""
                );


            }



            rows.add(row);


        }





        Map<String,Object> response =

                new HashMap<>();




        response.put(
                "draw",
                draw
        );



        response.put(
                "recordsTotal",
                rows.size()
        );



        response.put(
                "recordsFiltered",
                rows.size()
        );



        response.put(
                "data",
                rows
        );



        return response;


    }
    // =========================================================
    // CHECKER PENDING LIST PAGE
    // =========================================================


    @GetMapping("/shareholders")
    public String pendingShareholders(Model model){



        List<ApprovalRequest> requests =

                requestRepository.findByStatus(
                        "PENDING_CHECKER"
                );



        List<Map<String,Object>> rows =

                new ArrayList<>();




        for(ApprovalRequest request : requests){



            Map<String,Object> row =

                    new HashMap<>();



            row.put(
                    "requestId",
                    request.getRequestId()
            );


            row.put(
                    "entityId",
                    request.getEntityId()
            );


            row.put(
                    "makerId",
                    request.getMakerId()
            );


            row.put(
                    "operationName",
                    request.getOperationName()
            );


            row.put(
                    "status",
                    request.getStatus()
            );


            row.put(
                    "createdAt",
                    request.getCreatedAt()
            );




            // ==============================
            // SHAREHOLDER INFORMATION
            // ==============================


            AccountShare shareholder =

                    accountShareService.findById(
                            request.getEntityId()
                    );



            if(shareholder != null){


                row.put(
                        "folioBo",
                        shareholder.getFolioBo()
                );


                row.put(
                        "custName",
                        shareholder.getCustName()
                );


                row.put(
                        "phone",
                        shareholder.getPhone()
                );


            }
            else{


                row.put(
                        "folioBo",
                        ""
                );


                row.put(
                        "custName",
                        ""
                );


                row.put(
                        "phone",
                        ""
                );


            }




            rows.add(row);



        }




        model.addAttribute(
                "requests",
                rows
        );



        return "checker-shareholders";


    }




// =========================================================
// CHECKER APPROVE
// =========================================================


    @PostMapping("/approve/{requestId}")
    public String approve(


            @PathVariable Long requestId


    ){



        ApprovalRequest request =


                requestRepository
                        .findById(requestId)
                        .orElse(null);




        if(request == null){


            return "redirect:/checker/shareholders";


        }




        if(!"PENDING_CHECKER".equals(
                request.getStatus()
        )){


            return "redirect:/checker/shareholders";


        }




        LocalDateTime now =

                LocalDateTime.now();





        // ==============================
        // UPDATE APPROVAL REQUEST
        // ==============================


        request.setCheckerId(
                "test.checker"
        );


        request.setCheckerIp(
                "127.0.0.1"
        );



        request.setCurrentStage(
                "COMPLETED"
        );



        request.setStatus(
                "APPROVED"
        );



        request.setUpdatedAt(
                now
        );


        request.setDecidedAt(
                now
        );




        if(request.getVersionNo()==null){


            request.setVersionNo(1);


        }

        else{


            request.setVersionNo(
                    request.getVersionNo()+1
            );


        }



        requestRepository.save(request);


        // =====================================
        // UPDATE T_TRANS_SHARE VALID STATUS
        // =====================================


        List<TransShare> transShares =

                transShareRepository.findByTrId(
                        request.getEntityId()
                );


        for(TransShare transShare : transShares){

            transShare.setIsValid(1);

        }


        transShareRepository.saveAll(
                transShares
        );




        // ==============================
// UPDATE SHAREHOLDER STATUS
// ==============================


        if("SHARE_TRANSFER".equals(request.getEntityType())){


            // Share transfer approval handled by T_TRANS_SHARE


        }
        else{


            AccountShare shareholder =

                    accountShareService.findById(
                            request.getEntityId()
                    );



            if(shareholder != null){


                shareholder.setStatus(1);


                shareholder.setCheckerId(
                        "test.checker"
                );


                accountShareService.save(
                        shareholder
                );


            }


        }
        // ==============================
        // CREATE APPROVAL ACTION HISTORY
        // ==============================


        ApprovalAction action =

                new ApprovalAction();



        action.setRequestId(
                requestId
        );



        action.setAction(
                "APPROVED"
        );



        action.setStage(
                "CHECKER"
        );



        action.setActorId(
                "test.checker"
        );



        action.setActorIp(
                "127.0.0.1"
        );



        action.setRemarks(
                "Approved by checker"
        );



        action.setActionAt(
                now
        );



        actionRepository.save(action);



        return "checker-forward-success";


    }






// =========================================================
// CHECKER REJECT / RETURN TO MAKER
// =========================================================


    @PostMapping("/reject/{requestId}")
    public String reject(


            @PathVariable Long requestId,


            @RequestParam String remarks


    ){



        ApprovalRequest request =


                requestRepository
                        .findById(requestId)
                        .orElse(null);




        if(request == null){


            return "redirect:/checker/shareholders";


        }




        if(!"PENDING_CHECKER".equals(
                request.getStatus()
        )){


            return "redirect:/checker/shareholders";


        }




        LocalDateTime now =

                LocalDateTime.now();





        request.setCheckerId(
                "test.checker"
        );



        request.setCheckerIp(
                "127.0.0.1"
        );



        request.setCurrentStage(
                "MAKER"
        );



        request.setStatus(
                "RETURNED_FOR_MODIFICATION"
        );



        request.setUpdatedAt(
                now
        );



        request.setDecidedAt(
                now
        );




        if(request.getVersionNo()==null){


            request.setVersionNo(1);


        }

        else{


            request.setVersionNo(
                    request.getVersionNo()+1
            );


        }



        requestRepository.save(request);





        // ==============================
        // SAVE RETURN HISTORY
        // ==============================


        ApprovalAction action =

                new ApprovalAction();



        action.setRequestId(
                requestId
        );



        action.setAction(
                "RETURNED_FOR_MODIFICATION"
        );



        action.setStage(
                "CHECKER"
        );



        action.setActorId(
                "test.checker"
        );



        action.setActorIp(
                "127.0.0.1"
        );



        action.setRemarks(
                remarks
        );



        action.setActionAt(
                now
        );



        actionRepository.save(action);




        return "checker-return-success";


    }



}