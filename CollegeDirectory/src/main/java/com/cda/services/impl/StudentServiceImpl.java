package com.cda.services.impl;

import com.cda.entities.*;
import com.cda.exceptions.ResourceNotFoundException;
import com.cda.payload.UserDto;
import com.cda.repositries.*;
import com.cda.services.StudentService;
import com.cda.services.UserServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CoursesFacultyRepository coursesFacultyRepository;

    @Autowired
    private FacultyProfileRepository facultyProfileRepository;

    @Override
    public StudentProfile getStudentProfileByUsername(String username) {

        return studentRepository.findByUser_Email(username)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "email", username));
    }

    public List<StudentProfile> getAllStudents() {
        return studentRepository.findAll();
    }

    public StudentProfile getStudentById(Long id) {
        return studentRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Student", "Id", String.valueOf(id)));
    }

    public StudentProfile saveStudent(StudentProfile student, Long userId, List<Long> courseIds) {
        // Find the existing user and set it to the student profile
        Users existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", String.valueOf(userId)));
        student.setUser(existingUser);

        // Handle course enrollment: first delete old enrollments if present
        if (student.getId() != null) {
            List<Enrollment> existingEnrollments = enrollmentRepository.findByStudentId(student.getId());
            if (!existingEnrollments.isEmpty()) {
                enrollmentRepository.deleteAll(existingEnrollments);
            }
        }

        // Create new enrollments for the selected courses
        List<Enrollment> newEnrollments = new ArrayList<>();
        for (Long courseId : courseIds) {
            Course course = courseRepository.findById(courseId).orElse(null);
            if (course != null) {
                Enrollment enrollment = new Enrollment();
                enrollment.setStudent(student);  // Set the student profile
                enrollment.setCourse(course);    // Set the course
                newEnrollments.add(enrollment);
            }
        }

        // Set the new enrollments to the student profile
        student.setEnrollments(newEnrollments);

        // Now save the student profile, which will cascade and save the enrollments
        StudentProfile savedStudentProfile = studentRepository.save(student);


        return savedStudentProfile;
    }



    @Transactional
    public void deleteStudent(Long id) {
        enrollmentRepository.deleteByStudentId(id);
        studentRepository.deleteById(id);
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    private UserDto mapToDTO(Users user) {
        return mapper.map(user, UserDto.class);
    }

    public List<FacultyProfile> getAdvisorsForStudent(Long studentId) {
        // Fetch all courses the student is enrolled in
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);

        Set<FacultyProfile> advisors = new HashSet<>();

        // Loop through each enrollment and fetch corresponding faculty members
        for (Enrollment enrollment : enrollments) {
            Long courseId = enrollment.getCourse().getId();

            // Fetch faculty IDs for the enrolled course
            List<Long> facultyIds = coursesFacultyRepository.findFacultyIdsByCourseId(courseId);

            // Fetch FacultyProfile for each facultyId
            for (Long facultyId : facultyIds) {
                FacultyProfile faculty = facultyProfileRepository.findById(facultyId)
                        .orElseThrow(() -> new ResourceNotFoundException("Faculty", "Id", facultyId.toString()));
//                System.out.println(faculty);
                advisors.add(faculty);  // Add to set to avoid duplicates
            }
        }

        return new ArrayList<>(advisors);  // Convert to List and return
    }

    public List<StudentProfile> searchStudents(String name, Long departmentId, String year) {
         List<StudentProfile> studentsByFilters = studentRepository.findStudentsByFilters(name, departmentId, year);
        System.out.println("search students in studentsService: " + studentsByFilters.size());
        return studentsByFilters;
    }


}
