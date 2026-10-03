package com.ibblSB.SB.controller;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class HomeController {



    // =========================
    // Login / Main Page
    // =========================

    @GetMapping("/")
    public String home(Model model ){


        return "index";

    }





    // =========================
    // Common Dashboard
    // =========================

    @GetMapping("/dashboard")
    public String dashboard(

            @RequestParam(
                    defaultValue = "maker"
            )
            String role,

            Model model

    ){

        model.addAttribute(
                "role",
                role
        );


        return "dashboard";

    }







    // =========================
    // Maker Dashboard
    // =========================

    @GetMapping("/maker")
    public String maker(

            Model model

    ){

        model.addAttribute(
                "role",
                "maker"
        );


        return "dashboard";

    }








    // =========================
    // Checker Dashboard
    // =========================

    @GetMapping("/checker")
    public String checker(

            Model model

    ){

        model.addAttribute(
                "role",
                "checker"
        );


        return "dashboard";

    }








    // =========================
    // Approver Dashboard
    // =========================

    @GetMapping("/approver")
    public String approver(

            Model model

    ){

        model.addAttribute(
                "role",
                "approver"
        );


        return "dashboard";

    }



}