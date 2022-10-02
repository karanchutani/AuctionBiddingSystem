package com.planify.Planify_Auc_System_Karan.repository;

import com.planify.Planify_Auc_System_Karan.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}
