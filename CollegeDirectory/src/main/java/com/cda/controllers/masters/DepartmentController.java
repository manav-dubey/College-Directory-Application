package com.cda.controllers.masters;

import com.cda.entities.Department;
import com.cda.repositries.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping
    public String showDepartments(Model model) {
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("editMode", false);
        return "masters/master-departments";
    }

    @PostMapping("/save")
    public String saveDepartment(@RequestParam(value = "id",required = false) Long id ,
                                 @RequestParam("name") String name,
                                 @RequestParam("description") String description) {
        Department department;
        if (id != null) {
            department = departmentRepository.findById(id).orElse(new Department());
        } else {
            department = new Department();
        }

        department.setName(name);
        department.setDescription(description);
        departmentRepository.save(department);
        return "redirect:/departments";
    }

    @GetMapping("/edit/{id}")
    public String editDepartment(@PathVariable Long id, Model model) {
        Department department = departmentRepository.findById(id).orElse(null);
        model.addAttribute("department", department);
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("editMode", true);
        return "masters/master-departments";
    }

}
