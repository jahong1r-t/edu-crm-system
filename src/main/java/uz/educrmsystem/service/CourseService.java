package uz.educrmsystem.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import uz.educrmsystem.entity.Attachment;
import uz.educrmsystem.entity.Course;
import uz.educrmsystem.exception.InvalidUserInputException;
import uz.educrmsystem.payload.CourseDTO;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CourseService {
    @PersistenceContext
    private EntityManager entityManager;
    final AttachmentService attachmentService;


    public void addCourse(CourseDTO courseDTO, MultipartFile file, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidUserInputException(
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        Optional<Attachment> upload = attachmentService.upload(file);

        upload.ifPresent(attachment ->
                entityManager.persist(
                        Course.builder()
                                .name(courseDTO.getName())
                                .description(courseDTO.getDescription())
                                .duration(courseDTO.getDuration())
                                .price(courseDTO.getPrice())
                                .isActive(courseDTO.isActive())
                                .attachment(attachment)
                                .build()
                ));
    }

    public List<Course> getAllCourses() {
        return entityManager
                .createQuery("select c from Course c", Course.class)
                .getResultList();
    }
}
