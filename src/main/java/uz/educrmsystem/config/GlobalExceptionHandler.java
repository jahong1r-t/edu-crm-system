package uz.educrmsystem.config;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public String handleException(UsernameNotFoundException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("code", HttpStatus.CONFLICT);
        return "error";
    }
}
