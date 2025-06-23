package uz.educrmsystem.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import uz.educrmsystem.entity.Course;
import uz.educrmsystem.exception.InvalidUserInputException;
import uz.educrmsystem.payload.CourseDTO;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CourseService {
    @PersistenceContext
    private EntityManager entityManager;
    final AttachmentService attachmentService;


    @Transactional
    public void addCourse(CourseDTO courseDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidUserInputException(
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

//        Optional<Attachment> upload = attachmentService.upload(file);

        entityManager.persist(
                Course.builder()
                        .name(courseDTO.getName())
                        .description(courseDTO.getDescription())
                        .duration(courseDTO.getDuration())
                        .price(courseDTO.getPrice())
                        .isActive(courseDTO.getIsActive())
//                        .attachment(attachment)
                        .build()
        );
    }

    public List<Course> getAllCourses() {
        return entityManager
                .createQuery("select c from Course c", Course.class)
                .getResultList();
    }

    public List<Course> getAllActiveCourses() {
        return entityManager
                .createQuery("select c from Course c where c.isActive= true", Course.class)
                .getResultList();
    }

    @Transactional
    public void updateCourse(CourseDTO courseDTO, BindingResult bindingResult) {

    }

    @Transactional
    public void deleteCourse(Long id) {
        Course course = entityManager.find(Course.class, id);
        if (course != null) {
            entityManager.remove(course);
        }
    }

    @Transactional
    public void changeStatus(Long id, Boolean status) {
        Course course = entityManager.find(Course.class, id);
        if (course != null) {
            course.setIsActive(status);
        }
    }
}
