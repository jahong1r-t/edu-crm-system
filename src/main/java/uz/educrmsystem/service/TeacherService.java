package uz.educrmsystem.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import uz.educrmsystem.entity.Course;
import uz.educrmsystem.entity.Teacher;
import uz.educrmsystem.entity.enums.Role;
import uz.educrmsystem.exception.InvalidUserInputException;
import uz.educrmsystem.payload.TeacherDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TeacherService {
    @PersistenceContext
    private EntityManager entityManager;
    final PasswordEncoder passwordEncoder;


    public List<Teacher> getAllActiveTeachers() {
        return entityManager
                .createQuery("select t from Teacher t where t.isActive= true", Teacher.class)
                .getResultList();
    }

    @Transactional
    public List<TeacherDTO> getAllTeachers() {
        List<Teacher> teachers = entityManager
                .createQuery("select t from Teacher t", Teacher.class)
                .getResultList();


        return teachers.stream()
                .map(teacher -> TeacherDTO.builder()
                        .id(teacher.getId())
                        .name(teacher.getName())
                        .surname(teacher.getSurname())
                        .email(teacher.getEmail())
                        .specialty(teacher.getSpecialty())
                        .experience(teacher.getExperience())
                        .salary(teacher.getSalary())
                        .birthDate(teacher.getBirthDate())
                        .isActive(teacher.getIsActive())
                        .phoneNumber(teacher.getPhoneNumber())
                        .build()
                ).collect(Collectors.toList());
    }

    @Transactional
    public void addTeacher(TeacherDTO teacherDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidUserInputException(
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        Teacher teacher = new Teacher();
        teacher.setName(teacherDTO.getName());
        teacher.setSurname(teacherDTO.getSurname());
        teacher.setEmail(teacherDTO.getEmail());
        teacher.setSpecialty(teacherDTO.getSpecialty());
        teacher.setExperience(teacherDTO.getExperience());
        teacher.setSalary(teacherDTO.getSalary());
        teacher.setBirthDate(teacherDTO.getBirthDate());
        teacher.setIsActive(teacherDTO.getIsActive());
        teacher.setPhoneNumber(teacherDTO.getPhoneNumber());
        teacher.setPassword(passwordEncoder.encode(teacherDTO.getPassword()));
        teacher.setRole(Role.TEACHER);

        entityManager.persist(teacher);
    }

    @Transactional
    public void deleteTeacher(Long id) {
        Teacher teacher = entityManager.find(Teacher.class, id);

        System.err.println(teacher.getName());

        if (teacher != null) {
            entityManager.remove(teacher);
        }
    }

    @Transactional
    public void changeStatus(Long id, Boolean status) {
        Teacher teacher = entityManager.find(Teacher.class, id);
        if (teacher != null) {
            teacher.setIsActive(status);
        }
    }

    public void updateTeacher(Long id, TeacherDTO teacherDTO) {

    }
}