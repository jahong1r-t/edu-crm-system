package uz.educrmsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
public class StudentController {
    @GetMapping
    public String student() {
        return "/student/dashboard";
    }

    @GetMapping("/profile")
    public String profile() {
        return "/student/profile";
    }
}
