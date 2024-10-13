package com.cda.services.impl;

import com.cda.entities.*;
import com.cda.exceptions.ResourceNotFoundException;
import com.cda.repositries.*;
import com.cda.services.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private FacultyProfileRepository facultyProfileRepository;

    @Autowired
    private CoursesFacultyRepository coursesFacultyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Override
    public FacultyProfile getFacultyProfileByUsername(String name) {
        return facultyProfileRepository.findByUser_Email(name)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty", "email", name));
    }

    @Override
    public List<FacultyProfile> getAllFaculty() {
        return facultyProfileRepository.findAll();
    }

    @Override
    public FacultyProfile getFacultyById(Long id) {
        return facultyProfileRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Faculty", "Id", String.valueOf(id)));
    }

    public FacultyProfile getFacultyByUserId(Long id) {
        return facultyProfileRepository.findByUserId(id).
                orElseThrow(() -> new ResourceNotFoundException("Faculty", "Id", String.valueOf(id)));
    }
    public FacultyProfile saveFaculty(FacultyProfile faculty, Long userId, List<Long> courseIds) {
        // Find the existing user and set it to the faculty profile
        Users existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", String.valueOf(userId)));
        faculty.setUser(existingUser);

        // Handle faculty-course associations (delete old associations first if updating)
        if (faculty.getId() != null) {
            List<Courses_Faculty> existingFacultyCourses = coursesFacultyRepository.findByFacultyId(faculty.getId());
            if (!existingFacultyCourses.isEmpty()) {
                coursesFacultyRepository.deleteAll(existingFacultyCourses);
            }
        }

        // Create new faculty-course associations for the selected courses
        List<Courses_Faculty> newFacultyCourses = new ArrayList<>();
        for (Long courseId : courseIds) {
            Course course = courseRepository.findById(courseId).orElse(null);
            if (course != null) {
                Courses_Faculty facultyCourse = new Courses_Faculty();
                facultyCourse.setFaculty(faculty);   // Set the faculty profile
                facultyCourse.setCourse(course);    // Set the course
                newFacultyCourses.add(facultyCourse);
            }
        }

        // Set the new course associations to the faculty profile
        faculty.setCourses_faculties(newFacultyCourses);

        // Now save the faculty profile, which will cascade and save the facultyCourses associations
        FacultyProfile savedFacultyProfile = facultyProfileRepository.save(faculty);


        return savedFacultyProfile;
    }
    public FacultyProfile updateFacultyWithUsers(FacultyProfile faculty, Long userId, List<Long> courseIds) {
        // Fetch the existing user associated with the faculty profile
        Users existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", "Faculty does not have an associated user."));

        // Debugging statement to check the existing user before updates
        System.out.println("Existing User before update: " + existingUser.getName() + ", " + existingUser.getEmail());

        // Update only non-null fields in the user object
        Users userFromFaculty = faculty.getUser(); // Get user details from faculty
        if (userFromFaculty != null) {
            // Only update if the value is not null to prevent overwriting existing data
            if (userFromFaculty.getName() != null) {
                existingUser.setName(userFromFaculty.getName());
            }
            if (userFromFaculty.getPhone() != null) {
                existingUser.setPhone(userFromFaculty.getPhone());
            }
            if (userFromFaculty.getEmail() != null) {
                existingUser.setEmail(userFromFaculty.getEmail());
            }
            // Important: Do not change username or password here unless required
        }

        // Debugging statement after updating user fields
        System.out.println("Updated User details: " + existingUser.getName() + ", " + existingUser.getEmail());

        // Handle faculty-course associations
        List<Courses_Faculty> currentCoursesFaculties = faculty.getCourses_faculties();
        if (currentCoursesFaculties == null) {
            currentCoursesFaculties = new ArrayList<>();
            faculty.setCourses_faculties(currentCoursesFaculties);  // Initialize the list if it's null
        }

        // Create a set of course IDs for current associations
        Set<Long> currentCourseIds = currentCoursesFaculties.stream()
                .map(coursesFaculty -> coursesFaculty.getCourse().getId())
                .collect(Collectors.toSet());

        // Add new courses if not already associated
        for (Long courseId : courseIds) {
            if (!currentCourseIds.contains(courseId)) {
                Course course = courseRepository.findById(courseId).orElse(null);
                if (course != null) {
                    Courses_Faculty facultyCourse = new Courses_Faculty();
                    facultyCourse.setFaculty(faculty);
                    facultyCourse.setCourse(course);
                    currentCoursesFaculties.add(facultyCourse);
                }
            }
        }

        // Remove courses that are no longer in the new list
        currentCoursesFaculties.removeIf(coursesFaculty -> !courseIds.contains(coursesFaculty.getCourse().getId()));

        // Now save the faculty profile, which will cascade and save the course associations
        faculty.setUser(existingUser);
        FacultyProfile savedFacultyProfile = facultyProfileRepository.save(faculty);

        // Debugging statement to check the saved faculty profile
        System.out.println("Saved Faculty Profile with ID: " + savedFacultyProfile.getId());

        return savedFacultyProfile;
    }



    @Transactional
    @Override
    public void deleteFaculty(Long id) {
        coursesFacultyRepository.deleteByFacultyId(id);
        facultyProfileRepository.deleteById(id);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();

    }

    public List<Users> getStudentsForFaculty(Long facultyId) {
        return enrollmentRepository.findStudentsByFacultyId(facultyId);
    }

}
