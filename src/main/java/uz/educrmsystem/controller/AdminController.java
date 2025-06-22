package uz.educrmsystem.controller;

import com.nimbusds.oauth2.sdk.Request;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.educrmsystem.entity.Group;
import uz.educrmsystem.exception.InvalidUserInputException;
import uz.educrmsystem.payload.GroupDTO;
import uz.educrmsystem.service.CourseService;

import java.time.LocalDate;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    final CourseService courseService;

    @GetMapping
    public String admin() {
        return "/admin/dashboard";
    }

    @GetMapping("/courses")
    public String courses(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        return "/admin/course";
    }

    @GetMapping("/groups")
    public String groups(Model model) {
        return "/admin/group";
    }

    @GetMapping("/students")
    public String students() {
        return "/admin/student";
    }

    @GetMapping("/requests")
    public String requests(Model model) {
        return "/admin/requests";
    }

    @GetMapping("/payments")
    public String payments() {
        return "/admin/payment";
    }

    @GetMapping("/all-payment")
    public String allPayments() {
        return "/admin/all-payments";
    }

//    @PostMapping("/add-course")
//    public String addCourse(@RequestParam("name") String name,
//                            @RequestParam(name = "description") String description,
//                            @RequestParam(name = "duration") int duration,
//                            @RequestParam(name = "price") Double price,
//                            @RequestParam(name = "isActive") boolean isActive,
//                            @RequestParam(name = "file") MultipartFile file) {
//
//
//        System.err.println("name: " + name + "description: " + description + "duration: " + duration + "price: " + price + "isActive: " + isActive);
//
//        CourseDTO courseDTO = new CourseDTO();
//        courseDTO.setName(name);
//        courseDTO.setDescription(description);
//        courseDTO.setDuration(duration);
//        courseDTO.setPrice(price);
//        courseDTO.setActive(isActive);
//
//        courseService.addCourse(courseDTO, file, new BeanPropertyBindingResult(courseDTO, "courseDTO"));
//
//        return "redirect:/admin/courses";
//    }


    @PostMapping("/add-course")
    public String addCourse(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return "redirect:/admin/courses?error=empty_file";
            }

            System.out.println("Fayl nomi: " + file.getOriginalFilename());
            System.out.println("Fayl hajmi: " + file.getSize() + " bayt");


        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/courses?error=upload_failed";
        }

        return "redirect:/admin/courses?success=true";
    }


    @PostMapping("/add-group")
    public String addGroup(@Valid @ModelAttribute("group") GroupDTO groupDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new InvalidUserInputException(
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        Group group = new Group();

        group.setName(groupDTO.getName());
        group.setStartDate(LocalDate.parse(groupDTO.getStartDate()));
        group.setEndDate(LocalDate.parse(groupDTO.getEndDate()));
        group.setStartTime(groupDTO.getStartTime());
        group.setEndTime(groupDTO.getEndTime());

//        Teacher teacher = entityManager.find(Teacher.class, groupDTO.getTeacherId());
//        group.setTeacher(teacher);
        group.setGroupStatus(groupDTO.getGroupStatus());

//        entityManager.persist(
//                Group.builder()
//                        .name(group.getName())
//                        .startDate(group.getStartDate())
//                        .endDate(group.getEndDate())
//                        .groupStatus(GroupStatus.NOT_STARTED)
//
//                        .build());
        return "redirect:/admin/courses";
    }
}
