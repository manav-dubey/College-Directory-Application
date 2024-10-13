package com.cda.controllers;

import com.cda.entities.FacultyProfile;
import com.cda.entities.StudentProfile;
import com.cda.entities.Users;
import com.cda.payload.UserDto;
import com.cda.repositries.UserRepository;
import com.cda.services.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/students")
public class StudentController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentService studentService;


    @Autowired
    private ModelMapper mapper;

    @RequestMapping("/home")
    public String adminPage(Principal principal, Model model) {
        String principalName = principal.getName();

        StudentProfile studentProfile = studentService.getStudentProfileByUsername(principalName);

        if(studentProfile != null) {
            model.addAttribute("studentProfile", studentProfile);
            model.addAttribute("departments", studentService.getAllDepartments());
        }

        System.out.println("Student controller student page method");
        System.out.println("----"+studentProfile.getId()+"----"+studentProfile.getUser().getEmail()+"--"+studentProfile.getUser().getPhone());
        return "student/studenthome";
    }

//    @PostMapping("/save")
//    public String saveStudent(@ModelAttribute StudentProfile student,@RequestParam("userId") Long userId,
//                              @RequestParam("courseIds") List<Long> courseIds) {
//        studentService.saveStudent(student,userId, courseIds);
//        return "redirect:/admin/students";
//    }

    @PostMapping("/update")
    public String updateStudent(@ModelAttribute StudentProfile student,@RequestParam("userId") Long userId,@RequestParam("courseIds") List<Long> courseIds) {
        System.out.println("Student Controller update method called");
        studentService.saveStudent(student,userId,courseIds);
        return "redirect:/admin/students";
    }

    private UserDto mapToDTO(Users user) {
        return mapper.map(user, UserDto.class);
    }

    @GetMapping("/advisors/{id}")
    public String getAdvisors(Model model, @PathVariable Long id) {
        // Fetch the advisors for the given student
        List<FacultyProfile> advisors = studentService.getAdvisorsForStudent(id);
//        System.out.println(advisors);
        model.addAttribute("advisors", advisors);
        return "student/student-advisors";  // Thymeleaf template to render the advisors
    }

    @GetMapping("/searchStudents")
    public String searchStudents(@RequestParam(required = false) String name,
                                 @RequestParam(required = false) Long department,
                                 @RequestParam(required = false) String year,
                                 Model model) {
         List<StudentProfile> students = studentService.searchStudents(name, department, year);
        System.out.println("searchStudents -- > student size "+students.size());
        model.addAttribute("students", students);
        return "redirect:home"; // Redirect to your student home page
    }

}
