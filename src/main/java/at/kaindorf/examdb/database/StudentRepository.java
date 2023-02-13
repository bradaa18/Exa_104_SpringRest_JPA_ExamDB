package at.kaindorf.examdb.database;

import at.kaindorf.examdb.pojos.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("select s from Student s WHERE s.classname.classId = ?1")
    Page<Student> getStudentByClassname(Long id, Pageable page);
}
