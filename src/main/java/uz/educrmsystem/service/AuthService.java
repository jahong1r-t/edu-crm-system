package uz.educrmsystem.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import uz.educrmsystem.dao.UserDAO;
import uz.educrmsystem.entity.Student;
import uz.educrmsystem.entity.User;
import uz.educrmsystem.entity.enums.Role;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthService extends DefaultOAuth2UserService implements AuthenticationSuccessHandler {
    final UserDAO userDAO;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("given_name");
        String surname = oAuth2User.getAttribute("family_name");
        String birthDate = oAuth2User.getAttribute("birthdate");
        String phone = oAuth2User.getAttribute("phone_number");

        oAuth2User.getAttributes().forEach((key, value) -> {
            System.out.println("key: " + key + " value: " + value);
        });

        User user = userDAO.findUserByEmail(email).orElseGet(() -> {
            Student student = new Student();
            student.setEmail(email);
            student.setName(name);
            student.setSurname(surname);
            student.setBirthDate(birthDate);
            student.setPhoneNumber(phone);
            student.setRole(Role.STUDENT);
            student.setIsActive(true);
            return userDAO.saveUser(student);
        });


        return new DefaultOAuth2User(
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())),
                oAuth2User.getAttributes(),
                "email"
        );
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();

        String redirectUrl = switch (user.getRole()) {
            case ADMIN -> "/admin";
            case TEACHER -> "/teacher";
            case STUDENT -> "/student";
        };

        response.sendRedirect(redirectUrl);
    }
}
