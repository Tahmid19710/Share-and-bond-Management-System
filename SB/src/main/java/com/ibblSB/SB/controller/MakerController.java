package com.ibblSB.SB.controller;


import com.ibblSB.SB.entity.AccountShare;
import com.ibblSB.SB.entity.AddressShare;
import com.ibblSB.SB.entity.BankInfoShare;
import com.ibblSB.SB.entity.ApprovalAction;
import com.ibblSB.SB.entity.ApprovalRequest;


import com.ibblSB.SB.repository.AddressShareRepository;
import com.ibblSB.SB.repository.BankInfoShareRepository;
import com.ibblSB.SB.repository.ApprovalActionRepository;
import com.ibblSB.SB.repository.ApprovalRequestRepository;


import com.ibblSB.SB.service.AccountShareService;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Controller
@RequestMapping("/maker")
public class MakerController {



    private final ApprovalRequestRepository approvalRepository;

    private final ApprovalActionRepository actionRepository;

    private final AccountShareService accountShareService;

    private final AddressShareRepository addressRepository;

    private final BankInfoShareRepository bankRepository;



    public MakerController(

            ApprovalRequestRepository approvalRepository,

            ApprovalActionRepository actionRepository,

            AccountShareService accountShareService,

            AddressShareRepository addressRepository,

            BankInfoShareRepository bankRepository

    ){


        this.approvalRepository = approvalRepository;

        this.actionRepository = actionRepository;

        this.accountShareService = accountShareService;

        this.addressRepository = addressRepository;

        this.bankRepository = bankRepository;


    }
     // =====================================================
     // RETURNED MODIFICATION REQUEST LIST
     // =====================================================


    @GetMapping("/modification")
    public String modificationList(Model model){


        List<ApprovalRequest> requests =

                approvalRepository.findByStatus(
                        "RETURNED_FOR_MODIFICATION"
                );



        List<Map<String,Object>> rows =

                new ArrayList<>();



        for(ApprovalRequest request : requests){


            Map<String,Object> row =

                    new HashMap<>();



            // =========================
            // APPROVAL REQUEST DATA
            // =========================


            row.put(
                    "requestId",
                    request.getRequestId()
            );


            row.put(
                    "entityId",
                    request.getEntityId()
            );


            row.put(
                    "status",
                    request.getStatus()
            );





            // =========================
            // SHAREHOLDER DATA
            // =========================


            AccountShare shareholder =

                    accountShareService.findById(
                            request.getEntityId()
                    );



            if(shareholder != null){


                row.put(
                        "customerName",
                        shareholder.getCustName()
                );


                row.put(
                        "phone",
                        shareholder.getPhone()
                );


            }
            else{


                row.put(
                        "customerName",
                        ""
                );


                row.put(
                        "phone",
                        ""
                );


            }






            // =========================
            // LAST CHECKER REMARK
            // =========================

            List<ApprovalAction> actions =

                    actionRepository
                            .findByRequestIdOrderByActionAtDesc(
                                    request.getRequestId()
                            );


            ApprovalAction action = null;


            if(!actions.isEmpty()){

                action = actions.get(0);

            }




            if(action != null){


                row.put(
                        "remark",
                        action.getRemarks()
                );


            }
            else{


                row.put(
                        "remark",
                        ""
                );


            }





            rows.add(row);



        }





        model.addAttribute(
                "requests",
                rows
        );



        return "maker-modification-list";


    }


    // =====================================================
    // OPEN EDIT PAGE
      // =====================================================


    @GetMapping("/edit/{id}")
    public String edit(

            @PathVariable Long id,

            Model model

    ){


        ApprovalRequest request =

                approvalRepository
                        .findById(id)
                        .orElse(null);



        if(request == null){

            return "redirect:/maker/modification";

        }




        String oid = request.getEntityId();




        AccountShare shareholder =

                accountShareService.findById(
                        oid
                );



        AddressShare address =

                addressRepository
                        .findByOid(oid)
                        .orElse(null);



        BankInfoShare bank =

                bankRepository
                        .findByOid(oid)
                        .orElse(null);





        model.addAttribute(
                "shareholder",
                shareholder
        );


        model.addAttribute(
                "address",
                address
        );


        model.addAttribute(
                "bank",
                bank
        );



        model.addAttribute(
                "requestId",
                id
        );



        return "maker-edit-shareholder";


    }






// =====================================================
// RESUBMIT AFTER MODIFICATION
// =====================================================


    @PostMapping("/resubmit/{id}")
    public String resubmit(

            @PathVariable Long id,

            @ModelAttribute AccountShare form,

            @ModelAttribute AddressShare addressForm,

            @ModelAttribute BankInfoShare bankForm

    )
    {



        ApprovalRequest request =

                approvalRepository
                        .findById(id)
                        .orElse(null);



        if(request == null){

            return "redirect:/maker/modification";

        }




        AccountShare existing =

                accountShareService.findById(
                        request.getEntityId()
                );





        if(existing != null){



            existing.setCustName(
                    form.getCustName()
            );


            existing.setPhone(
                    form.getPhone()
            );


            existing.setEmail(
                    form.getEmail()
            );


            existing.setFolioBo(
                    form.getFolioBo()
            );



            accountShareService.save(
                    existing
            );

            // ================================
            // UPDATE ADDRESS INFORMATION
            // ================================


            AddressShare address =
                    addressRepository
                            .findByOid(request.getEntityId())
                            .orElse(null);


            if(address != null){


                address.setAdd1(
                        addressForm.getAdd1()
                );


                address.setAdd2(
                        addressForm.getAdd2()
                );


                address.setAdd3(
                        addressForm.getAdd3()
                );


                address.setAdd4(
                        addressForm.getAdd4()
                );


                addressRepository.save(address);

            }
             // ================================
             // UPDATE BANK INFORMATION
             // ================================


            BankInfoShare bank =
                    bankRepository
                            .findByOid(request.getEntityId())
                            .orElse(null);



            if(bank != null){


                bank.setBankName(
                        bankForm.getBankName()
                );


                bank.setAccNo(
                        bankForm.getAccNo()
                );


                bank.setBranchName(
                        bankForm.getBranchName()
                );


                bank.setRoutingNo(
                        bankForm.getRoutingNo()
                );


                bankRepository.save(bank);

            }
        }







        // ==========================
        // SEND BACK TO CHECKER
        // ==========================


        request.setStatus(
                "PENDING_CHECKER"
        );



        request.setCurrentStage(
                "CHECKER"
        );



        request.setUpdatedAt(
                LocalDateTime.now()
        );




        if(request.getVersionNo()==null){


            request.setVersionNo(1);


        }
        else{


            request.setVersionNo(
                    request.getVersionNo()+1
            );


        }





        approvalRepository.save(
                request
        );





        return "redirect:/maker/modification";


    }

}