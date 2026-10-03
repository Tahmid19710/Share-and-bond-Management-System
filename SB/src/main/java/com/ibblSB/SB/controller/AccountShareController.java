package com.ibblSB.SB.controller;


import com.ibblSB.SB.dto.ShareholderFormDTO;

import com.ibblSB.SB.entity.AccountShare;
import com.ibblSB.SB.entity.AddressShare;
import com.ibblSB.SB.entity.BankInfoShare;
import com.ibblSB.SB.entity.ApprovalRequest;
import com.ibblSB.SB.entity.ShareholderChangeRequest;


import com.ibblSB.SB.repository.AddressShareRepository;
import com.ibblSB.SB.repository.BankInfoShareRepository;
import com.ibblSB.SB.repository.ApprovalRequestRepository;
import com.ibblSB.SB.repository.ShareholderChangeRequestRepository;


import com.ibblSB.SB.service.AccountShareService;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Controller
@RequestMapping("/shareholders")
public class AccountShareController {



    private final AccountShareService service;

    private final ShareholderChangeRequestRepository changeRequestRepository;

    private final ApprovalRequestRepository approvalRequestRepository;

    private final AddressShareRepository addressRepository;

    private final BankInfoShareRepository bankRepository;



    public AccountShareController(

            AccountShareService service,

            ShareholderChangeRequestRepository changeRequestRepository,

            ApprovalRequestRepository approvalRequestRepository,

            AddressShareRepository addressRepository,

            BankInfoShareRepository bankRepository

    ){

        this.service = service;

        this.changeRequestRepository =
                changeRequestRepository;

        this.approvalRequestRepository =
                approvalRequestRepository;

        this.addressRepository =
                addressRepository;

        this.bankRepository =
                bankRepository;

    }






    // ===============================
    // LIST PAGE
    // ===============================


    @GetMapping
    public String listPage(

            @RequestParam(
                    required=false,
                    defaultValue="maker"
            )
            String role,

            Model model

    ){


        model.addAttribute(
                "role",
                role
        );


        return "shareholders";

    }






    // ===============================
    // DATATABLE DATA
    // ===============================


    @GetMapping("/data")
    @ResponseBody
    public Map<String,Object> dataTable(

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


        List<AccountShare> data =
                service.getData(
                        start,
                        length,
                        search
                );


        long total =
                service.getTotal("");

        long filtered =
                service.getTotal(search);



        Map<String,Object> response =
                new HashMap<>();


        response.put(
                "draw",
                draw
        );


        response.put(
                "recordsTotal",
                total
        );


        response.put(
                "recordsFiltered",
                filtered
        );


        response.put(
                "data",
                data
        );


        return response;

    }







    // ===============================
    // CREATE PAGE
    // ===============================


    @GetMapping("/create")
    public String createPage(Model model){


        model.addAttribute(
                "form",
                new ShareholderFormDTO()
        );


        return "create-shareholder";

    }







    // ===============================
    // SAVE
    // ===============================


    @PostMapping("/save")
    public String save(

            @ModelAttribute ShareholderFormDTO form

    ){



        String oid =
                String.valueOf(
                        System.currentTimeMillis()
                );



        AccountShare shareholder =
                new AccountShare();



        shareholder.setOid(oid);


        shareholder.setFolioBo(
                form.getFolioBo()
        );


        shareholder.setCustName(
                form.getCustName()
        );


        shareholder.setPhone(
                form.getPhone()
        );


        shareholder.setEmail(
                form.getEmail()
        );


        shareholder.setCustType(
                form.getCustType()
        );


        shareholder.setCitizenType(
                form.getCitizenType()
        );


        shareholder.setResidentType(
                form.getResidentType()
        );


        shareholder.setIsEmployee(
                form.getIsEmployee()
        );


        shareholder.setTinNo(
                form.getTinNo()
        );


        shareholder.setIcbCode(
                form.getIcbCode()
        );


        shareholder.setIsLien(
                form.getIsLien()
        );


        shareholder.setShares(
                form.getShares()
        );


        shareholder.setSuspense(
                form.getSuspense()
        );


        shareholder.setBonus(
                form.getBonus()
        );


        shareholder.setBalance(
                form.getBalance()
        );


        shareholder.setMakerId(
                "test.maker"
        );


        shareholder.setStatus(0);



        service.save(
                shareholder
        );





        // ===============================
        // ADDRESS SAVE
        // ===============================


        AddressShare address =
                new AddressShare();


        address.setOid(oid);


        address.setFolioBo(
                form.getFolioBo()
        );


        address.setAdd1(
                form.getAdd1()
        );


        address.setAdd2(
                form.getAdd2()
        );


        address.setAdd3(
                form.getAdd3()
        );


        address.setAdd4(
                form.getAdd4()
        );


        address.setCountryName(
                form.getCountryName()
        );


        addressRepository.save(address);





        // ===============================
        // BANK SAVE
        // ===============================


        BankInfoShare bank =
                new BankInfoShare();


        bank.setOid(oid);


        bank.setFolioBo(
                form.getFolioBo()
        );


        bank.setAccNo(
                form.getAccNo()
        );


        bank.setBankName(
                form.getBankName()
        );


        bank.setBranchName(
                form.getBranchName()
        );


        bank.setRoutingNo(
                form.getRoutingNo()
        );


        bankRepository.save(bank);
        // ===============================
        // SHAREHOLDER CHANGE REQUEST
        // ===============================


        String changeId =
                String.valueOf(
                        System.currentTimeMillis()
                );


        ShareholderChangeRequest changeRequest =
                new ShareholderChangeRequest();



        changeRequest.setChangeId(
                changeId
        );


        changeRequest.setFolioBo(
                form.getFolioBo()
        );


        changeRequest.setOperationCode(
                "SHAREHOLDER_CREATE"
        );


        changeRequest.setNewValue(
                form.toString()
        );


        changeRequest.setCreatedBy(
                "test.maker"
        );


        changeRequest.setCreatedIp(
                "127.0.0.1"
        );


        changeRequest.setCreatedAt(
                LocalDateTime.now()
        );


        changeRequest.setUpdatedAt(
                LocalDateTime.now()
        );


        changeRequest.setVersionNo(
                1
        );


        changeRequestRepository.save(
                changeRequest
        );







        // ===============================
        // APPROVAL REQUEST
        // ===============================


        ApprovalRequest request =
                new ApprovalRequest();



        request.setRequestId(
                Long.valueOf(changeId)
        );


        request.setEntityId(
                oid
        );


        request.setEntityType(
                "SHAREHOLDER"
        );


        request.setMakerId(
                "test.maker"
        );


        request.setMakerIp(
                "127.0.0.1"
        );


        request.setCurrentStage(
                "CHECKER"
        );


        request.setStatus(
                "PENDING_CHECKER"
        );


        request.setOperationName(
                "SHAREHOLDER_CREATE"
        );


        request.setSourceId(
                changeId
        );


        request.setSourceType(
                "SHAREHOLDER_CHANGE"
        );


        request.setBusinessDate(
                LocalDate.now()
        );


        request.setBusinessReference(
                changeId
        );


        request.setCreatedAt(
                LocalDateTime.now()
        );


        request.setUpdatedAt(
                LocalDateTime.now()
        );


        request.setVersionNo(
                1
        );



        approvalRequestRepository.save(
                request
        );





        // ===============================
        // SUCCESS PAGE REDIRECT
        // ===============================


        return "redirect:/shareholders/success?folioBo="
                + form.getFolioBo();


    }







    // ===============================
    // SUCCESS PAGE
    // ===============================


    @GetMapping("/success")
    public String success(

            @RequestParam String folioBo,

            Model model

    ){


        model.addAttribute(
                "folioBo",
                folioBo
        );


        return "shareholder-success";

    }







    // ===============================
    // EDIT PAGE
    // ===============================


    @GetMapping("/edit/{oid}")
    public String editPage(

            @PathVariable String oid,

            Model model

    ){


        AccountShare shareholder =
                service.findById(oid);



        if(shareholder == null){

            return "redirect:/shareholders";

        }



        model.addAttribute(
                "shareholder",
                shareholder
        );


        return "edit-shareholder";

    }








    // ===============================
    // UPDATE
    // ===============================


    @PostMapping("/update")
    public String update(

            @ModelAttribute AccountShare formData

    ){


        AccountShare existing =
                service.findById(
                        formData.getOid()
                );



        if(existing != null){


            existing.setFolioBo(
                    formData.getFolioBo()
            );


            existing.setCustName(
                    formData.getCustName()
            );


            existing.setPhone(
                    formData.getPhone()
            );


            existing.setEmail(
                    formData.getEmail()
            );


            existing.setShares(
                    formData.getShares()
            );


            existing.setBalance(
                    formData.getBalance()
            );


            service.save(
                    existing
            );


        }



        return "redirect:/shareholders?role=maker";


    }








    // ===============================
    // VIEW DETAILS
    // ===============================

    @GetMapping("/view/{oid}")
    public String viewPage(

            @PathVariable String oid,

            @RequestParam(
                    required=false,
                    defaultValue="maker"
            )
            String role,

            @RequestParam(
                    required=false
            )
            Long requestId,

            Model model

    ){


        AccountShare shareholder =
                service.findById(oid);



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
                "role",
                role
        );


        // checker approve/reject এর জন্য
        model.addAttribute(
                "requestId",
                requestId
        );



        return "view-shareholder";


    }



    // ===============================
    // DELETE
    // ===============================


    @GetMapping("/delete/{oid}")
    public String delete(

            @PathVariable String oid

    ){


        service.delete(
                oid
        );


        return "redirect:/shareholders?role=maker";


    }



}