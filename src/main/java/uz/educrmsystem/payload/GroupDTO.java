package uz.educrmsystem.payload;

import jakarta.validation.constraints.*;
import lombok.*;
import uz.educrmsystem.entity.enums.GroupStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupDTO {

    private Long id;

    @NotBlank(message = "Group name must not be empty")
    @Size(min = 3, max = 100, message = "Group name must be between 3 and 100 characters")
    private String name;

    @NotBlank(message = "Start time must not be empty")
    private String startTime;

    @NotBlank(message = "End time must not be empty")
    private String endTime;

    @NotNull(message = "Teacher ID is required")
    private Long teacherId;

    private String teacherFullName;
    private String courseName;

    @NotNull(message = "Group status is required")
    private GroupStatus groupStatus;

    @NotNull
    private Long courseId;
}
