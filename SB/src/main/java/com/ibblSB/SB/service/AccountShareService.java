package com.ibblSB.SB.service;


import com.ibblSB.SB.entity.AccountShare;
import com.ibblSB.SB.repository.AccountShareRepository;

import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class AccountShareService {



    private final AccountShareRepository repository;



    public AccountShareService(
            AccountShareRepository repository
    ){

        this.repository = repository;

    }





    // =====================================
    // SAVE / UPDATE
    // =====================================

    public AccountShare save(
            AccountShare shareholder
    ){

        return repository.save(
                shareholder
        );

    }


    // =====================================
    // TRANSFER VALIDATION
   // =====================================

    public boolean isTransferable(AccountShare accountShare){


        if(accountShare == null){

            return false;

        }


        // account must be valid

        if(accountShare.getIsValid() == 0){

            return false;

        }


        // lien account cannot transfer

        if(accountShare.getIsLien() == 1){

            return false;

        }


        return true;

    }



    // =====================================
    // FIND BY OID
    // =====================================

    public AccountShare findById(
            String oid
    ){

        return repository
                .findById(oid)
                .orElse(null);

    }


    // =====================================
    // FIND BY FOLIO BO
   // =====================================

    public AccountShare findByFolioBo(
            String folioBo
    ){

        return repository.findByFolioBo(
                folioBo
        );

    }

    

    // =====================================
    // DELETE
    // =====================================

    public void delete(
            String oid
    ){

        repository.deleteById(
                oid
        );

    }







    // =====================================
    // DATATABLE PAGINATION + SEARCH
    // =====================================

    public List<AccountShare> getData(
            int start,
            int length,
            String search
    ){


        List<AccountShare> list =
                repository.findAll();



        if(search != null &&
                !search.trim().isEmpty()){


            String keyword =
                    search.toLowerCase();



            list =
                    list.stream()
                            .filter(data ->


                                    (data.getOid()!=null &&
                                            data.getOid()
                                                    .toLowerCase()
                                                    .contains(keyword))


                                            ||

                                            (data.getFolioBo()!=null &&
                                                    data.getFolioBo()
                                                            .toLowerCase()
                                                            .contains(keyword))


                                            ||

                                            (data.getCustName()!=null &&
                                                    data.getCustName()
                                                            .toLowerCase()
                                                            .contains(keyword))


                                            ||

                                            (data.getPhone()!=null &&
                                                    data.getPhone()
                                                            .contains(keyword))


                                            ||

                                            (data.getEmail()!=null &&
                                                    data.getEmail()
                                                            .toLowerCase()
                                                            .contains(keyword))

                            )
                            .toList();


        }






        int from =
                Math.min(
                        start,
                        list.size()
                );


        int to =
                Math.min(
                        start + length,
                        list.size()
                );



        return list.subList(
                from,
                to
        );

    }







    // =====================================
    // DATATABLE COUNT
    // =====================================


    public long getTotal(
            String search
    ){



        if(search == null ||
                search.trim().isEmpty()){


            return repository.count();

        }




        return getData(
                0,
                Integer.MAX_VALUE,
                search
        ).size();


    }








    // =====================================
    // CHECKER PENDING LIST
    // STATUS = 0
    // =====================================


    public List<AccountShare> getPendingList(){


        return repository.findByStatus(0);


    }




}
