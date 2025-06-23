package uz.educrmsystem.payload;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDTO {

    private Long id;

    @NotBlank(message = "Name must not be empty")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Surname must not be empty")
    @Size(min = 2, max = 50, message = "Surname must be between 2 and 50 characters")
    private String surname;

    @NotBlank(message = "Email must not be empty")
    @Email(message = "Email must be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;

    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    private String password;

    @NotBlank(message = "Specialty must not be empty")
    @Size(min = 3, max = 100, message = "Specialty must be between 3 and 100 characters")
    private String specialty;

    @NotNull(message = "Experience is required")
    @Min(value = 0, message = "Experience must be 0 or greater")
    private Integer experience;

    @NotNull(message = "Salary is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Salary must be greater than 0")
    private Double salary;

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Birth date must be in YYYY-MM-DD format")
    private String birthDate;

    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Phone number must be valid (e.g., +1234567890)")
    private String phoneNumber;

    @NotNull(message = "Status is required")
    private Boolean isActive;
}