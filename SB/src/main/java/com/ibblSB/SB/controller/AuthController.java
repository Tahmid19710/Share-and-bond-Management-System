package com.ibblSB.SB.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class AuthController {



    // ===============================
    // MAIN LOGIN PAGE
    // ===============================

    @GetMapping("/login")
    public String login(){

        return "login";

    }





    // ===============================
    // MAKER LOGIN SELECT
    // ===============================

    @GetMapping("/auth/maker")
    public String makerLogin(Model model){


        model.addAttribute(
                "role",
                "Maker"
        );


        return "role-login";

    }





    // ===============================
    // CHECKER LOGIN SELECT
    // ===============================

    @GetMapping("/auth/checker")
    public String checkerLogin(Model model){


        model.addAttribute(
                "role",
                "Checker"
        );


        return "role-login";

    }






    // ===============================
    // APPROVER LOGIN SELECT
    // ===============================

    @GetMapping("/auth/approver")
    public String approverLogin(Model model){


        model.addAttribute(
                "role",
                "Approver"
        );


        return "role-login";

    }



}