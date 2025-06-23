package uz.educrmsystem.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import uz.educrmsystem.entity.Course;
import uz.educrmsystem.entity.Group;
import uz.educrmsystem.entity.Teacher;
import uz.educrmsystem.entity.enums.GroupStatus;
import uz.educrmsystem.exception.InvalidUserInputException;
import uz.educrmsystem.payload.GroupDTO;

import java.util.List;
import java.util.Objects;

@Component
public class GroupService {
    @PersistenceContext
    private EntityManager entityManager;

    public List<GroupDTO> getAllGroupDTOs() {
        List<Group> groups = entityManager.createQuery("""
                select g from Group g
                join fetch g.teacher
                join fetch g.course
                """, Group.class).getResultList();

        return groups.stream().map(group -> {
            GroupDTO dto = new GroupDTO();
            dto.setId(group.getId());
            dto.setName(group.getName());
            dto.setStartTime(group.getStartTime());
            dto.setEndTime(group.getEndTime());
            dto.setTeacherId(group.getTeacher().getId());
            dto.setCourseId(group.getCourse().getId());
            dto.setGroupStatus(group.getGroupStatus());
            dto.setTeacherFullName(group.getTeacher().getName() + " " + group.getTeacher().getSurname());
            dto.setCourseName(group.getCourse().getName());

            return dto;
        }).toList();
    }


    public List<Teacher> getAllActiveTeachers() {
        return entityManager
                .createQuery("select t from Teacher t where t.isActive=true", Teacher.class)
                .getResultList();
    }

    @Transactional
    public void addGroup(GroupDTO groupDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidUserInputException(
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        Teacher teacher = entityManager.find(Teacher.class, groupDTO.getTeacherId());
        Course course = entityManager.find(Course.class, groupDTO.getCourseId());

        if (teacher == null || course == null) {
            throw new InvalidUserInputException("");
        }

        Group build = Group.builder()
                .name(groupDTO.getName())
                .startTime(groupDTO.getStartTime())
                .endTime(groupDTO.getEndTime())
                .course(course)
                .teacher(teacher)
                .groupStatus(groupDTO.getGroupStatus())
                .build();


        entityManager.persist(build);
    }

    @Transactional
    public void deleteGroup(Long id) {
        Group group = entityManager.find(Group.class, id);
        if (group == null) {
            throw new InvalidUserInputException("");
        }

        entityManager.remove(group);
    }

    @Transactional
    public void changeStatus(Long id, GroupStatus status) {
        Group group = entityManager.find(Group.class, id);
        if (group == null) {
            throw new InvalidUserInputException("");
        }
        group.setGroupStatus(status);

        entityManager.merge(group);
    }
}
