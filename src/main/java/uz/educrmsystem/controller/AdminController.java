package uz.educrmsystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping
    public String admin() {
        return "/admin/dashboard";
    }

    @GetMapping("/courses")
    public String courses() {
        return "/admin/course";
    }

    @GetMapping("/groups")
    public String groups() {
        return "/admin/group";
    }

    @GetMapping("/students")
    public String students() {
        return "/admin/student";
    }

    @GetMapping("/requests")
    public String requests() {
        return "/admin/requests";
    }

    @GetMapping("/payments")
    public String payments() {
        return "/admin/payment";
    }

    @GetMapping("/all-payment")
    public String allPayments() {
        return "/admin/all-payments";
    }

}
