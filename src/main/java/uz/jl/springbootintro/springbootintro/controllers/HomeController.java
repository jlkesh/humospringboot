package uz.jl.springbootintro.springbootintro.controllers;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @RequestMapping("")
    public String index() {
        return "index";
    }

    @ResponseBody
    @RequestMapping("/profile")
    public String profile(@AuthenticationPrincipal User user) {
        return "profile " + user.getUsername();
    }

    @ResponseBody
    @RequestMapping("/manager")
    public String manager(@AuthenticationPrincipal User user) {
        return "manager " + user.getUsername() + ": roles -> " + user
                .getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .toList();
    }

    @ResponseBody
    @RequestMapping("/admin")
    public String admin(@AuthenticationPrincipal User user) {
        return "admin " + user.getUsername() + ": roles -> " + user
                .getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .toList();
    }

}
