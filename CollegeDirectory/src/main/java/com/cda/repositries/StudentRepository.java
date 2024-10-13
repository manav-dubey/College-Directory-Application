package com.cda.repositries;


import com.cda.entities.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentProfile, Long> {
        Optional<StudentProfile> findByUser_Username(String username);
        Optional<StudentProfile> findByUser_Email(String username);

        @Query("SELECT s FROM StudentProfile s " +
                "JOIN s.user u " +
                "JOIN s.department d " +
                "WHERE (:name IS NULL OR LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
                "AND (:departmentId IS NULL OR d.id = :departmentId) " +
                "AND (:year IS NULL OR s.year = :year)")
        List<StudentProfile> findStudentsByFilters(
                @Param("name") String name,
                @Param("departmentId") Long departmentId,
                @Param("year") String year);




}
