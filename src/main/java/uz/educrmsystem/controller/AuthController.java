package uz.educrmsystem.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.educrmsystem.entity.Student;
import uz.educrmsystem.entity.enums.Role;
import uz.educrmsystem.exception.InvalidUserInputException;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    @PersistenceContext
    private EntityManager entityManager;
    final PasswordEncoder passwordEncoder;


    @GetMapping("/sign-in")
    public String signIn() {
        return "/auth/sign-in";
    }

    @GetMapping("/sign-up")
    public String signUp() {
        return "/auth/sign-up";
    }

    @PostMapping("/sign-up")
    @Transactional
    public String signUp(@ModelAttribute Student user) {
        if (user.getEmail() == null || user.getEmail().isEmpty() ||
                user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new InvalidUserInputException("Invalid email or password");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.STUDENT);
        user.setIsActive(true);
        entityManager.persist(user);
        return "redirect:/student";
    }
}
