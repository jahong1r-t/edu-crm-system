package uz.educrmsystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import uz.educrmsystem.entity.enums.Role;

import java.time.LocalDate;

@Entity(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private String email;
    private String password;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;
    private Boolean isActive;
}
