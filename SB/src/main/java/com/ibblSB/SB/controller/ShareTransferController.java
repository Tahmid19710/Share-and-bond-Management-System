package com.ibblSB.SB.controller;


import com.ibblSB.SB.entity.AccountShare;
import com.ibblSB.SB.entity.TransAuth;
import com.ibblSB.SB.entity.TransShare;
import com.ibblSB.SB.entity.ApprovalRequest;



import com.ibblSB.SB.repository.TransAuthRepository;
import com.ibblSB.SB.service.AccountShareService;
import com.ibblSB.SB.repository.TransShareRepository;
import com.ibblSB.SB.repository.ApprovalRequestRepository;
import com.ibblSB.SB.repository.SequenceRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;




@Controller
@RequestMapping("/maker/share-transfer")
public class ShareTransferController {



    private final AccountShareService accountShareService;

    private final TransAuthRepository transAuthRepository;

    private final TransShareRepository transShareRepository;

    private final ApprovalRequestRepository approvalRequestRepository;

    private final SequenceRepository sequenceRepository;




    private String generateTrId(){


        String datePart =

                LocalDate.now()
                        .format(
                                DateTimeFormatter.ofPattern("yyyyMMdd")
                        );



        Long sequence =

                sequenceRepository
                        .getNextShareMovementSequence();



        String serialPart =

                String.format(
                        "%06d",
                        sequence
                );



        return datePart + serialPart;


    }

    private String generateGrpTrId(){


        String year =

                LocalDate.now()
                        .format(
                                DateTimeFormatter.ofPattern("yyyy")
                        );



        Long sequence =

                sequenceRepository
                        .getNextGroupTransferSequence();



        String serialPart =

                String.format(
                        "%09d",
                        sequence
                );



        return year + serialPart;


    }

    // =====================================
    // SHARE TRANSFER PAGE
    // =====================================


    @GetMapping
    public String page(Model model){


        List<TransShare> transactions =

                transShareRepository
                        .findLatestTransactions();



        model.addAttribute(
                "transactions",
                transactions
        );


        return "share-transfer";

    }




    public ShareTransferController(

            AccountShareService accountShareService,

            TransAuthRepository transAuthRepository,

            TransShareRepository transShareRepository,

            ApprovalRequestRepository approvalRequestRepository,

            SequenceRepository sequenceRepository

    ){

        this.accountShareService = accountShareService;

        this.transAuthRepository = transAuthRepository;

        this.transShareRepository = transShareRepository;

        this.approvalRequestRepository = approvalRequestRepository;

        this.sequenceRepository = sequenceRepository;

    }


    // =====================================
    // DEBIT SEARCH
    // =====================================


    @PostMapping("/search")
    public String search(


            @RequestParam("folioBo")
            String folioBo,


            @RequestParam(
                    value="creditFolioBo",
                    required=false
            )
            String creditFolioBo,


            Model model


    ){


        AccountShare debit =

                accountShareService.findByFolioBo(
                        folioBo
                );



        AccountShare credit = null;



        if(creditFolioBo != null &&
                !creditFolioBo.isEmpty()){


            credit =

                    accountShareService.findByFolioBo(
                            creditFolioBo
                    );

        }





        model.addAttribute(
                "shareholder",
                debit
        );



        model.addAttribute(
                "creditShareholder",
                credit
        );

        List<TransShare> transactions =

                transShareRepository
                        .findLatestTransactions();



        model.addAttribute(
                "transactions",
                transactions
        );

        model.addAttribute(
                "searchFolio",
                folioBo
        );



        model.addAttribute(
                "creditSearchFolio",
                creditFolioBo
        );



        return "share-transfer";


    }



// =====================================
// VIEW SHARE TRANSFER DETAILS (MAKER)
// =====================================

    @GetMapping("/view/{trId}")
    public String viewShareTransfer(

            @PathVariable("trId")
            String trId,

            Model model

    ){


        List<TransShare> transfers =

                transShareRepository
                        .findByTrId(trId);



        ApprovalRequest request =

                approvalRequestRepository
                        .findByEntityId(trId)
                        .stream()
                        .findFirst()
                        .orElse(null);



        model.addAttribute(
                "transfers",
                transfers
        );



        model.addAttribute(
                "request",
                request
        );

        model.addAttribute(
                "fromChecker",
                false
        );

        model.addAttribute(
                "trId",
                trId
        );



        return "checker-share-transfer-view";

    }

    // =====================================
    // CREDIT SEARCH
    // =====================================


    @PostMapping("/credit-search")
    public String creditSearch(


            @RequestParam("creditFolioBo")
            String creditFolioBo,


            @RequestParam(
                    value="folioBo",
                    required=false
            )
            String folioBo,


            Model model


    ){



        AccountShare credit =

                accountShareService.findByFolioBo(
                        creditFolioBo
                );



        AccountShare debit = null;



        if(folioBo != null &&
                !folioBo.isEmpty()){


            debit =

                    accountShareService.findByFolioBo(
                            folioBo
                    );

        }





        model.addAttribute(
                "shareholder",
                debit
        );



        model.addAttribute(
                "creditShareholder",
                credit
        );



        model.addAttribute(
                "searchFolio",
                folioBo
        );



        model.addAttribute(
                "creditSearchFolio",
                creditFolioBo
        );

        List<TransShare> transactions =

                transShareRepository
                        .findLatestTransactions();



        model.addAttribute(
                "transactions",
                transactions
        );

        return "share-transfer";


    }// =====================================
    // SAVE TRANSFER
    // =====================================


    @PostMapping("/save")
    public String saveTransfer(


            @RequestParam("folioBo")
            String folioBo,


            @RequestParam("creditFolioBo")
            String creditFolioBo,


            Model model


    ){


        AccountShare debit =

                accountShareService.findByFolioBo(
                        folioBo
                );



        AccountShare credit =

                accountShareService.findByFolioBo(
                        creditFolioBo
                );





        model.addAttribute(
                "shareholder",
                debit
        );


        model.addAttribute(
                "creditShareholder",
                credit
        );


        model.addAttribute(
                "searchFolio",
                folioBo
        );


        model.addAttribute(
                "creditSearchFolio",
                creditFolioBo
        );







        if(debit == null){


            model.addAttribute(
                    "error",
                    "Debit account not found"
            );


            return "share-transfer";

        }




        if(credit == null){


            model.addAttribute(
                    "error",
                    "Credit account not found"
            );


            return "share-transfer";

        }







        if(!accountShareService.isTransferable(debit)){


            model.addAttribute(
                    "error",
                    "Debit account is not transferable"
            );


            return "share-transfer";

        }






        if(!accountShareService.isTransferable(credit)){


            model.addAttribute(
                    "error",
                    "Credit account is not transferable"
            );


            return "share-transfer";

        }








        // =====================================
        // CREATE T_TRANS_AUTH RECORD
        // =====================================


        TransAuth transAuth = new TransAuth();

        String transferId = generateTrId();

        String grpTrId = generateGrpTrId();


        transAuth.setOid(
                String.valueOf(System.currentTimeMillis())
        );



        transAuth.setFolioBo(
                debit.getFolioBo()
        );



        transAuth.setTrId(
              transferId
        );



        transAuth.setTrCode(
                "F2F"
        );



        transAuth.setTrDate(
                new Date()
        );



        transAuth.setDrAmt(
                debit.getBalance()
        );



        transAuth.setCrAmt(
                credit.getBalance()
        );



        transAuth.setTrState(
                0
        );



        transAuth.setMakerId(
                "test.maker"
        );



        transAuth.setMakerIp(
                "127.0.0.1"
        );



        transAuth.setParticular(
                "Share Transfer"
        );



        transAuth.setRemarks(
                "Pending Checker Approval"
        );



        transAuth.setModifyDate(
                new Date()
        );





        transAuthRepository.save(
                transAuth
        );


        // =====================================
        // CREATE T_TRANS_SHARE DEBIT ROW
        // =====================================


        TransShare debitShare = new TransShare();


        debitShare.setOid(
                sequenceRepository.getNextTransShareOid()
        );


        debitShare.setFolioBo(
                debit.getFolioBo()
        );


        debitShare.setTrId(
                transferId
        );


        debitShare.setGrpTrId(
                grpTrId
        );


        debitShare.setTrDate(
                new Date()
        );


        debitShare.setTrType(
                "201"
        );


        debitShare.setTrCode(
                "03"
        );


        debitShare.setDrShare(
                debit.getShares().doubleValue()
        );


        debitShare.setCrShare(
                0.0
        );


        debitShare.setContraAcc(
                credit.getFolioBo()
        );


        debitShare.setParticulars(
                "Share Transfer Debit"
        );


        debitShare.setUserId(
                "test.maker"
        );


        debitShare.setIsValid(
                0
        );


        debitShare.setPostDate(
                new Date()
        );



        transShareRepository.save(
                debitShare
        );





// =====================================
// CREATE T_TRANS_SHARE CREDIT ROW
// =====================================


        TransShare creditShare = new TransShare();


        creditShare.setOid(
                sequenceRepository.getNextTransShareOid()
        );


        creditShare.setFolioBo(
                credit.getFolioBo()
        );


        creditShare.setTrId(
                transferId
        );


        creditShare.setGrpTrId(
                grpTrId
        );


        creditShare.setTrDate(
                new Date()
        );


        creditShare.setTrType(
                "101"
        );


        creditShare.setTrCode(
                "02"
        );


        creditShare.setDrShare(
                0.0
        );


        creditShare.setCrShare(
                debit.getShares().doubleValue()
        );


        creditShare.setContraAcc(
                debit.getFolioBo()
        );


        creditShare.setParticulars(
                "Share Transfer Credit"
        );


        creditShare.setUserId(
                "test.maker"
        );


        creditShare.setIsValid(
                0
        );


        creditShare.setPostDate(
                new Date()
        );



        transShareRepository.save(
                creditShare
        );

        // =====================================
        // CREATE APPROVAL REQUEST
        // =====================================


        ApprovalRequest approvalRequest = new ApprovalRequest();


        approvalRequest.setRequestId(
                System.currentTimeMillis()
        );


        approvalRequest.setEntityId(
                transferId
        );


        approvalRequest.setEntityType(
                "SHARE_TRANSFER"
        );


        approvalRequest.setMakerId(
                "test.maker"
        );


        approvalRequest.setMakerIp(
                "127.0.0.1"
        );


        approvalRequest.setCurrentStage(
                "CHECKER"
        );


        approvalRequest.setStatus(
                "PENDING_CHECKER"
        );


        approvalRequest.setOperationName(
                "SHARE_TRANSFER"
        );


        approvalRequest.setSourceType(
                "T_TRANS_SHARE"
        );



        approvalRequest.setSourceId(
                transferId
        );

        approvalRequest.setCreatedAt(
                java.time.LocalDateTime.now()
        );

        approvalRequest.setBusinessDate(
                java.time.LocalDate.now()
        );

        approvalRequest.setUpdatedAt(
                java.time.LocalDateTime.now()
        );

        approvalRequest.setVersionNo(
                1
        );



        approvalRequestRepository.save(
                approvalRequest
        );

        model.addAttribute(
                "success",
                "Transfer request saved successfully"
        );



        return "share-transfer";


    }


}