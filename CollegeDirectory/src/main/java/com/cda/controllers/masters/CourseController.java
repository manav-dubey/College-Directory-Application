package com.cda.controllers.masters;

import com.cda.entities.Course;
import com.cda.entities.Department;
import com.cda.repositries.CourseRepository;
import com.cda.repositries.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping
    public String showCourses(Model model) {
        model.addAttribute("courses", courseRepository.findAll());
        model.addAttribute("departments", departmentRepository.findAll()); // For dropdown
        model.addAttribute("editMode", false);
        return "masters/master-courses";  // HTML file to display the courses
    }

    @PostMapping("/save")
    public String saveCourse(@RequestParam(value = "id", required = false) Long id,
                             @RequestParam("title") String title,
                             @RequestParam("description") String description,
                             @RequestParam("departmentId") Long departmentId) {
        Course course;
        if (id != null) {
            course = courseRepository.findById(id).orElse(new Course());
        } else {
            course = new Course();
        }

        course.setTitle(title);
        course.setDescription(description);

        // Set department based on the selected department ID
        Department department = departmentRepository.findById(departmentId).orElse(null);
        if (department != null) {
            course.setDepartment(department);
        }

        courseRepository.save(course);
        return "redirect:/courses";  // Refresh list after save
    }

    @GetMapping("/edit/{id}")
    public String editCourse(@PathVariable Long id, Model model) {
        Course course = courseRepository.findById(id).orElse(null);
        model.addAttribute("course", course);
        model.addAttribute("courses", courseRepository.findAll());  // List all courses
        model.addAttribute("departments", departmentRepository.findAll());  // For dropdown
        model.addAttribute("editMode", true);
        return "masters/master-courses";  // HTML file to display the courses
    }
}
