package com.cda.controllers;

import com.cda.configs.CustomeUserDetails;
import com.cda.entities.FacultyProfile;
import com.cda.entities.StudentProfile;
import com.cda.entities.Users;
import com.cda.payload.UserDto;
import com.cda.repositries.UserRepository;
import com.cda.services.FacultyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/faculty")
public class FacultyController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/home")
    public String adminPage(Principal principal, Model model) {
        String principalName = principal.getName();
        Optional<Users> users = userRepository.findByUsernameOrEmail(principalName, principalName);

        if (users.isPresent()) {
            model.addAttribute("user", mapToDTO(users.get()));
        }
        System.out.println("Faculty controller faculty page method");
        return "faculty/facultyhome";
    }

    @PostMapping("/update")
    public String saveFaculty(@ModelAttribute FacultyProfile facultyProfile, @RequestParam("userId") Long userId,@RequestParam("courseIds") List<Long> courseIds) {
        facultyService.saveFaculty(facultyProfile,userId,courseIds);
        return "redirect:/admin/faculty";
    }

    @PostMapping("/updateByFaculty")
    public String updateFaculty(@ModelAttribute FacultyProfile facultyProfile,
                                @RequestParam("userId") Long userId,
                                @RequestParam("courseIds") List<Long> courseIds,
                                Model model, Principal principal) {
        // Update the faculty profile
        facultyService.updateFacultyWithUsers(facultyProfile, userId, courseIds);

        // Fetch the updated faculty profile (you can use the facultyProfile's ID if it is available)
        Long facultyId = facultyProfile.getId(); // Assuming you have set the ID
        FacultyProfile updatedFacultyProfile = facultyService.getFacultyById(facultyId);

        // Add the updated faculty profile to the model
        model.addAttribute("faculty", updatedFacultyProfile);

        // Fetch and add the user information to the model, if necessary
        Optional<Users> user = userRepository.findById(userId);
        user.ifPresent(value -> model.addAttribute("user", mapToDTO(value)));

        // Return the faculty home view with updated data
        return "faculty/facultyhome";
    }


    @GetMapping("/edit/{id}")
    public String showEditFacultyForm(@PathVariable Long id, Model model) {
        FacultyProfile facultyProfile = facultyService.getFacultyByUserId(id);
        if (facultyProfile == null) {
            return "redirect:home";
        }
        model.addAttribute("faculty", facultyProfile);
        model.addAttribute("departments", facultyService.getAllDepartments());
        model.addAttribute("courses", facultyService.getAllCourses());
        return "/faculty/faculty-form-faculty";
    }


    private UserDto mapToDTO(Users user) {
        return mapper.map(user, UserDto.class);
    }

    @GetMapping("/class-list")
    public String getClassList(Model model, @AuthenticationPrincipal CustomeUserDetails userDetails) {
        Long facultyId = userDetails.getId(); // Get the faculty ID from the authenticated user
        List<Users> students = facultyService.getStudentsForFaculty(facultyId);
        model.addAttribute("students", students);
        System.out.println("facultyId -- > "+facultyId);
        System.out.println(students);
        return "faculty/class-list"; // Name of the Thymeleaf template
    }


}
