package uz.educrmsystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.educrmsystem.entity.enums.GroupStatus;
import uz.educrmsystem.payload.CourseDTO;
import uz.educrmsystem.payload.GroupDTO;
import uz.educrmsystem.payload.TeacherDTO;
import uz.educrmsystem.service.CourseService;
import uz.educrmsystem.service.GroupService;
import uz.educrmsystem.service.TeacherService;


@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    final GroupService groupService;
    final TeacherService teacherService;
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
        model.addAttribute("groups", groupService.getAllGroupDTOs());
        model.addAttribute("teachers", groupService.getAllActiveTeachers());
        model.addAttribute("courses", courseService.getAllActiveCourses());

        return "/admin/group";
    }

    @GetMapping("/students")
    public String students() {
        return "/admin/student";
    }

    @GetMapping("/requests")
    public String requests() {
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

    @PostMapping("/add-course")
    public String addCourse(@Valid @ModelAttribute CourseDTO courseDTO, BindingResult bindingResult) {
        courseService.addCourse(courseDTO, bindingResult);

        return "redirect:/admin/courses";
    }

    @PostMapping("/update-course")
    public String updateCourse(@Valid @ModelAttribute CourseDTO courseDTO, BindingResult bindingResult) {

        return "redirect:/admin/courses";
    }

    @PostMapping("/change-status-course")
    public String changeStatusCourse(@RequestParam(name = "id") Long id,
                                     @RequestParam(name = "status") Boolean status) {
        courseService.changeStatus(id, status);

        return "redirect:/admin/courses";
    }


    @GetMapping("/delete-course/{id}")
    public String deleteCourse(@PathVariable("id") Long id) {
        courseService.deleteCourse(id);

        return "redirect:/admin/courses";
    }


    @PostMapping("/add-group")
    public String addGroup(@Valid @ModelAttribute("group") GroupDTO groupDTO, BindingResult bindingResult) {
        groupService.addGroup(groupDTO, bindingResult);

        return "redirect:/admin/groups";
    }


    @GetMapping("/delete-group/{id}")
    public String deleteGroup(@PathVariable("id") Long id) {
        groupService.deleteGroup(id);
        return "redirect:/admin/groups";
    }

    @PostMapping
    public String updateGroup(@Valid @ModelAttribute GroupDTO groupDTO, BindingResult bindingResult) {
        return "redirect:/admin/groups";
    }

    @PostMapping("/change-status-group")
    public String changeStatus(@RequestParam(name = "id") Long id,
                               @RequestParam(name = "status") GroupStatus status) {
        groupService.changeStatus(id, status);

        return "redirect:/admin/groups";
    }


    @GetMapping("/teachers")
    public String getTeachers(Model model) {
        model.addAttribute("teachers", teacherService.getAllTeachers());
        return "/admin/teacher";
    }

    @PostMapping("/add-teacher")
    public String addTeacher(@Valid @ModelAttribute TeacherDTO teacherDTO, BindingResult bindingResult) {
        teacherService.addTeacher(teacherDTO, bindingResult);
        return "redirect:/admin/teachers";
    }

    @PostMapping("/update-teacher")
    public String updateTeacher(@ModelAttribute TeacherDTO teacherDTO) {
        teacherService.updateTeacher(teacherDTO.getId(), teacherDTO);
        return "redirect:/admin/teachers";
    }

    @PostMapping("/change-status-teacher")
    public String changeStatus(@RequestParam("id") Long id, @RequestParam("status") Boolean isActive) {
        teacherService.changeStatus(id, isActive);
        return "redirect:/admin/teachers";
    }

    @GetMapping("/delete-teacher/{id}")
    public String deleteTeacher(@PathVariable("id") Long id) {
        teacherService.deleteTeacher(id);
        return "redirect:/admin/teachers";
    }

}
