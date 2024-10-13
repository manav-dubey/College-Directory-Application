package com.cda.controllers;

import com.cda.entities.FacultyProfile;
import com.cda.entities.StudentProfile;
import com.cda.entities.Users;
import com.cda.payload.UserDto;
import com.cda.repositries.UserRepository;
import com.cda.services.FacultyService;
import com.cda.services.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private ModelMapper mapper;

    @RequestMapping("/home")
    public String adminPage(Principal principal, Model model) {
        String principalName = principal.getName();
        Optional<Users> users = userRepository.findByUsernameOrEmail(principalName, principalName);

        if (users.isPresent()) {
            model.addAttribute("user", mapToDTO(users.get()));
        }
        System.out.println("Admin controller admin page method");
        return "admin/adminhome";
    }

    @GetMapping("/students")
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "student/student-list";
    }

    @GetMapping("/students/edit/{id}")
    public String showEditStudentForm(@PathVariable Long id, Model model) {
        StudentProfile student = studentService.getStudentById(id);
        if (student == null) {
            return "redirect:/admin/students";
        }
        model.addAttribute("student", student);
        model.addAttribute("departments", studentService.getAllDepartments());
        model.addAttribute("courses", studentService.getAllCourses());
        return "/student/student-form";
    }

    @GetMapping("students/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        System.out.println("studentController/delete/{id}  --- > "+id);
        studentService.deleteStudent(id);
        return "redirect:/admin/students";
    }


    @GetMapping("/faculty")
    public String listFaculty(Model model) {
        model.addAttribute("faculty", facultyService.getAllFaculty());
        return "faculty/faculty-list";
    }

    @GetMapping("/faculty/edit/{id}")
    public String showEditFacultyForm(@PathVariable Long id, Model model) {
        FacultyProfile facultyProfile = facultyService.getFacultyById(id);
        if (facultyProfile == null) {
            return "redirect:/admin/faculty";
        }
        model.addAttribute("faculty", facultyProfile);
        model.addAttribute("departments", facultyService.getAllDepartments());
        model.addAttribute("courses", facultyService.getAllCourses());
        return "/faculty/faculty-form";
    }

    @GetMapping("faculty/delete/{id}")
    public String deleteFaculty(@PathVariable Long id) {
        System.out.println("facultyController/delete/{id}  --- > "+id);
        facultyService.deleteFaculty(id);
        return "redirect:/admin/faculty";
    }
    private UserDto mapToDTO(Users user) {
        return mapper.map(user, UserDto.class);
    }

}